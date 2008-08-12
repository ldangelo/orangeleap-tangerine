package com.mpower.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;
import com.mpower.security.MpowerAuthenticationToken;

@Component("sessionService")
public class SessionServiceImpl implements SessionService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    @Resource(name = "siteDao")
    private SiteDao siteDao;

    public Site lookupSite() {
        return siteDao.readSite(lookupUserSiteName());
    }

    public static String lookupUserSiteName() {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getSite();
    }

    public static List<String> lookupUserRoles() {
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> roleList = null;
        if (authorities != null) {
            roleList = new ArrayList<String>();
            for (GrantedAuthority authority : authorities) {
                roleList.add(authority.getAuthority());
            }
        }
        return roleList;
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
