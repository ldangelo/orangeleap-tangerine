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

import org.springframework.security.AuthenticationManager;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;

/**
 * Created by IntelliJ IDEA.
 * User: ldangelo
 * Date: Jun 17, 2009
 * Time: 1:38:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class TangerineAuthenticationManager implements AuthenticationManager
{
    public TangerineAuthenticationProvider getProvider() {
        return provider;
    }

    public void setProvider(TangerineAuthenticationProvider provider) {
        this.provider = provider;
    }

    private TangerineAuthenticationProvider provider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String site = authentication.getPrincipal().toString();
        String username = site.substring(0,site.indexOf('@'));
        site = site.substring(site.indexOf('@')+1);

        TangerineAuthenticationToken token = new TangerineAuthenticationToken(username,authentication.getCredentials(),site);

        return provider.authenticate(token);
    }
}
