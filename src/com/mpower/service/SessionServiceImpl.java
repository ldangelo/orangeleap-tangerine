package com.mpower.service;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;
import com.mpower.domain.User;
import com.mpower.security.MpowerAuthenticationToken;

@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    public User lookupUser(ServletRequest request) {
		// -- Remove once login has been implemented
//		System.out.println("WARNING: lookup user hard-coded.  Remove once login has been implemented");
//		User u = new User();
//		u.setId(new Long(1));
//		Site s = new Site();
//		s.setName("1");
//		u.setSite(s);
//		storeUser(request, u);
		// -- Remove once login has been implemented
//		return (User) lookupValue((HttpServletRequest) request, SessionValue.USER);
        return userService.getUser(lookupUserId(request));
	}

    public Site lookupSite(ServletRequest request) {
        return siteDao.readSite(lookupUserSiteName(request));
    }

    public static String lookupUserSiteName(ServletRequest request) {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getSite();
    }

    public static Long lookupUserId(ServletRequest request) {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
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
