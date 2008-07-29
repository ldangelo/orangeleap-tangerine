package com.mpower.service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;
import com.mpower.security.MpowerAuthenticationToken;

@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    public Site lookupSite(ServletRequest request) {
        return siteDao.readSite(lookupUserSiteName(request));
    }

    public static String lookupUserSiteName(ServletRequest request) {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getSite();
    }

//	private static void storeUser(ServletRequest request, User user) {
//		storeValue((HttpServletRequest) request, SessionValue.USER, user);
//	}

//	private static Object lookupValue(HttpServletRequest request, SessionValue name) {
//		return request.getSession(true).getAttribute(name.toString());
//	}

//	private static void storeValue(HttpServletRequest request, SessionValue name, Object value) {
//		request.getSession(true).setAttribute(name.toString(), value);
//	}
}
