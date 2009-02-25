package com.mpower.domain.model.communication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mpower.domain.GeneratedId;
import com.mpower.type.ActivationType;
import com.mpower.util.StringConstants;

public class Address extends AbstractCommunication implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private String addressType = StringConstants.UNKNOWN;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String stateProvince;
    private String country;
    private String postalCode;
    private List<AddressCustomField> addressCustomFields;

    public Address() { }

    public Address(Long personId) {
        this.personId = personId;
        this.addressType = StringConstants.UNKNOWN;  // defaulting to 'home' would change the home address on the constituent whenever a new payment type is created with a new address.
        this.activationStatus = ActivationType.permanent;
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

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public List<AddressCustomField> getAddressCustomFields() {
        if (addressCustomFields == null) {
            addressCustomFields = new ArrayList<AddressCustomField>();
        }
        return addressCustomFields;
    }

    public String getShortDisplay() {
        String shortDisplay = null;
        if (isValid()) {
            shortDisplay = StringUtils.substring(addressLine1, 0, 10) + " ... " + StringUtils.substring(postalCode, 0, 5);
        }
        return shortDisplay;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (addressLine1, city, stateProvince, postalCode, country) are populated
     * @return true if this Address has all required fields populated
     */
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(addressLine1) &&
                org.springframework.util.StringUtils.hasText(city) &&
                org.springframework.util.StringUtils.hasText(stateProvince) &&
                org.springframework.util.StringUtils.hasText(postalCode) &&
                org.springframework.util.StringUtils.hasText(country));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address a = (Address) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(personId, a.getPersonId()).append(addressType, a.getAddressType()).append(activationStatus, a.getActivationStatus()).append(addressLine1, a.getAddressLine1()).append(addressLine2, a.getAddressLine2()).append(addressLine3, a.getAddressLine3()).append(city, a.getCity())
        .append(country, a.getCountry()).append(stateProvince, a.getStateProvince()).append(postalCode, a.getPostalCode());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(personId).append(addressType).append(activationStatus).append(addressLine1).append(addressLine2).append(addressLine3).append(city).append(country).append(stateProvince).append(postalCode);
        return hcb.hashCode();
    }
}