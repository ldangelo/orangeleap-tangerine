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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.providers.AuthenticationProvider;

/*
 * Provides an ordered list of authentication providers to call
 */
public class OrangeLeapAuthenticationProvider implements AuthenticationProvider {

	private static final Log logger = OrangeLeapLogger.getLog(OrangeLeapBindAuthenticator.class);

	private List<AuthenticationProvider> providerList = new ArrayList<AuthenticationProvider>();
    
    public OrangeLeapAuthenticationProvider() {
    }


	@Override
    public boolean supports(Class authentication) {
        return true;
    }


	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		// IMPORTANT: Be sure to clear this from the last authentication when switching authentication methods
		OrangeLeapUsernamePasswordLocal.getOrangeLeapAuthInfo().clear();
		
		for (AuthenticationProvider authenticationProvider: providerList) {
			if (authenticationProvider.supports(authentication.getClass())) {
				logger.debug("Attempting authentication with "+authentication.getClass().getName());
				Authentication result = authenticationProvider.authenticate(authentication);
				// Return first successful authentication
				if (result != null) {
					logger.debug("Authentication succeeded with "+authentication.getClass().getName());
					return result;
				}
			}
		}
		
		logger.debug("No authentication succeeded.");
		return null;
	}


	public void setProviderList(List<AuthenticationProvider> providerList) {
		this.providerList = providerList;
	}


	public List<AuthenticationProvider> getProviderList() {
		return providerList;
	}

}

