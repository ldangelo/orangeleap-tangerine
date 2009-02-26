package com.mpower.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.util.DistributionLineCustomFieldMap;

public class DistributionLine implements 
//Customizable, Viewable, 
GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(getClass());

    private Long id;

    private BigDecimal amount;
    
    private BigDecimal percentage;

    private String projectCode;

    private String motivationCode;
    
    private String other_motivationCode;

//    private List<DistributionLineCustomField> distributionLineCustomFields;

    private Gift gift;

    private Commitment commitment;

    private Map<String, CustomField> customFieldMap = null;

    private Map<String, String> fieldLabelMap = null;

    private Map<String, Object> fieldValueMap = null;
    
    private Map<String, FieldDefinition> fieldTypeMap = null;

    public DistributionLine() {
    }

    public DistributionLine(Gift gift) {
        this.gift = gift;
    }

    public DistributionLine(Gift gift, BigDecimal amount, String projectCode, String motivationCode) {
        this.gift = gift;
        this.amount = amount;
        this.projectCode = projectCode;
        this.motivationCode = motivationCode;
    }

    public DistributionLine(Commitment commitment) {
        this.commitment = commitment;
    }

    public DistributionLine(Commitment commitment, BigDecimal amount, String projectCode, String motivationCode) {
        this.commitment = commitment;
        this.amount = amount;
        this.projectCode = projectCode;
        this.motivationCode = motivationCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getMotivationCode() {
        return motivationCode;
    }

    public void setMotivationCode(String motivationCode) {
        this.motivationCode = motivationCode;
    }

    public String getOther_motivationCode() {
        return other_motivationCode;
    }

    public void setOther_motivationCode(String other_motivationCode) {
        this.other_motivationCode = other_motivationCode;
    }

//    public List<DistributionLineCustomField> getCustomFields() {
//        if (distributionLineCustomFields == null) {
//            distributionLineCustomFields = new ArrayList<DistributionLineCustomField>();
//        }
//        return distributionLineCustomFields;
//    }
//
//    @SuppressWarnings("unchecked")
//    public Map<String, CustomField> getCustomFieldMap() {
//        if (customFieldMap == null) {
//            customFieldMap = DistributionLineCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
//        }
//        return customFieldMap;
//    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Commitment getCommitment() {
        return commitment;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }

//    @Override
//    public Map<String, String> getFieldLabelMap() {
//        return fieldLabelMap;
//    }
//
//    @Override
//    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
//        this.fieldLabelMap = fieldLabelMap;
//    }
//
//    @Override
//    public Map<String, Object> getFieldValueMap() {
//        return fieldValueMap;
//    }
//
//    @Override
//    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
//        this.fieldValueMap = fieldValueMap;
//    }
//
//    @Override
//    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
//        this.fieldTypeMap = fieldTypeMap;
//    }
//
//    @Override
//    public Map<String, FieldDefinition> getFieldTypeMap() {
//        return fieldTypeMap;
//    }

//    public Site getSite() {
//        return gift != null ? gift.getSite() : null;
//    }
//
//    @Override
//    public Person getPerson() {
//        return gift != null ? gift.getPerson() : null;
//    }
}
