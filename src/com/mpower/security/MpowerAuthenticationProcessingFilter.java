package com.mpower.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.util.TextUtils;
import org.springframework.util.Assert;

import com.mpower.util.HttpUtil;

public class MpowerAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    public static final String SITE_KEY = "sitename";

    private String siteParameter = SITE_KEY;

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
        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void onPreAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        HttpUtil.removeCookie("siteCookie", response);
        super.onPreAuthentication(request, response);
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
        HttpUtil.setCookie("siteCookie", obtainSite(request), Integer.MAX_VALUE, response);
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

    public String getSiteParameter() {
        return siteParameter;
    }
}
