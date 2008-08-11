package com.mpower.security;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.mpower.type.AccessType;

public class MpowerAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private String site;

    private Map<String, AccessType> pageAccess;

    public MpowerAuthenticationToken(Object principal, Object credentials, String site) {
        super(principal, credentials);
        this.site = site;
    }

    public MpowerAuthenticationToken(Object principal, Object credentials, String site, GrantedAuthority[] authorities) {
        super(principal, credentials, authorities);
        this.site = site;
    }

    public String getSite() {
        return this.site;
    }

    public Map<String, AccessType> getPageAccess() {
        return pageAccess;
    }

    public void setPageAccess(Map<String, AccessType> pageAccess) {
        this.pageAccess = pageAccess;
    }
}
