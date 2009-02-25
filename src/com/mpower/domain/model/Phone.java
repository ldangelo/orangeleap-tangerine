package com.mpower.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.customization.FieldDefinition;

public class Phone implements GeneratedId, Serializable { // SiteAware, Customizable, ConstituentInfo, Inactivatible, Serializable { TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long personId;
    private String phoneType = "unknown";
    private String number;
    private String provider;
    private String sms;
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
    private List<PhoneCustomField> phoneCustomFields;
    // only meaningful for Permanent phones, and indicates when date becomes effective (ex. they are moving the first of next month)
    private Date effectiveDate;
    private final Map<String, CustomField> customFieldMap = null;
    private Map<String, String> fieldLabelMap = null;
    private Map<String, Object> fieldValueMap = null;
    private Map<String, FieldDefinition> fieldTypeMap = null;
    private boolean userCreated = false;

    public Phone() { }

    public Phone(Long personId) {
        this.personId = personId;
        this.phoneType = "unknown"; // defaulting to 'home' would change the home phone on the constituent whenever a new payment type is created with a new phone.
        this.activationStatus = "permanent";
    }
    
    public Phone(Long personId, String number) {
        this(personId);
        this.number = number;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
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

    // @Override
    // TODO: put back for IBatis
    public boolean isInactive() {
        return inactive;
    }

    // @Override
    // TODO: put back for IBatis
    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<PhoneCustomField> getPhoneCustomFields() {
        if (phoneCustomFields == null) {
            phoneCustomFields = new ArrayList<PhoneCustomField>();
        }
        return phoneCustomFields;
    }

    @SuppressWarnings("unchecked")
    // @Override
    // TODO: put back for IBatis
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            // customFieldMap = PhoneCustomFieldMap.buildCustomFieldMap(getPhoneCustomFields(), this);
            // TODO: put back for IBatis
        }
        return customFieldMap;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    // @Override
    // TODO: is this necessary?  
//    public Site getSite() {
//        return person.getSite();
//    }

    // @Override
    // TODO: put back for IBatis
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    // @Override
    // TODO: put back for IBatis
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    // @Override
    // TODO: put back for IBatis
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    // @Override
    // TODO: put back for IBatis
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    // @Override
    // TODO: put back for IBatis
    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    // @Override
    // TODO: put back for IBatis
    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (number) are populated
     * @return true if this Address has all required fields populated
     */
    // @Override
    // TODO: put back for IBatis
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(number));
    }

    // @PrePersist
    // @PreUpdate
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
        if (!(obj instanceof Phone)) {
            return false;
        }
        Phone p = (Phone) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getPersonId(), p.getPersonId()).append(phoneType, p.getPhoneType()).append(activationStatus, p.getActivationStatus()).append(number, p.getNumber()).append(sms, p.getSms());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getPersonId()).append(phoneType).append(activationStatus).append(number).append(sms);
        return hcb.hashCode();
    }
}
