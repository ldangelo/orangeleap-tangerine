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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.springframework.security.Authentication;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContextImpl;

public class CasCookieLocal {

    protected static final Log logger = OLLogger.getLog(RulesStack.class);

    private static ThreadLocal<String> cas_cookie = new ThreadLocal<String>() {
        protected synchronized String initialValue() {
            return null;
        }
    };

    private static ThreadLocal<HttpServletRequest> cas_request = new ThreadLocal<HttpServletRequest>() {
        protected synchronized HttpServletRequest initialValue() {
            return null;
        }
    };

    public static String getCasCookie() {
        return cas_cookie.get();
    }

    public static HttpServletRequest getCasRequest() {
        return cas_request.get();
    }

    public static void setCasCookie(HttpServletRequest request) {
    	cas_request.set(request);
    	cas_cookie.remove();
    	if (request != null && request.getCookies() != null) for (Cookie cookie : request.getCookies()) {
    		if (cookie != null && cookie.getName().equals(StringConstants.CAS_COOKIE_NAME)) {
    			if (cookie.getValue() != null) cas_cookie.set(cookie.getValue());
    		}
    	}
    }
    
    public static Authentication getAuthenticationToken() {
    	return  ((SecurityContextImpl)getCasRequest().getSession().getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY)).getAuthentication();
    }

}
