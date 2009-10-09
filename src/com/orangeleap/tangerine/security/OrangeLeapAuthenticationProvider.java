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

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.cas.CasAuthenticationToken;

/*
 * Provides an ordered list of authentication providers to call
 */
public class OrangeLeapAuthenticationProvider implements AuthenticationProvider {

	private List<AuthenticationProvider> providerList = new ArrayList<AuthenticationProvider>();
    
    public OrangeLeapAuthenticationProvider() {
    }


    @SuppressWarnings("unchecked")
    public boolean supports(Class authentication) {
        return (CasAuthenticationToken.class.isAssignableFrom(authentication));
    }


	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		// IMPORTANT: Be sure to clear this from the last authentication when switching authentication methods
		OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo().clear();
		
		List<AuthenticationException> exceptions = new ArrayList<AuthenticationException>();
		
		for (AuthenticationProvider authenticationProvider: providerList) {
			if (authenticationProvider.supports(authentication.getClass())) {
				try {
					Authentication result = authenticationProvider.authenticate(authentication);
					// Return first successful authentication
					return result;
				} catch (AuthenticationException e) {
					exceptions.add(e);
				}
			}
		}
		
		// throw last failed authentication
		throw exceptions.get(exceptions.size()-1);
	}


	public void setProviderList(List<AuthenticationProvider> providerList) {
		this.providerList = providerList;
	}


	public List<AuthenticationProvider> getProviderList() {
		return providerList;
	}

}

