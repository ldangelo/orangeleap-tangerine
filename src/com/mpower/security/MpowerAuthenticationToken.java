package com.mpower.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class MpowerAuthenticationToken extends UsernamePasswordAuthenticationToken {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
	private static final long serialVersionUID = 1L;
    private String site;

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
}
