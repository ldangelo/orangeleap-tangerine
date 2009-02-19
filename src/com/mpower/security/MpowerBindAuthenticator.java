package com.mpower.security;

import java.util.Iterator;

import javax.naming.directory.DirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.ldap.NamingException;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.security.Authentication;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.ldap.SpringSecurityContextSource;
import org.springframework.security.ldap.SpringSecurityLdapTemplate;
import org.springframework.security.providers.ldap.authenticator.AbstractLdapAuthenticator;
import org.springframework.util.Assert;

public class MpowerBindAuthenticator extends AbstractLdapAuthenticator {
    private static final Log logger = LogFactory.getLog(MpowerBindAuthenticator.class);

    /**
     * Create an initialized instance using the {@link SpringSecurityContextSource} provided.
     * @param contextSource the SpringSecurityContextSource instance against which bind operations will be performed.
     */
    public MpowerBindAuthenticator(SpringSecurityContextSource contextSource) {
        super(contextSource);
    }

    @SuppressWarnings("unchecked")
    public DirContextOperations authenticate(Authentication authentication) {
        DirContextOperations user = null;
        Assert.isInstanceOf(MpowerAuthenticationToken.class, authentication, "Can only process MpowerAuthenticationToken objects");

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        String site = ((MpowerAuthenticationToken) authentication).getSite();

        // If DN patterns are configured, try authenticating with them directly
        Iterator dns = getUserDns(username).iterator();

        while (dns.hasNext() && user == null) {
            user = bindWithDn((String) dns.next(), username, password);
        }

        // Otherwise use the configured locator to find the user
        // and authenticate with the returned DN.
        if (user == null && getUserSearch() != null) {
            DirContextOperations userFromSearch = ((MpowerLdapUserSearch) getUserSearch()).searchForUser(username, site);
            user = bindWithDn(userFromSearch.getDn().toString(), username, password);
        }

        if (user == null) {
            throw new BadCredentialsException(messages.getMessage("BindAuthenticator.badCredentials", "Bad credentials"));
        }

        return user;
    }

    private DirContextOperations bindWithDn(String userDn, String username, String password) {
        SpringSecurityLdapTemplate template = new SpringSecurityLdapTemplate(new BindWithSpecificDnContextSource((SpringSecurityContextSource) getContextSource(), userDn, password));

        try {
            return template.retrieveEntry(userDn, getUserAttributes());

        } catch (BadCredentialsException e) {
            // This will be thrown if an invalid user name is used and the method may
            // be called multiple times to try different names, so we trap the exception
            // unless a subclass wishes to implement more specialized behaviour.
            handleBindException(userDn, username, e.getCause());
        }

        return null;
    }

    /**
     * Allows subclasses to inspect the exception thrown by an attempt to bind with a particular DN. The default implementation just reports the failure to the debug log.
     */
    protected void handleBindException(String userDn, String username, Throwable cause) {
        if (logger.isDebugEnabled()) {
            logger.debug("Failed to bind as " + userDn + ": " + cause);
        }
    }

    private class BindWithSpecificDnContextSource implements ContextSource {
        private SpringSecurityContextSource ctxFactory;
        DistinguishedName userDn;
        private String password;

        public BindWithSpecificDnContextSource(SpringSecurityContextSource ctxFactory, String userDn, String password) {
            this.ctxFactory = ctxFactory;
            this.userDn = new DistinguishedName(userDn);
            this.userDn.prepend(ctxFactory.getBaseLdapPath());
            this.password = password;
        }

        public DirContext getReadOnlyContext() throws DataAccessException {
            return ctxFactory.getReadWriteContext(userDn.toString(), password);
        }

        public DirContext getReadWriteContext() throws DataAccessException {
            return ctxFactory.getReadWriteContext(userDn.toString(), password);
        }

		public DirContext getContext(String principal, String credentials)
				throws NamingException {
            return ctxFactory.getReadWriteContext(userDn.toString(), password);
		}
    }
}
