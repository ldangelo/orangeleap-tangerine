package com.mpower.domain;

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

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.util.DistributionLineCustomFieldMap;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "DISTRO_LINE")
public class DistributionLine implements Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "DISTRO_LINE_ID")
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
    
    @Column(name = "PERCENTAGE")
    private BigDecimal percentage;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "MOTIVATION_CODE")
    private String motivationCode;
    
    @Column(name = "OTHER_MOTIVATION")
    private String other_motivationCode;

    @OneToMany(mappedBy = "distributionLine", cascade = CascadeType.ALL)
    private List<DistributionLineCustomField> distributionLineCustomFields;

    @ManyToOne(optional = true)
    @JoinColumn(name = "GIFT_ID")
    private Gift gift;

    @ManyToOne(optional = true)
    @JoinColumn(name = "COMMITMENT_ID")
    private Commitment commitment;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;
    
    @Transient
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

    public List<DistributionLineCustomField> getCustomFields() {
        if (distributionLineCustomFields == null) {
            distributionLineCustomFields = new ArrayList<DistributionLineCustomField>();
        }
        return distributionLineCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = DistributionLineCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
        }
        return customFieldMap;
    }

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

    @Override
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    @Override
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    @Override
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    @Override
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    @Override
    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        this.fieldTypeMap = fieldTypeMap;
    }

    @Override
    public Map<String, FieldDefinition> getFieldTypeMap() {
        return fieldTypeMap;
    }

    public Site getSite() {
        return gift != null ? gift.getSite() : null;
    }

    @Override
    public Person getPerson() {
        return gift != null ? gift.getPerson() : null;
    }
}
