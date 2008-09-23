package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.util.CommitmentCustomFieldMap;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "COMMITMENT", uniqueConstraints = @UniqueConstraint(columnNames = { "NAME", "PERSON_ID" }))
public class Commitment implements SiteAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "COMMITMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @OneToMany(mappedBy = "commitment")
    private List<Gift> gifts;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "AMOUNT_PER_GIFT")
    private BigDecimal amountPerGift;

    @Column(name = "NUMBER_OF_GIFTS")
    private Integer numberOfGifts;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CHECK_NUMBER")
    private Integer checkNumber;

    @Column(name = "COMMITMENT_CODE")
    private String commitmentCode;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "MOTIVATION_CODE")
    private String motivationCode;

    @OneToMany(mappedBy = "commitment", cascade = CascadeType.ALL)
    private List<CommitmentCustomField> commitmentCustomFields;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate = Calendar.getInstance().getTime();

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    @Column(name = "DEDUCTIBLE")
    private boolean deductible = false;

    @Column(name = "AUTO_PAY")
    private boolean autoPay = false;

    @Column(name = "NOTES")
    private String notes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PAYMENT_SOURCE_ID")
    private PaymentSource paymentSource;

    @Column(name = "FREQUENCY")
    private String frequency;

    @OneToOne(mappedBy = "commitment", cascade = { CascadeType.ALL })
    private RecurringGift recurringGift;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Set<String> requiredFields = null;

    @Transient
    private Map<String, String> validationMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

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

    public List<Gift> getGifts() {
        if (gifts == null) {
            gifts = new ArrayList<Gift>();
        }
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAmountPerGift() {
        return amountPerGift;
    }

    public void setAmountPerGift(BigDecimal amountPerGift) {
        this.amountPerGift = amountPerGift;
    }

    public Integer getNumberOfGifts() {
        return numberOfGifts;
    }

    public void setNumberOfGifts(Integer numberOfGifts) {
        this.numberOfGifts = numberOfGifts;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditCardType() {
        return getPaymentSource().getCreditCardType();
    }

    public void setCreditCardType(String creditCardType) {
        getPaymentSource().setCreditCardType(creditCardType);
    }

    public String getCreditCardNumber() {
        return getPaymentSource().getCreditCardNumber();
    }

    public void setCreditCardNumber(String creditCardNumber) {
        getPaymentSource().setCreditCardNumber(creditCardNumber);
    }

    public Date getCreditCardExpiration() {
        return getPaymentSource().getCreditCardExpirationDate();
    }

    public void setCreditCardExpiration(Date creditCardExpiration) {
        getPaymentSource().setCreditCardExpirationDate(creditCardExpiration);
    }

    public String getCreditCardSecurityCode() {
        return getPaymentSource().getCreditCardSecurityCode();
    }

    public void setCreditCardSecurityCode(String creditCardSecurityCode) {
        getPaymentSource().setCreditCardSecurityCode(creditCardSecurityCode);
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getAchType() {
        return getPaymentSource().getAchType();
    }

    public void setAchType(String achType) {
        getPaymentSource().setAchType(achType);
    }

    public String getAchRoutingNumber() {
        return getPaymentSource().getAchRoutingNumber();
    }

    public void setAchRoutingNumber(String achRoutingNumber) {
        getPaymentSource().setAchRoutingNumber(achRoutingNumber);
    }

    public String getAchAccountNumber() {
        return getPaymentSource().getAchAccountNumber();
    }

    public void setAchAccountNumber(String achAccountNumber) {
        getPaymentSource().setAchAccountNumber(achAccountNumber);
    }

    public List<CommitmentCustomField> getCustomFields() {
        if (commitmentCustomFields == null) {
            commitmentCustomFields = new ArrayList<CommitmentCustomField>();
        }
        return commitmentCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = CommitmentCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
        }
        return customFieldMap;
    }

    @Override
    public Set<String> getRequiredFields() {
        return requiredFields;
    }

    @Override
    public void setRequiredFields(Set<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    @Override
    public Map<String, String> getValidationMap() {
        return validationMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    @Override
    public void setValidationMap(Map<String, String> validationMap) {
        this.validationMap = validationMap;
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
    public Site getSite() {
        return person != null ? person.getSite() : null;
    }

    public String getCommitmentCode() {
        return commitmentCode;
    }

    public void setCommitmentCode(String commitmentCode) {
        this.commitmentCode = commitmentCode;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isDeductible() {
        return deductible;
    }

    public void setDeductible(boolean deductible) {
        this.deductible = deductible;
    }

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PaymentSource getPaymentSource() {
        if (paymentSource == null) {
            paymentSource = new PaymentSource(this.getPerson());
        }
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public RecurringGift getRecurringGift() {
        return recurringGift;
    }

    public void setRecurringGift(RecurringGift recurringGift) {
        this.recurringGift = recurringGift;
    }

    public boolean canRecur() {
        boolean recur = true;
        if (getEndDate() != null && getEndDate().before(Calendar.getInstance().getTime())) {
            recur = false;
        } else if ("canceled".equals(getStatus()) || "expired".equals(getStatus()) || "suspend".equals(getStatus())) {
            recur = false;
        }
        return recur;
    }
}
