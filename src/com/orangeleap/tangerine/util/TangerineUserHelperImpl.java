/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RoleType;

public class TangerineUserHelperImpl implements TangerineUserHelper, ApplicationContextAware {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private ApplicationContext applicationContext;
    

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public CasAuthenticationToken getToken() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof CasAuthenticationToken) {
            return (CasAuthenticationToken) authentication;
        }
        return null;
    }

    @Override
    public TangerineAuthenticationDetails getDetails() {
    	CasAuthenticationToken cat = getToken();
    	if (cat == null) return null;
    	return (TangerineAuthenticationDetails)cat.getDetails();
    }

    /* (non-Javadoc)
    * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserSiteName()
    */
    @Override
    public String lookupUserSiteName() {
        TangerineAuthenticationDetails details = getDetails();
        return details == null ? null : details.getSite();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserName()
     */
    @Override
    public String lookupUserName() {
    	TangerineAuthenticationDetails details = getDetails();
        return details == null ? null : details.getUserName();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserPassword()
     */
    @Override
    public String lookupUserPassword() {
    	CasAuthenticationToken token = getToken();
        return token == null ? null : (String) token.getCredentials();
    }

    /* (non-Javadoc)
    * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserId()
    */
    @Override
    public Long lookupUserId() {
    	TangerineAuthenticationDetails details = getDetails();
        return details == null ? null : details.getConstituentId();
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
    
    @Override
    public Map<String, String> getSiteOptionsMap() {
    	return ((SiteService)applicationContext.getBean("siteService")).getSiteOptionsMap();
    }


    // Used by nightly scheduled job functions
    @Override
    @Transactional
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
        details.setConstituentId(0L);
        token.setDetails(details);
        
        SecurityContextHolder.getContext().setAuthentication(token);
    }


}
