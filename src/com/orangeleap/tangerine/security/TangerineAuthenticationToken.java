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

package com.orangeleap.tangerine.security;

import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import java.util.Map;

public class TangerineAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private String site;

    private Map<String, AccessType> pageAccess;

    private Long constituentId;

    private Map<String, String> userAttributes;

    public TangerineAuthenticationToken(Object principal, Object credentials, String site) {
        super(principal, credentials);
        this.site = site;
    }

    public TangerineAuthenticationToken(Object principal, Object credentials, String site, GrantedAuthority[] authorities) {
        super(principal, credentials, authorities);
        this.site = site;
    }
    
    public String getShortName() {
    	String shortname = getName();
    	int i = shortname.indexOf("@");
    	if (i > 0) shortname = shortname.substring(0,i);
    	return shortname;
    }

    public String getSite() {
        return this.site;
    }

    public Map<String, AccessType> getPageAccess() {
        return pageAccess;
    }

    public void setPageAccess(Map<String, AccessType> pageAccess) {
        this.pageAccess = pageAccess;
    }

    public void setConstituentId(Long constituentId) {
        this.constituentId = constituentId;
    }

    public Long getConstituentId() {
        return constituentId;
    }

    public void setUserAttributes(Map<String, String> userAttributes) {
        this.userAttributes = userAttributes;
    }

    public Map<String, String> getUserAttributes() {
        return userAttributes;
    }
}
