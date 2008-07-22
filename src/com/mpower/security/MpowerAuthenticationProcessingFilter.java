package com.mpower.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.util.TextUtils;
import org.springframework.util.Assert;

import com.mpower.domain.User;
import com.mpower.service.UserService;

public class MpowerAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

    public static final String SITE_KEY = "sitename";

    private String siteParameter = SITE_KEY;

    @Resource(name = "userService")
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request) throws AuthenticationException {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String site = obtainSite(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        username = username.trim();

        MpowerAuthenticationToken authRequest = new MpowerAuthenticationToken(username, password, site);

        // Place the last username attempted into HttpSession for views
        HttpSession session = request.getSession(false);

        if (session != null || getAllowSessionCreation()) {
            request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, TextUtils.escapeEntities(username));
        }

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        Authentication authentication = getAuthenticationManager().authenticate(authRequest);
        if (authentication.isAuthenticated()) {
            User user = userService.authenticateUser(username, site);
            if (user != null) {
                ((MpowerAuthenticationToken) authentication).setUserId(user.getId());
            } else {
                ((MpowerAuthenticationToken) authentication).setAuthenticated(false);
                throw new UsernameNotFoundException("User not found");
            }
        }
        return authentication;
    }

    protected String obtainSite(HttpServletRequest request) {
        return request.getParameter(siteParameter);
    }

    protected void setDetails(HttpServletRequest request, MpowerAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setSiteParameter(String siteParameter) {
        Assert.hasText(siteParameter, "Site parameter must not be empty or null");
        this.siteParameter = siteParameter;
    }

    String getSiteParameter() {
        return siteParameter;
    }
}
