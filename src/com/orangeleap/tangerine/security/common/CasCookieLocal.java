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

package com.orangeleap.tangerine.security.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.context.HttpSessionContextIntegrationFilter;
import org.springframework.security.context.SecurityContextImpl;
import org.springframework.security.providers.cas.CasAuthenticationToken;

public class CasCookieLocal {
	
	public final static String CAS_COOKIE_NAME = "CASTGC";


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
    		if (cookie != null && cookie.getName().equals(CAS_COOKIE_NAME)) {
    			if (cookie.getValue() != null) cas_cookie.set(cookie.getValue());
    		}
    	}
    }
    
    public static Authentication getAuthenticationToken() {
    	HttpServletRequest request = getCasRequest();
    	if (request == null) return null;
    	HttpSession session = request.getSession();
    	if (session == null) return null;
    	SecurityContextImpl si = (SecurityContextImpl)session.getAttribute(HttpSessionContextIntegrationFilter.SPRING_SECURITY_CONTEXT_KEY);
    	if (si == null) return null;
    	return  si.getAuthentication();
    }
    
    public static String getProxyTicketFor(String baseUrl) {
		Authentication token = getAuthenticationToken();
		if (token == null) return null;
		String serviceUrl = baseUrl + "/j_acegi_cas_security_check";
		return ((CasAuthenticationToken)token).getAssertion().getPrincipal().getProxyTicketFor(serviceUrl);
    }

}
