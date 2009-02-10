package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.type.CommitmentType;
import com.mpower.util.CommitmentCustomFieldMap;
import com.mpower.util.Utilities;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "COMMITMENT")
public class Commitment implements SiteAware, PaymentSourceAware, AddressAware, PhoneAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String STATUS_ACTIVE = "active";
    public static final String STATUS_CANCELED = "canceled";
    public static final String STATUS_EXPIRED = "expired";
    public static final String STATUS_FULFILLED = "fulfilled";

    public static final String PLEDGE_STATUS_PENDING = "pending";
    public static final String PLEDGE_STATUS_IN_PROGRESS= "inProgress";
    public static final String PLEDGE_STATUS_FULFILLED = "fulfilled";

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

    @Column(name = "COMMITMENT_TYPE")
    @Enumerated(EnumType.STRING)
    private CommitmentType commitmentType;

    @OneToMany(mappedBy = "commitment")
    private List<Gift> gifts;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "AMOUNT_PER_GIFT")
    private BigDecimal amountPerGift;

    @Column(name = "AMOUNT_TOTAL")
    private BigDecimal amountTotal = null;
    
    @Column(name = "CURRENCY_CODE")
    private String currencyCode = "USD";

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CHECK_NUMBER")
    private Integer checkNumber;

    @OneToMany(mappedBy = "commitment", cascade = CascadeType.ALL)
    private List<CommitmentCustomField> commitmentCustomFields;

    @OneToMany(mappedBy = "commitment", cascade = CascadeType.ALL)
    private List<DistributionLine> distributionLines;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate = Calendar.getInstance().getTime();

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "PLEDGE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pledgeDate;

    @Column(name = "PLEDGE_CANCEL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pledgeCancelDate;

    @Column(name = "PLEDGE_CANCEL_REASON")
    private String pledgeCancelReason;

    @Column(name = "STATUS")
    private String status = STATUS_ACTIVE;

    @Column(name = "PLEDGE_STATUS")
    private String pledgeStatus = PLEDGE_STATUS_PENDING;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    @Column(name = "AUTO_PAY")
    private boolean autoPay = false;

    @Column(name = "NOTES")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_SOURCE_ID")
    private PaymentSource paymentSource = new PaymentSource(person);

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address = new Address(person);

    @ManyToOne
    @JoinColumn(name = "PHONE_ID")
    private Phone phone = new Phone(person);

    @Column(name = "FREQUENCY")
    private String frequency;

    @OneToOne(mappedBy = "commitment", cascade = { CascadeType.ALL })
    private RecurringGift recurringGift;

    @Column(name = "LAST_ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastEntryDate;

    @Column(name = "RECURRING")
    private boolean recurring = false;

    @Column(name = "PROJECTED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectedDate;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    @Transient
    private PaymentSource selectedPaymentSource = new PaymentSource(person);

    @Transient
    private Address selectedAddress = new Address(person);

    @Transient
    private Phone selectedPhone = new Phone(person);

    public Commitment() {
    }

    public Commitment(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
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

    public CommitmentType getCommitmentType() {
        return commitmentType;
    }

    public void setCommitmentType(CommitmentType commitmentType) {
        this.commitmentType = commitmentType;
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

    @SuppressWarnings("unchecked")
    public List<DistributionLine> getDistributionLines() {
        if (distributionLines == null) {
            distributionLines = LazyList.decorate(new ArrayList<DistributionLine>(), FactoryUtils.instantiateFactory(DistributionLine.class, new Class[] { Commitment.class }, new Object[] { this }));
        }
        return distributionLines;
    }

    public void setDistributionLines(List<DistributionLine> distributionLines) {
        this.distributionLines = distributionLines;
    }

    public void addDistributionLine(DistributionLine distributionLine) {
        getDistributionLines().add(distributionLine);
    }

    public void removeInvalidDistributionLines() {
        Iterator<DistributionLine> distLineIter = this.distributionLines.iterator();
        while (distLineIter.hasNext()) {
            DistributionLine line = distLineIter.next();
            if (line == null || line.getAmount() == null) {
                distLineIter.remove();
            }
        }
    }

    /**
     * Check for at least 1 valid DistributionLine; create one if not found
     */
    public void defaultCreateDistributionLine() {
        boolean hasValid = false;
        Iterator<DistributionLine> distLineIter = this.distributionLines.iterator();
        while (distLineIter.hasNext()) {
            DistributionLine line = distLineIter.next();
            if (line != null) {
                hasValid = true;
                break;
            }
        }
        if (!hasValid) {
            getDistributionLines().get(0); // Default create one Distribution Line object if necessary
        }
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
        Utilities.populateIfNullPerson(paymentSource, person);
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Address getAddress() {
        Utilities.populateIfNullPerson(address, person);
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
        Utilities.populateIfNullPerson(phone, person);
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
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
        }
        return recur;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public Date getLastEntryDate() {
        return lastEntryDate;
    }

    public void setLastEntryDate(Date lastEntryDate) {
        this.lastEntryDate = lastEntryDate;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public Date getProjectedDate() {
        return projectedDate;
    }

    public void setProjectedDate(Date projectedDate) {
        this.projectedDate = projectedDate;
    }

    public BigDecimal getAmountPaid() {
        BigDecimal amount = BigDecimal.ZERO;
        if (getGifts() != null) {
            for (Gift gift : getGifts()) {
                amount = amount.add(gift.getAmount());
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

    public PaymentSource getSelectedPaymentSource() {
        Utilities.populateIfNullPerson(selectedPaymentSource, person);
        return selectedPaymentSource;
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        this.selectedPaymentSource = selectedPaymentSource;
    }

    public Address getSelectedAddress() {
        Utilities.populateIfNullPerson(selectedAddress, person);
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Phone getSelectedPhone() {
        Utilities.populateIfNullPerson(selectedPhone, person);
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

	public void setPledgeStatus(String pledgeStatus) {
		this.pledgeStatus = pledgeStatus;
	}

	public String getPledgeStatus() {
		return pledgeStatus;
	}


	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setPledgeDate(Date pledgeDate) {
		this.pledgeDate = pledgeDate;
	}

	public Date getPledgeDate() {
		return pledgeDate;
	}

	public void setPledgeCancelDate(Date pledgeCancelDate) {
		this.pledgeCancelDate = pledgeCancelDate;
	}

	public Date getPledgeCancelDate() {
		return pledgeCancelDate;
	}

	public void setPledgeCancelReason(String pledgeCancelReason) {
		this.pledgeCancelReason = pledgeCancelReason;
	}

	public String getPledgeCancelReason() {
		return pledgeCancelReason;
	}
	
    @PrePersist
    @PreUpdate
    public void normalize() {
        if (CommitmentType.RECURRING_GIFT.equals(getCommitmentType())) {
            setAmountTotal(null);
            setAutoPay(true);
            setProjectedDate(null);
        } else if (CommitmentType.PLEDGE.equals(getCommitmentType())) {
            setAutoPay(false);
            setAddress(null);
            setPaymentSource(null);
            setPhone(null);
            setSelectedAddress(null);
            setSelectedPaymentSource(null);
            setSelectedPhone(null);
            if (isRecurring()) {
                setProjectedDate(null);
                setAmountTotal(null);
            } else {
                setStartDate(null);
                setEndDate(null);
                setAmountPerGift(null);
                setFrequency(null);
            }
        } else if (CommitmentType.MEMBERSHIP.equals(getCommitmentType())) {
            setAutoPay(false);
        }
    }

}
