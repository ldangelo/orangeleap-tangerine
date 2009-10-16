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

import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;

public class OrangeLeapSystemAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Object principal;
	private Object credentials;
	private Object details;
	private GrantedAuthority[] ga;
	
	public OrangeLeapSystemAuthenticationToken(String name, Object principal, Object credentials, Object details, GrantedAuthority[] ga) {
		this.name = name;
		this.principal = principal;
		this.credentials = credentials;
		this.details = details;
		this.ga = ga;
	}

	@Override
	public GrantedAuthority[] getAuthorities() {
		return ga;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getDetails() {
		return details;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
	}

	@Override
	public String getName() {
		return name;
	}
	
}
