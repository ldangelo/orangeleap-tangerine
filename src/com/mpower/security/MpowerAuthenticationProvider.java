package com.mpower.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.SpringSecurityMessageSource;
import org.springframework.security.context.SecurityContextHolder;
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

import com.mpower.domain.Person;
import com.mpower.service.PersonService;
import com.mpower.service.exception.PersonValidationException;


public class MpowerAuthenticationProvider implements AuthenticationProvider {
    private static final Log logger = LogFactory.getLog(LdapAuthenticationProvider.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    protected LdapAuthenticator authenticator;
    private LdapAuthoritiesPopulator authoritiesPopulator;
    private UserDetailsContextMapper userDetailsContextMapper = new LdapUserDetailsMapper();
    private boolean useAuthenticationRequestCredentials = true;
    private PersonService personService;
    
    public MpowerAuthenticationProvider(LdapAuthenticator authenticator, LdapAuthoritiesPopulator authoritiesPopulator, PersonService personService) {
        this.setAuthenticator(authenticator);
        this.setAuthoritiesPopulator(authoritiesPopulator);
        this.setPersonService(personService);
    }

    public MpowerAuthenticationProvider(LdapAuthenticator authenticator) {
        this.setAuthenticator(authenticator);
        this.setAuthoritiesPopulator(new NullAuthoritiesPopulator());
    }

    public MpowerAuthenticationProvider() {
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

        MpowerAuthenticationToken userToken = (MpowerAuthenticationToken)authentication;

        String username = userToken.getName();
        String site = userToken.getSite();

        if (!StringUtils.hasLength(username)) {
            throw new BadCredentialsException(messages.getMessage("LdapAuthenticationProvider.emptyUsername",
                    "Empty Username"));
        }

        String password = (String) authentication.getCredentials();
        Assert.notNull(password, "Null password was supplied in authentication token");

        if (password.length() == 0) {
            logger.debug("Rejecting empty password for user " + username);
            throw new BadCredentialsException(messages.getMessage("LdapAuthenticationProvider.emptyPassword",
                    "Empty Password"));
        }

        try {
            DirContextOperations userData = getAuthenticator().authenticate(authentication);

            GrantedAuthority[] extraAuthorities = loadUserAuthorities(userData, username, password, site);

            UserDetails user = userDetailsContextMapper.mapUserFromContext(userData, username, extraAuthorities);

            Authentication authenticationToken = createSuccessfulAuthentication(userToken, user);
            
            if (authenticationToken.isAuthenticated()) {
            	Person person = getPersonService().readPersonByLoginId(username);
            	if (person == null) {
            	    person = createPerson(userData, username, site);
            	} 
            	((MpowerAuthenticationToken)authenticationToken).setPersonId(person.getId());
            } 
            
            return authenticationToken;
            
        } catch (NamingException ldapAccessFailure) {
            throw new AuthenticationServiceException(ldapAccessFailure.getMessage(), ldapAccessFailure);
        } catch (PersonValidationException personValidationException) {
            throw new AuthenticationServiceException(personValidationException.getMessage(), personValidationException);
        } catch (javax.naming.NamingException namingException) {
            throw new AuthenticationServiceException(namingException.getMessage(), namingException);
        }
    }
    
    // Create a Person object row corresponding to the login user.
    private Person createPerson(DirContextOperations user, String username, String site)  throws PersonValidationException, javax.naming.NamingException {
        Person person = getPersonService().createDefaultPerson(site);
        ((MpowerLdapAuthoritiesPopulator)getAuthoritiesPopulator()).populatePersonAttributesFromLdap(user, username, site, person);
        person.setConstituentIndividualRoles("user");
        person.setLoginId(username);
        return getPersonService().maintainPerson(person);
    }
    
    protected GrantedAuthority[] loadUserAuthorities(DirContextOperations userData, String username, String password, String site) {
        return ((MpowerLdapAuthoritiesPopulator)getAuthoritiesPopulator()).getGrantedAuthorities(userData, username, site);
    }

    protected Authentication createSuccessfulAuthentication(MpowerAuthenticationToken authentication,
            UserDetails user) {
        Object password = useAuthenticationRequestCredentials ? authentication.getCredentials() : user.getPassword();

        return new MpowerAuthenticationToken(user, password, authentication.getSite(), user.getAuthorities());
    }

    @SuppressWarnings("unchecked")
    public boolean supports(Class authentication) {
        return (MpowerAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	private static class NullAuthoritiesPopulator implements LdapAuthoritiesPopulator {
        public GrantedAuthority[] getGrantedAuthorities(DirContextOperations userDetails, String username) {
            return new GrantedAuthority[0];
        }
    }
}

