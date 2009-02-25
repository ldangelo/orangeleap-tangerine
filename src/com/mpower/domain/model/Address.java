package com.mpower.domain.model;

import com.mpower.domain.*;
import com.mpower.domain.CustomField;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.util.AddressCustomFieldMap;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Address implements Customizable, Inactivatible, GeneratedId, Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private final Log logger = LogFactory.getLog(getClass());

    private Long id;
    private Long personId;

    private String addressType = "unknown";

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String stateProvince;

    private String country;

    private String postalCode;
    private Date createDate;
    private Date updateDate;

    private boolean receiveMail = false;

    // either permanent, temporary, or seasonal
    private String activationStatus;

    // only care about month/day/year, not time
    private Date temporaryStartDate;

    // only care about month/day/year, not time
    private Date temporaryEndDate;

    // only care about month/day, not year or time
    private Date seasonalStartDate;

    // only care about month/day, not year or time
    private Date seasonalEndDate;

    private boolean inactive = false;

    private String comments;

    private List<AddressCustomField> addressCustomFields;

    // only meaningful for Permanent addresses, and indicates when date becomes effective (ex. they are moving the first of next month)
    private Date effectiveDate;

    private Map<String, CustomField> customFieldMap = null;
    private Map<String, String> fieldLabelMap = null;
    private Map<String, Object> fieldValueMap = null;
    private Map<String, FieldDefinition> fieldTypeMap = null;
    private boolean userCreated = false;

    public Address() {
    }

    public Address(Person person) {
        this.personId = person.getId();
        this.addressType = "unknown";  // defaulting to 'home' would change the home address on the constituent whenever a new payment type is created with a new address.
        this.activationStatus = "permanent";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId; 
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isReceiveMail() {
        return receiveMail;
    }

    public void setReceiveMail(boolean receiveMail) {
        this.receiveMail = receiveMail;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public Date getTemporaryStartDate() {
        return temporaryStartDate;
    }

    public void setTemporaryStartDate(Date temporaryStartDate) {
        this.temporaryStartDate = temporaryStartDate;
    }

    public Date getTemporaryEndDate() {
        return temporaryEndDate;
    }

    public void setTemporaryEndDate(Date temporaryEndDate) {
        this.temporaryEndDate = temporaryEndDate;
    }

    public Date getSeasonalStartDate() {
        return seasonalStartDate;
    }

    public void setSeasonalStartDate(Date seasonalStartDate) {
        this.seasonalStartDate = seasonalStartDate;
    }

    public Date getSeasonalEndDate() {
        return seasonalEndDate;
    }

    public void setSeasonalEndDate(Date seasonalEndDate) {
        this.seasonalEndDate = seasonalEndDate;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<AddressCustomField> getAddressCustomFields() {
        if (addressCustomFields == null) {
            addressCustomFields = new ArrayList<AddressCustomField>();
        }
        return addressCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            //customFieldMap = AddressCustomFieldMap.buildCustomFieldMap(getAddressCustomFields(), this);
        }
        return customFieldMap;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }



    //@Override
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    //@Override
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    //@Override
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    //@Override
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    //@Override
    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    //@Override
    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
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

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (activationStatus != null) {
            if ("permanent".equals(getActivationStatus())) {
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
            } else if ("seasonal".equals(getActivationStatus())) {
                setEffectiveDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
            } else if ("temporary".equals(getActivationStatus())) {
                setEffectiveDate(null);
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
            }
        }
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