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
import java.util.Date;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class Address extends AbstractCommunicationEntity {
    
    private static final long serialVersionUID = 1L;

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String stateProvince;
    private String country;
    private String postalCode;
    private Date ncoaDate;
    private Date cassDate;

    public Address() { }

    public Address(Long constituentId) {
        this.constituentId = constituentId;
        this.activationStatus = ActivationType.permanent;
    }

	public Address(Long id, Long constituentId) {
		this(constituentId);
	    this.id = id;
	}

    public Address(Long constituentId, String addressLine1, String city, String stateProvince, String postalCode, String country) {
        this(constituentId);
        this.addressLine1 = addressLine1;
        this.city = city;
        this.stateProvince = stateProvince;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
	public void setNcoaDate(Date ncoaDate) {
		this.ncoaDate = ncoaDate;
	}

	public Date getNcoaDate() {
		return ncoaDate;
	}

	public void setCassDate(Date cassDate) {
		this.cassDate = cassDate;
	}

	public Date getCassDate() {
		return cassDate;
	}



    public String getShortDisplay() {
        String shortDisplay = null;
        if (isValid()) {
            shortDisplay = org.apache.commons.lang.StringUtils.substring(addressLine1, 0, 10) + " ... " + org.apache.commons.lang.StringUtils.substring(postalCode, 0, 5);
        }
        return shortDisplay;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (addressLine1, city, stateProvince, postalCode, country) are populated
     * @return true if this Address has all required fields populated
     */
    @Override
    public boolean isValid() {
        return (StringUtils.hasText(addressLine1) &&
                StringUtils.hasText(city) &&
                StringUtils.hasText(stateProvince) &&
                StringUtils.hasText(postalCode) &&
                StringUtils.hasText(country));
    }

    public boolean isFieldEntered() {
        return isAddressEntered() || StringUtils.hasText(getCustomFieldValue(StringConstants.ADDRESS_TYPE));
    }
    
    public boolean isAddressEntered() {
        return StringUtils.hasText(addressLine1) || StringUtils.hasText(addressLine2) || StringUtils.hasText(addressLine3) || StringUtils.hasText(city) || 
            StringUtils.hasText(stateProvince) || StringUtils.hasText(postalCode) || StringUtils.hasText(country);
    }
    
    @Override
    public String getAuditShortDesc() {
    	return getAddressLine1();
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (isAddressEntered() && StringUtils.hasText(getCustomFieldValue(StringConstants.ADDRESS_TYPE)) == false) {
            setCustomFieldValue(StringConstants.ADDRESS_TYPE, StringConstants.UNKNOWN_LOWER_CASE);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address a = (Address) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(constituentId, a.getConstituentId()).append(addressLine1, a.getAddressLine1()).append(addressLine2, a.getAddressLine2()).append(addressLine3, a.getAddressLine3()).append(city, a.getCity())
        .append(country, a.getCountry()).append(stateProvince, a.getStateProvince()).append(postalCode, a.getPostalCode());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(constituentId).append(addressLine1).append(addressLine2).append(addressLine3).append(city).append(country).append(stateProvince).append(postalCode);
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("addressLine1", addressLine1).append("addressLine2", addressLine2).append("addressLine3", addressLine3).append("city", city).
            append("stateProvince", stateProvince).append("postalCode", postalCode).append("country", country).toString();
    }

}