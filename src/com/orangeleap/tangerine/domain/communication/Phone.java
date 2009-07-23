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

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
public class Phone extends AbstractCommunicationEntity { // SiteAware, ConstituentInfo TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private String number;
    private String provider;
    private String sms;
    private boolean receiveCorrespondenceText = false;

    public Phone() {
    }

    public Phone(Long constituentId) {
        this.constituentId = constituentId;
        this.activationStatus = ActivationType.permanent;
    }

	public Phone(Long id, Long constituentId) {
		this(constituentId);
	    this.id = id;
	}

    public Phone(Long constituentId, String number) {
        this(constituentId);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public boolean isReceiveCorrespondenceText() {
        return receiveCorrespondenceText;
    }

    public void setReceiveCorrespondenceText(boolean receiveCorrespondenceText) {
        this.receiveCorrespondenceText = receiveCorrespondenceText;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (number) are populated
     *
     * @return true if this Address has all required fields populated
     */
    @Override
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(number));
    }

    public boolean isFieldEntered() {
        return isPhoneEntered() || StringUtils.hasText(getCustomFieldValue(StringConstants.PHONE_TYPE));
    }

    public boolean isPhoneEntered() {
        return StringUtils.hasText(number);
    }

    @Override
    public String getAuditShortDesc() {
        return getNumber();
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (isPhoneEntered() && StringUtils.hasText(getCustomFieldValue(StringConstants.PHONE_TYPE)) == false) {
            setCustomFieldValue(StringConstants.PHONE_TYPE, StringConstants.UNKNOWN_LOWER_CASE);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Phone)) {
            return false;
        }
        Phone p = (Phone) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getConstituentId(), p.getConstituentId()).append(number, p.getNumber());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getConstituentId()).append(number);
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("number", number).append("provider", provider).append("sms", sms).append("receiveCorrespondenceText", receiveCorrespondenceText).toString();
    }

}
