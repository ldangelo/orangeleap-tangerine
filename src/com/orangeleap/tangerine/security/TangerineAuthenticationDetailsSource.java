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

import org.springframework.security.ui.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Create an empty TangerineAuthenticationDetails to be used for the
 * Details property of the Authentication object
 */
public class TangerineAuthenticationDetailsSource implements AuthenticationDetailsSource {

    @Override
    public Object buildDetails(Object context) {

        TangerineAuthenticationDetails details = new TangerineAuthenticationDetails();

        if (context instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) context;

            HttpSession session = req.getSession(false);

            if (session != null) {
                details.setSessionId(session.getId());
            }
        }

        return details;
    }
}
