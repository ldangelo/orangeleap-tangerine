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

import java.util.List;
import java.util.Map;

import org.springframework.security.providers.cas.CasAuthenticationToken;

import com.orangeleap.tangerine.domain.SiteOption;
import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;

public interface TangerineUserHelper {

    public CasAuthenticationToken getToken();

    public TangerineAuthenticationDetails getDetails();

    public String lookupUserSiteName();

    public String lookupUserName();

    public String lookupUserPassword();

    public List<String> lookupUserRoles();

    public Long lookupUserId();

    public Map<String, String> getSiteOptionsMap();

    public String getSiteOptionByName(String name);

    public void setSystemUserAndSiteName(String siteName);


}
