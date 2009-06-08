package com.orangeleap.tangerine.security;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.User;
import org.springframework.security.providers.cas.CasAuthenticationToken;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.vote.AffirmativeBased;
import org.springframework.beans.factory.annotation.Autowired;

import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.domain.Person;

/**
 * Custom AccessDecisionManager which first check the Trangerine PageAccess collection
 * for access control to URIs, then defers to normal Spring Voters. This will first check
 * to see if the pageAcess variable is set on the TangerineAuthenticationDetails object,
 * and if not, will read it from the PageCustomizationService.
 */
public class TangerineAffirmativeBased extends AffirmativeBased {

    /*
     * (non-Javadoc)
     * @see org.springframework.security.vote.AffirmativeBased#decide(org.springframework .security.Authentication, java.lang.Object, org.springframework.security.ConfigAttributeDefinition)
     */
    @Override
    public void decide(Authentication authentication, Object object, ConfigAttributeDefinition config) throws AccessDeniedException {

        if (authentication instanceof CasAuthenticationToken) {

            CasAuthenticationToken token = (CasAuthenticationToken) authentication;

            if (object instanceof FilterInvocation) {
                String requestUrl = ((FilterInvocation) object).getRequestUrl();
                // chop off request parameters
                requestUrl = (requestUrl != null && requestUrl.indexOf('?') > -1) ? requestUrl.substring(0, requestUrl.indexOf('?')) : requestUrl;

                TangerineAuthenticationDetails details = (TangerineAuthenticationDetails) authentication.getDetails();
                Map<String, AccessType> pageAccess = details.getPageAccess();
                AccessType accessType = null;

                if(pageAccess == null) {
                    pageAccess = details.getPageAccess();
                    accessType = pageAccess.get(requestUrl);
                } else {
                    accessType = pageAccess.get(requestUrl);
                }
                if (accessType != null && AccessType.DENIED.equals(accessType)) {
                    throw new AccessDeniedException(messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
                }
            }
        }

        super.decide(authentication, object, config);
    }
}
