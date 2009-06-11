package com.orangeleap.tangerine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.security.TangerineAuthenticationToken;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RoleType;

public class TangerineUserHelperImpl implements TangerineUserHelper, ApplicationContextAware {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private ApplicationContext applicationContext;

    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}


    
    @Override
    public TangerineAuthenticationToken getToken() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof TangerineAuthenticationToken) {
            return (TangerineAuthenticationToken)authentication;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserSiteName()
     */
    @Override
    public String lookupUserSiteName() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getSite();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserName()
     */
    @Override
    public String lookupUserName() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getName();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserPassword()
     */
    @Override
    public String lookupUserPassword() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : (String)token.getCredentials();
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserId()
     */
    @Override
    public Long lookupUserId() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getPersonId();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.security.TangerineUserHelper#lookupUserRoles()
     */
    @Override
    @Transactional
    public List<String> lookupUserRoles() {
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
    @Transactional
    public void setSystemUserAndSiteName(String siteName) {
    	// Give system user all roles used by screen definitions
    	SiteService siteservice = (SiteService) applicationContext.getBean("siteService");
    	GrantedAuthority[] ga = siteservice.readDistinctRoles();
    	TangerineAuthenticationToken token = new TangerineAuthenticationToken("system", "", siteName, ga);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    
}
