/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain.communication;

import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlType;
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

	public Email(Long id, Long constituentId) {
		this(constituentId);
	    this.id = id;
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
