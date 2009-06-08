package com.orangeleap.tangerine.security;

import com.orangeleap.tangerine.type.AccessType;

import java.io.Serializable;
import java.util.Map;

import org.springframework.security.concurrent.SessionIdentifierAware;

/**
 * This Object carries the Tangerine-specific information which is needed
 * in the Authentication object. This information is populated in the
 * {@link TangerineSessionInformationFilter}. Note that this class must
 * implement the SessionIdentifierAware interface in order to work with
 * the ConcurrentSessionController for preventing multiple logins for
 * the same User.
 */
public class TangerineAuthenticationDetails implements SessionIdentifierAware, Serializable {

    private static final long serialVersionUID = 13L;

    private String site;

    private String userName;

    private Map<String, AccessType> pageAccess;

    private Long personId;

    private Map<String, String> userAttributes;

    private String firstName="";

    private String lastName="";

    private String sessionId;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, AccessType> getPageAccess() {
        return pageAccess;
    }

    public void setPageAccess(Map<String, AccessType> pageAccess) {
        this.pageAccess = pageAccess;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Map<String, String> getUserAttributes() {
        return userAttributes;
    }

    public void setUserAttributes(Map<String, String> userAttributes) {
        this.userAttributes = userAttributes;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
