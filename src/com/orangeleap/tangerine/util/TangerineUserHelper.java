package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.security.TangerineAuthenticationDetails;

import java.util.List;

import org.springframework.security.providers.cas.CasAuthenticationToken;

public interface TangerineUserHelper {

    public CasAuthenticationToken getToken();

    public TangerineAuthenticationDetails getDetails();
    
    public String lookupUserSiteName();

    public String lookupUserName();

    public List<String> lookupUserRoles();
    
    public Long lookupUserId();
    
    public void setSystemUserAndSiteName(String siteName);

    
}