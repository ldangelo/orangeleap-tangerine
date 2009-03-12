package com.orangeleap.tangerine.util;

import java.util.List;

import com.orangeleap.tangerine.security.TangerineAuthenticationToken;

public interface TangerineUserHelper {

    public TangerineAuthenticationToken getToken();
    
    public String lookupUserSiteName();

    public String lookupUserName();

    public String lookupUserPassword();

    public List<String> lookupUserRoles();
    
    public Long lookupUserId();
}