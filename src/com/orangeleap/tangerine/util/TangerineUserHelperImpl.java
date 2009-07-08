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

import com.orangeleap.tangerine.security.TangerineAuthenticationToken;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.RoleType;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    public TangerineAuthenticationToken getToken() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof TangerineAuthenticationToken) {
            return (TangerineAuthenticationToken) authentication;
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
        return token == null ? null : (String) token.getCredentials();
    }

    /* (non-Javadoc)
    * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserId()
    */
    @Override
    public Long lookupUserId() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getConstituentId();
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
