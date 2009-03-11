package com.orangeleap.tangerine.util;

import java.util.List;

public interface TangerineUserHelper {

    public String lookupUserSiteName();

    public String lookupUserName();

    public String lookupUserPassword();

    public List<String> lookupUserRoles();
    
    public Long lookupUserId();
}