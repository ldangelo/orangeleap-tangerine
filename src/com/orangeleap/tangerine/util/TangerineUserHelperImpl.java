package com.orangeleap.tangerine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.Authentication;
import org.springframework.security.userdetails.User;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;

import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RoleType;
import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;

public final class TangerineUserHelperImpl implements TangerineUserHelper, ApplicationContextAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ApplicationContext applicationContext;

    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

    @Override
    public CasAuthenticationToken getToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth instanceof CasAuthenticationToken) {
            return (CasAuthenticationToken) auth;
        }

        return null;
    }

    @Override
    public TangerineAuthenticationDetails getDetails() {

        CasAuthenticationToken token = getToken();

        if (token != null) {
            return (TangerineAuthenticationDetails) token.getDetails();
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserSiteName()
     */
    @Override
    public final String lookupUserSiteName() {
        TangerineAuthenticationDetails token = getDetails();
        return token == null ? null : token.getSite();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserName()
     */
    @Override
    public final String lookupUserName() {
        TangerineAuthenticationDetails token = getDetails();
        return token == null ? null : token.getUserName();
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserId()
     */
    @Override
    public final Long lookupUserId() {
        TangerineAuthenticationDetails token = getDetails();
        return token == null ? null : token.getPersonId();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.security.TangerineUserHelper#lookupUserRoles()
     */
    @Override
    public final List<String> lookupUserRoles() {
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        RoleType greatestRoleType = null;
        for (int i = 0; i < authorities.length; i++) {
            RoleType tempRoleType = RoleType.valueOf(authorities[i].getAuthority());
            if (greatestRoleType == null || greatestRoleType.getRoleRank() < tempRoleType.getRoleRank()) {
                greatestRoleType = tempRoleType;
            }
        }

        List<String> roles = new ArrayList<String>();
        RoleType[] roleTypes = RoleType.values();
        for (int i = 0; i < roleTypes.length; i++) {
            if (roleTypes[i].getRoleRank() <= greatestRoleType.getRoleRank()) {
                roles.add(roleTypes[i].getName());
            }
        }
        return roles;
    }

    // Used by nightly scheduled job functions
    @Override
    public void setSystemUserAndSiteName(String siteName) {
    	// Give system user all roles used by screen definitions
    	SiteService siteservice = (SiteService) applicationContext.getBean("siteService");
    	GrantedAuthority[] ga = siteservice.readDistinctRoles();

        User user = new User("system","",true,true,true,true,ga);
        Assertion assertion = new AssertionImpl("system");

        // Create a fake CAS authentication token
        CasAuthenticationToken token = new CasAuthenticationToken("tangerine-client-key", user, "none",ga,user,assertion);

        // Set the details so that the operations on this class will still work with one
        // of our fabricated auth tokens
        TangerineAuthenticationDetails details = new TangerineAuthenticationDetails();
        details.setUserName("system");
        details.setSite(siteName);
        details.setPersonId(0L);
        token.setDetails(details);

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    
}
