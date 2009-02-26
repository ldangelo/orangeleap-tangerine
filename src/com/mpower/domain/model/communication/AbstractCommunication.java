package com.mpower.domain.model.communication;

import java.util.Date;
import java.util.Map;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.Inactivatible;
import com.mpower.domain.Normalizable;
import com.mpower.domain.model.customization.CustomField;
import com.mpower.domain.model.customization.FieldDefinition;
import com.mpower.type.ActivationType;

public abstract class AbstractCommunication implements Inactivatible, GeneratedId, Normalizable { // TODO: add back Customizable,

    protected Long id;
    protected Long personId;
    protected Date createDate;
    protected Date updateDate;
    protected ActivationType activationStatus;
    protected boolean receiveMail = false;
    protected Date temporaryStartDate;
    protected Date temporaryEndDate;
    protected Date seasonalStartDate;
    protected Date seasonalEndDate;
    protected boolean inactive = false;
    protected String comments;
    // only meaningful for Permanent emails, and indicates when date becomes effective (ex. they are moving the first of next month)
    protected Date effectiveDate;
    protected boolean userCreated = false;
    protected Map<String, CustomField> customFieldMap = null;
    protected Map<String, String> fieldLabelMap = null;
    protected Map<String, Object> fieldValueMap = null;
    protected Map<String, FieldDefinition> fieldTypeMap = null;

    public AbstractCommunication() {
        
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

    public ActivationType getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationType activationStatusType) {
        this.activationStatus = activationStatusType;
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

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            // customFieldMap = PhoneCustomFieldMap.buildCustomFieldMap(getPhoneCustomFields(), this);
            // TODO: put back for IBatis
        }
        return customFieldMap;
    }

    public void setCustomFieldMap(Map<String,CustomField> customFieldMap) {
        this.customFieldMap = customFieldMap;
    }

    public String getCustomFieldValue(String fieldName) {
        CustomField customField = getCustomFieldMap().get(fieldName);
        if (customField == null || customField.getValue() == null) {
            return null;
        }
        return customField.getValue();
    }

    public void setCustomFieldValue(String fieldName, String value) {
        CustomField customField = getCustomFieldMap().get(fieldName);
        if (customField == null) {
            customField = new CustomField();
            customField.setName(fieldName);
            customField.setValue(value);
            customField.setEntityId(this.getId());
            customField.setEntityType(this.getClass().getSimpleName().toLowerCase());
            getCustomFieldMap().put(fieldName, customField);

        } else {
            customField.setValue(value);
        }
    }


    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }
    
    @Override
    public void normalize() {
        if (activationStatus != null) {
            if (ActivationType.permanent.equals(getActivationStatus())) {
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
            } 
            else if (ActivationType.seasonal.equals(getActivationStatus())) {
                setEffectiveDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
            } 
            else if (ActivationType.temporary.equals(getActivationStatus())) {
                setEffectiveDate(null);
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
            }
        }
    }
}