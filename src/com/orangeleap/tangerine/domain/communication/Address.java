package com.orangeleap.tangerine.domain.communication;

import java.util.Date;

import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;
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
        this.personId = constituentId;
        this.activationStatus = ActivationType.permanent;
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
        eb.append(personId, a.getPersonId()).append(addressLine1, a.getAddressLine1()).append(addressLine2, a.getAddressLine2()).append(addressLine3, a.getAddressLine3()).append(city, a.getCity())
        .append(country, a.getCountry()).append(stateProvince, a.getStateProvince()).append(postalCode, a.getPostalCode());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(personId).append(addressLine1).append(addressLine2).append(addressLine3).append(city).append(country).append(stateProvince).append(postalCode);
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("addressLine1", addressLine1).append("addressLine2", addressLine2).append("addressLine3", addressLine3).append("city", city).
            append("stateProvince", stateProvince).append("postalCode", postalCode).append("country", country).toString();
    }

}