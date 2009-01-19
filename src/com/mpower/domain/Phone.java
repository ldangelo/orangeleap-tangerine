package com.mpower.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.util.PhoneCustomFieldMap;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "PHONE")
public class Phone implements SiteAware, Customizable, ConstituentInfo, Inactivatible, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PHONE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "PHONE_TYPE", nullable = false)
    private String phoneType = "home";

    @Column(name = "NUMBER")
    private String number;

    @Column(name = "SMS")
    private String sms;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    @Column(name = "RECEIVE_CORRESPONDENCE")
    private boolean receiveMail = false;

    // either permanent, temporary, or seasonal
    @Column(name = "ACTIVATION_STATUS")
    private String activationStatus;

    // only care about month/day/year, not time
    @Column(name = "TEMPORARY_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date temporaryStartDate;

    // only care about month/day/year, not time
    @Column(name = "TEMPORARY_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date temporaryEndDate;

    // only care about month/day, not year or time
    @Column(name = "SEASONAL_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date seasonalStartDate;

    // only care about month/day, not year or time
    @Column(name = "SEASONAL_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date seasonalEndDate;

    @Column(name = "INACTIVE")
    private boolean inactive = false;

    @Column(name = "COMMENT")
    private String comments;

    @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL)
    private List<PhoneCustomField> phoneCustomFields;

    // only meaningful for Permanent phones, and indicates when date becomes effective (ex. they are moving the first of next month)
    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveDate;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    @Transient
    private boolean userCreated = false;

    public Phone() {
    }

    public Phone(Person person) {
        this.person = person;
        this.phoneType = "unknown";  // defaulting to 'home' would change the home phone on the constituent whenever a new payment type is created with a new phone.
        this.activationStatus = "permanent";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public List<PhoneCustomField> getPhoneCustomFields() {
        if (phoneCustomFields == null) {
            phoneCustomFields = new ArrayList<PhoneCustomField>();
        }
        return phoneCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = PhoneCustomFieldMap.buildCustomFieldMap(getPhoneCustomFields(), this);
        }
        return customFieldMap;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Site getSite() {
        return person.getSite();
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
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(number));
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
        if (!(obj instanceof Phone)) {
            return false;
        }
        Phone p = (Phone) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(person.getId(), p.getPerson().getId()).append(phoneType, p.getPhoneType()).append(activationStatus, p.getActivationStatus()).append(number, p.getNumber()).append(sms, p.getSms());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(person.getId()).append(phoneType).append(activationStatus).append(number).append(sms);
        return hcb.hashCode();
    }
}
