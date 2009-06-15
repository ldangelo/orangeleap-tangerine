package com.orangeleap.tangerine.security;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.orangeleap.tangerine.type.AccessType;

public class TangerineAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;

    private String site;

    private Map<String, AccessType> pageAccess;
    
    private Long constituentId;
    
    private Map<String, String> userAttributes;

    public TangerineAuthenticationToken(Object principal, Object credentials, String site) {
        super(principal, credentials);
        this.site = site;
    }

    public TangerineAuthenticationToken(Object principal, Object credentials, String site, GrantedAuthority[] authorities) {
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

	public void setConstituentId(Long constituentId) {
		this.constituentId = constituentId;
	}

	public Long getConstituentId() {
		return constituentId;
	}

	public void setUserAttributes(Map<String, String> userAttributes) {
		this.userAttributes = userAttributes;
	}

	public Map<String, String> getUserAttributes() {
		return userAttributes;
	}
}
