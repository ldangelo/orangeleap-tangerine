package com.orangeleap.tangerine.security;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.SpringSecurityMessageSource;
import org.springframework.security.ldap.LdapAuthoritiesPopulator;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.ldap.LdapAuthenticationProvider;
import org.springframework.security.providers.ldap.LdapAuthenticator;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.ldap.LdapUserDetailsMapper;
import org.springframework.security.userdetails.ldap.UserDetailsContextMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SiteService;

public class TangerineAuthenticationProvider implements AuthenticationProvider {
    private static final Log logger = OLLogger.getLog(LdapAuthenticationProvider.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    protected LdapAuthenticator authenticator;
    private LdapAuthoritiesPopulator authoritiesPopulator;
    private UserDetailsContextMapper userDetailsContextMapper = new LdapUserDetailsMapper();
    private boolean useAuthenticationRequestCredentials = true;
    
    @Resource(name="siteService")
    private SiteService siteService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;
    
    public TangerineAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator) {
        this.setAuthenticator(authenticator);
        this.setAuthoritiesPopulator(authoritiesPopulator);
    }

    public TangerineAuthenticationProvider(LdapAuthenticator authenticator) {
        this.setAuthenticator(authenticator);
        this.setAuthoritiesPopulator(new NullAuthoritiesPopulator());
    }

    public TangerineAuthenticationProvider() {
    }

    private void setAuthenticator(LdapAuthenticator authenticator) {
        Assert.notNull(authenticator, "An LdapAuthenticator must be supplied");
        this.authenticator = authenticator;
    }

    private LdapAuthenticator getAuthenticator() {
        return authenticator;
    }

    private void setAuthoritiesPopulator(LdapAuthoritiesPopulator authoritiesPopulator) {
        Assert.notNull(authoritiesPopulator, "An LdapAuthoritiesPopulator must be supplied");
        this.authoritiesPopulator = authoritiesPopulator;
    }

    protected LdapAuthoritiesPopulator getAuthoritiesPopulator() {
        return authoritiesPopulator;
    }

    public void setUserDetailsContextMapper(UserDetailsContextMapper userDetailsContextMapper) {
        Assert.notNull(userDetailsContextMapper, "UserDetailsContextMapper must not be null");
        this.userDetailsContextMapper = userDetailsContextMapper;
    }

    protected UserDetailsContextMapper getUserDetailsContextMapper() {
        return userDetailsContextMapper;
    }

    public void setUseAuthenticationRequestCredentials(boolean useAuthenticationRequestCredentials) {
        this.useAuthenticationRequestCredentials = useAuthenticationRequestCredentials;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
            messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                "Only UsernamePasswordAuthenticationToken is supported"));

        TangerineAuthenticationToken userToken = (TangerineAuthenticationToken)authentication;

        String username = userToken.getName();
        String site = userToken.getSite();
        boolean active = checkSiteActive(site);

        if (!StringUtils.hasLength(username)) {
            throw new BadCredentialsException(messages.getMessage("LdapAuthenticationProvider.emptyUsername",
                    "Empty Username"));
        }

        if (!active) {
            throw new BadCredentialsException(messages.getMessage("LdapAuthenticationProvider.inactiveSite",
                    "Inactive Site"));
        }
        
        String password = (String) authentication.getCredentials();
        Assert.notNull(password, "Null password was supplied in authentication token");

        if (password.length() == 0) {
            logger.trace("Rejecting empty password for user " + username);
            throw new BadCredentialsException(messages.getMessage("LdapAuthenticationProvider.emptyPassword",
                    "Empty Password"));
        }

        try {
            DirContextOperations userData = getAuthenticator().authenticate(authentication);

            GrantedAuthority[] extraAuthorities = loadUserAuthorities(userData, username, password, site);

            UserDetails user = userDetailsContextMapper.mapUserFromContext(userData, username, extraAuthorities);

            Authentication authenticationToken = createSuccessfulAuthentication(userToken, user);
            
            if (authenticationToken.isAuthenticated()) {
	            // IMPORTANT: Can't write to the database until this authentication token is set in the session, 
	            // otherwise the TangerineDataSource will not have access to the siteName to change databases.
	            try {
        			Map<String, String> map = ((TangerineLdapAuthoritiesPopulator)authoritiesPopulator).populateUserAttributesMapFromLdap(userData, username, site);
	            	((TangerineAuthenticationToken)authenticationToken).setUserAttributes(map);
				} catch (javax.naming.NamingException e) {
					e.printStackTrace();
					throw new RuntimeException("Unable to read user attributes.", e);
				}
            }
            
            return authenticationToken;
            
        } catch (NamingException ldapAccessFailure) {
            /* "error code 32" means the Organization (Company) name is invalid,
             * so by catching this explicitly here, we can pitch the correct exception
             * rather than allow the ugly LDAP error to propogate to the login screen
             */
            if(ldapAccessFailure.getMessage().contains("error code 32")) {
                throw new BadCredentialsException("Invalid Organization Name");
            } else {
                throw new AuthenticationServiceException(ldapAccessFailure.getMessage(), ldapAccessFailure);
            }
        }
    }
    protected boolean checkSiteActive(String siteName) {
         Site site = siteService.readSite(siteName);
         if (site == null) {
            return true; // new site
        }
         return site.isActive();
    }
    
    protected GrantedAuthority[] loadUserAuthorities(DirContextOperations userData, String username, String password, String site) {
        return ((TangerineLdapAuthoritiesPopulator)getAuthoritiesPopulator()).getGrantedAuthorities(userData, username, site);
    }

    protected Authentication createSuccessfulAuthentication(TangerineAuthenticationToken authentication,
            UserDetails user) {
        Object password = useAuthenticationRequestCredentials ? authentication.getCredentials() : user.getPassword();

        return new TangerineAuthenticationToken(user, password, authentication.getSite(), user.getAuthorities());
    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class authentication) {
        return (TangerineAuthenticationToken.class.isAssignableFrom(authentication));
    }

	private static class NullAuthoritiesPopulator implements LdapAuthoritiesPopulator {
        public GrantedAuthority[] getGrantedAuthorities(DirContextOperations userDetails, String username) {
            return new GrantedAuthority[0];
        }
    }
}

