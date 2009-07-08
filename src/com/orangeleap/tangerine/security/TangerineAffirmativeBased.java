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

import java.util.Map;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.vote.AffirmativeBased;

import com.orangeleap.tangerine.type.AccessType;

/**
 * Simple concrete implementation of {@link org.springframework.security.AccessDecisionManager} that grants access if any <code>AccessDecisionVoter</code> returns an affirmative response.
 */
public class TangerineAffirmativeBased extends AffirmativeBased {
    /*
     * (non-Javadoc)
     * @see org.springframework.security.vote.AffirmativeBased#decide(org.springframework .security.Authentication, java.lang.Object, org.springframework.security.ConfigAttributeDefinition)
     */
    @Override
    public void decide(Authentication authentication, Object object, ConfigAttributeDefinition config) throws AccessDeniedException {
        int deny = 0;

        if (authentication instanceof TangerineAuthenticationToken) {
            if (object instanceof FilterInvocation) {
                String requestUrl = ((FilterInvocation) object).getRequestUrl();
                // chop off request parameters
                requestUrl = (requestUrl != null && requestUrl.indexOf('?') > -1) ? requestUrl.substring(0, requestUrl.indexOf('?')) : requestUrl;
                Map<String, AccessType> pageAccess = ((TangerineAuthenticationToken) authentication).getPageAccess();
                AccessType accessType = null;
                if (pageAccess != null) {
                    accessType = pageAccess.get(requestUrl);
                }
                if (accessType != null && AccessType.DENIED.equals(accessType)) {
                    deny++;
                }
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        }

        super.decide(authentication, object, config);
    }
}
