package com.mpower.web.common;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.mpower.domain.Site;
import com.mpower.domain.User;

public class SessionUtils {

	public static User lookupUser(ServletRequest request) {
		// -- Remove once login has been implemented
		System.out.println("WARNING: lookup user hard-coded.  Remove once login has been implemented");
		User u = new User();
		u.setId(new Long(1));
		Site s = new Site();
		s.setName("1");
		u.setSite(s);
		storeUser(request, u);
		// -- Remove once login has been implemented
		return (User) lookupValue((HttpServletRequest) request, SessionValue.USER);
	}

	private static void storeUser(ServletRequest request, User user) {
		storeValue((HttpServletRequest) request, SessionValue.USER, user);
	}

	private static Object lookupValue(HttpServletRequest request, SessionValue name) {
		return request.getSession(true).getAttribute(name.toString());
	}

	private static void storeValue(HttpServletRequest request, SessionValue name, Object value) {
		request.getSession(true).setAttribute(name.toString(), value);
	}
}
