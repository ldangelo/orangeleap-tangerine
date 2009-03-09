package com.mpower.domain.model.communication;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.mpower.type.ActivationType;
import com.mpower.util.StringConstants;

public class Email extends AbstractCommunicationEntity  { //SiteAware, ConstituentInfo { TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private String emailType = StringConstants.UNKNOWN_LOWER_CASE;
    private String emailAddress;
    private String emailDisplay;

    public Email() { }

    public Email(Long personId) {
        this.personId = personId;
        this.emailType = StringConstants.UNKNOWN_LOWER_CASE;  
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

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (emailAddress) are populated
     * @return true if this Email has all required fields populated
     */
    @Override
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
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("emailAddress", emailAddress).append("emailDisplay", emailDisplay).append("emailType", emailType).toString();
    }

    @Override
    public String getCommunicationType() {
        return emailType;
    }
}
