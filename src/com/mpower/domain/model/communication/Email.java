package com.mpower.domain.model.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mpower.domain.GeneratedId;
import com.mpower.type.ActivationType;
import com.mpower.util.StringConstants;

public class Email extends AbstractCommunication implements GeneratedId, Serializable { //SiteAware, ConstituentInfo { TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private String emailType = StringConstants.UNKNOWN;
    private String emailAddress;
    private String emailDisplay;
    private List<EmailCustomField> emailCustomFields;

    public Email() { }

    public Email(Long personId) {
        this.personId = personId;
        this.emailType = StringConstants.UNKNOWN;  
        this.activationStatus = ActivationType.permanent;
    }

    public Email(Long personId, String emailAddress) {
        this(personId);
        this.emailAddress = emailAddress;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailDisplay() {
        return emailDisplay;
    }

    public void setEmailDisplay(String emailDisplay) {
        this.emailDisplay = emailDisplay;
    }

    public List<EmailCustomField> getEmailCustomFields() {
        if (emailCustomFields == null) {
            emailCustomFields = new ArrayList<EmailCustomField>();
        }
        return emailCustomFields;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (emailAddress) are populated
     * @return true if this Email has all required fields populated
     */
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(emailAddress));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Email)) {
            return false;
        }
        Email e = (Email) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getPersonId(), e.getPersonId()).append(emailType, e.getEmailType()).append(activationStatus, e.getActivationStatus()).append(emailAddress, e.getEmailAddress()).append(emailDisplay, e.getEmailDisplay());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getPersonId()).append(emailType).append(activationStatus).append(emailAddress).append(emailDisplay);
        return hcb.hashCode();
    }
}
