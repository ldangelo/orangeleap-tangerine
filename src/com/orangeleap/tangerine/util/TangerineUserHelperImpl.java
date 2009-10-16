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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;
import com.orangeleap.tangerine.security.common.OrangeLeapSystemAuthenticationToken;
import com.orangeleap.tangerine.service.SiteService;

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
    public Authentication getToken() {
    	return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public TangerineAuthenticationDetails getDetails() {
    	Authentication cat = getToken();
    	if (cat == null) return null;
    	Object obj = cat.getDetails();
    	if (!(obj instanceof TangerineAuthenticationDetails)) return null;
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
    	Authentication token = getToken();
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
        List<String> roles = new ArrayList<String>();
        for (int i = 0; i < authorities.length; i++) {
        	roles.add(authorities[i].getAuthority());
        }
        return roles;
    }

    @Override
    public Map<String, String> getSiteOptionsMap() {
    	return getSiteService().getSiteOptionsMap();
    }

    @Override
    public String getSiteOptionByName(String name) {
    	Map<String, String> soMap = this.getSiteOptionsMap();
    	return soMap.get(name).toString();

    	//return ((SiteService)applicationContext.getBean("siteService")).getSiteOptionById(id).getOptionValue().toString();
    }
    
    private SiteService getSiteService() {
    	return ((SiteService)applicationContext.getBean("siteService"));
    }

    public Site getSite(String sitename) {
    	return getSiteService().readSite(sitename);
    }

    // Used by nightly scheduled job functions
    @Override
    @Transactional
    public void setSystemUserAndSiteName(String siteName) {
       
    	// Give system user super admin role
        final GrantedAuthority[] ga = new GrantedAuthority[] {new GrantedAuthorityImpl("ROLE_SUPER_ADMIN"), new GrantedAuthorityImpl("ROLE_USER")};
        
        final Site site = getSite(siteName);
       
        // Set the details so that the operations on this class will still work with one
        // of our fabricated auth tokens
        final TangerineAuthenticationDetails details = new TangerineAuthenticationDetails();
        details.setUserName(site.getJasperUserId());
        details.setSite(siteName);
        details.setConstituentId(0L);

        // Create a fake authentication token for tangerine system user
        Authentication token = new OrangeLeapSystemAuthenticationToken(
        		site.getJasperUserId(), site.getJasperUserId(), site.getJasperPassword(), details, ga);
        

        SecurityContextHolder.getContext().setAuthentication(token);
    }


}
