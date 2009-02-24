package com.mpower.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;

import com.mpower.security.MpowerAuthenticationToken;
import com.mpower.type.RoleType;

public final class TangerineUserHelperImpl implements TangerineUserHelper {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    /* (non-Javadoc)
     * @see com.mpower.security.TangerineUserHelper#lookupUserSiteName()
     */
    public final String lookupUserSiteName() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication instanceof MpowerAuthenticationToken ? ((MpowerAuthenticationToken)authentication).getSite() : null;
    }

    /* (non-Javadoc)
     * @see com.mpower.security.TangerineUserHelper#lookupUserName()
     */
    public final String lookupUserName() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication instanceof MpowerAuthenticationToken ? ((MpowerAuthenticationToken)authentication).getName() : null;
    }

    /* (non-Javadoc)
     * @see com.mpower.security.TangerineUserHelper#lookupUserPassword()
     */
    public final String lookupUserPassword() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication instanceof MpowerAuthenticationToken ? (String)((MpowerAuthenticationToken)authentication).getCredentials() : null;
    }

    /* (non-Javadoc)
     * @see com.mpower.security.TangerineUserHelper#lookupUserRoles()
     */
    public final List<String> lookupUserRoles() {
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
}
