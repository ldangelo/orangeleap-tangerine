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

package com.mpower.security.common;

import java.util.Map;
import java.util.TreeMap;

public class OrangeLeapUsernamePasswordLocal {
	
	public static final String SITE = "site";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";

    private static ThreadLocal<Map<String, Object>> authinfo = new ThreadLocal<Map<String, Object>>() {
        protected synchronized Map<String, Object> initialValue() {
            return new TreeMap<String,Object>();
        }
    };

    public static Map<String, Object> getOrangeLeapAuthInfo() {
        return authinfo.get();
    }


}
