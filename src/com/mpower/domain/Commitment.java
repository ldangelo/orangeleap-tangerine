package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
public class Commitment implements SiteAware, PaymentSourceAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_CANCELED = "canceled";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_FULFILLED = "fulfilled";
    public static final String STATUS_SUSPEND = "suspend";

    public static final String FREQUENCY_ONE_TIME = "one time";
    public static final String FREQUENCY_WEEKLY = "weekly";
    public static final String FREQUENCY_TWICE_MONTHLY = "twice monthly";
    public static final String FREQUENCY_MONTHLY = "monthly";
    public static final String FREQUENCY_QUARTERLY = "quarterly";
    public static final String FREQUENCY_TWICE_ANNUALLY = "twice annually";
    public static final String FREQUENCY_ANNUALLY = "annually";
    public static final String FREQUENCY_UNSPECIFIED = "unspecified";

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

    @Column(name = "AMOUNT_TOTAL")
    private BigDecimal amountTotal = null;

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
    private String status = STATUS_ACTIVE;

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

    @Column(name = "SUSPEND_START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date suspendStartDate;

    @Column(name = "SUSPEND_END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date suspendEndDate;

    @Column(name = "LAST_ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEntryDate;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    @Transient
    private List<PaymentSource> paymentSources = null;

    @Transient
    private PaymentSource selectedPaymentSource = new PaymentSource();

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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
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
        } else if ("canceled".equals(getStatus()) || "expired".equals(getStatus())) {
            recur = false;
        } else if ("suspend".equals(getStatus())) {
            if (suspendStartDate == null && suspendEndDate == null) {
                recur = false;
            } else {
                Date now = new Date();
                if (suspendStartDate != null && suspendEndDate != null && now.after(suspendStartDate) && suspendEndDate.after(endDate)) {
                    return false;
                }
            }
        }
        return recur;
    }

    public boolean isSuspended(Date date) {
        return "suspend".equals(getStatus()) && suspendStartDate.before(date) && suspendEndDate.after(date);
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Date getSuspendStartDate() {
        return suspendStartDate;
    }

    public void setSuspendStartDate(Date suspendStartDate) {
        this.suspendStartDate = suspendStartDate;
    }

    public Date getSuspendEndDate() {
        return suspendEndDate;
    }

    public void setSuspendEndDate(Date suspendEndDate) {
        this.suspendEndDate = suspendEndDate;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }

    public BigDecimal getAmountPaid() {
        BigDecimal amount = BigDecimal.ZERO;
        if (getGifts() != null) {
            for (Gift gift : getGifts()) {
                amount = amount.add(gift.getValue());
            }
        }
        return amount;
    }

    public BigDecimal getAmountRemaining() {
        BigDecimal amount = getAmountTotal();
        if (amount != null) {
            amount = amount.subtract(getAmountPaid());
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                amount = BigDecimal.ZERO;
            }
        }
        return amount;
    }

    public List<PaymentSource> getPaymentSources() {
        return paymentSources;
    }

    public void setPaymentSources(List<PaymentSource> paymentSources) {
        this.paymentSources = paymentSources;
    }

    public PaymentSource getSelectedPaymentSource() {
        return selectedPaymentSource;
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        this.selectedPaymentSource = selectedPaymentSource;
    }
}
