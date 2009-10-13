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

import javax.servlet.http.HttpServletRequest;

public class OrangeLeapRequestLocal {
	

    private static ThreadLocal<HttpServletRequest> _request = new ThreadLocal<HttpServletRequest>() {
        protected synchronized HttpServletRequest initialValue() {
            return null;
        }
    };

    public static HttpServletRequest getRequest() {
        return _request.get();
    }

    public static void setRequest(HttpServletRequest request) {
    	_request.set(request);
    }
    
}
