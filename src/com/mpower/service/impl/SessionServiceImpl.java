package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;
import org.springframework.stereotype.Component;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;
import com.mpower.security.MpowerAuthenticationToken;
import com.mpower.service.SessionService;
import com.mpower.type.RoleType;

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
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication instanceof MpowerAuthenticationToken ? ((MpowerAuthenticationToken)authentication).getSite() : null;
    }

    public static String lookupUserName() {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static String lookupUserPassword() {
        MpowerAuthenticationToken authentication = (MpowerAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getCredentials();
    }

    public static List<String> lookupUserRoles() {
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        RoleType greatestRoleType = null;
        for (int i = 0; i < authorities.length; i++) {
            RoleType tempRoleType = RoleType.valueOf(authorities[i].getAuthority());
            if (greatestRoleType == null || greatestRoleType.getRoleRank() < tempRoleType.getRoleRank()) {
                greatestRoleType = tempRoleType;
            }
        }

        List<String> roles = new ArrayList<String>();
        RoleType[] roleTypes = RoleType.values();
        for (int i = 0; i < roleTypes.length; i++) {
            if (roleTypes[i].getRoleRank() <= greatestRoleType.getRoleRank()) {
                roles.add(roleTypes[i].getName());
            }
        }
        return roles;
    }

    // private static void storeUser(ServletRequest request, User user) {
    // storeValue((HttpServletRequest) request, SessionValue.USER, user);
    // }

    // private static Object lookupValue(HttpServletRequest request, SessionValue name) {
    // return request.getSession(true).getAttribute(name.toString());
    // }

    // private static void storeValue(HttpServletRequest request, SessionValue name, Object value) {
    // request.getSession(true).setAttribute(name.toString(), value);
    // }
}
