package com.orangeleap.tangerine.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.AbstractAuthenticationToken;

import com.orangeleap.tangerine.security.TangerineAuthenticationToken;
import com.orangeleap.tangerine.type.RoleType;

public final class TangerineUserHelperImpl implements TangerineUserHelper {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private TangerineAuthenticationToken getToken() {
        AbstractAuthenticationToken authentication = (AbstractAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof TangerineAuthenticationToken) {
            return (TangerineAuthenticationToken)authentication;
        }
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserSiteName()
     */
    @Override
    public final String lookupUserSiteName() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getSite();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserName()
     */
    @Override
    public final String lookupUserName() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getName();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserPassword()
     */
    @Override
    public final String lookupUserPassword() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : (String)token.getCredentials();
    }
    
    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.util.TangerineUserHelper#lookupUserId()
     */
    @Override
    public final Long lookupUserId() {
        TangerineAuthenticationToken token = getToken();
        return token == null ? null : token.getPersonId();
    }

    /* (non-Javadoc)
     * @see com.orangeleap.tangerine.security.TangerineUserHelper#lookupUserRoles()
     */
    @Override
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
