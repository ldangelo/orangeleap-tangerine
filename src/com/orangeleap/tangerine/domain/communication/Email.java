package com.orangeleap.tangerine.domain.communication;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class Email extends AbstractCommunicationEntity  { //SiteAware, ConstituentInfo { TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private String emailAddress;
    private String emailDisplay;

    public Email() { }

    public Email(Long constituentId) {
        this.constituentId = constituentId;
        this.activationStatus = ActivationType.permanent;
    }

    public Email(Long constituentId, String emailAddress) {
        this(constituentId);
        this.emailAddress = emailAddress;
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

    public boolean isFieldEntered() {
        return isEmailEntered() || StringUtils.hasText(getCustomFieldValue(StringConstants.EMAIL_TYPE));
    }
    
    public boolean isEmailEntered() {
        return StringUtils.hasText(emailAddress);
    }
    
    @Override
    public String getAuditShortDesc() {
    	return getEmailAddress();
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (isEmailEntered() && StringUtils.hasText(getCustomFieldValue(StringConstants.EMAIL_TYPE)) == false) {
            setCustomFieldValue(StringConstants.EMAIL_TYPE, StringConstants.UNKNOWN_LOWER_CASE);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Email)) {
            return false;
        }
        Email e = (Email) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getConstituentId(), e.getConstituentId()).append(emailAddress, e.getEmailAddress());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getConstituentId()).append(emailAddress);
        return hcb.hashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("emailAddress", emailAddress).append("emailDisplay", emailDisplay).toString();
    }

}
