package com.orangeleap.tangerine.security;

import org.springframework.security.ui.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  Create an empty TangerineAuthenticationDetails to be used for the
 *  Details property of the Authentication object
 */
public class TangerineAuthenticationDetailsSource implements AuthenticationDetailsSource{

    @Override
    public Object buildDetails(Object context) {

        TangerineAuthenticationDetails details = new TangerineAuthenticationDetails();

        if(context instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) context;

            HttpSession session = req.getSession(false);

            if(session != null) {
                details.setSessionId( session.getId() );
            }
        }

        return details;
    }
}
