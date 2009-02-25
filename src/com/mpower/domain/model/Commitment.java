package com.mpower.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.DistributionLine;
import com.mpower.domain.GeneratedId;
import com.mpower.domain.Normalizable;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.type.CommitmentType;

public class Commitment implements 
//SiteAware, PaymentSourceAware, AddressAware, PhoneAware, EmailAware, Customizable, Viewable, 
GeneratedId, Normalizable, Serializable {

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

    private final Log logger = LogFactory.getLog(getClass());

    private Long id;

    private Person person;

    private CommitmentType commitmentType;

    private List<Gift> gifts;

    private String comments;

    private BigDecimal amountPerGift;

    private BigDecimal amountTotal = null;
    
    private String currencyCode = "USD";

    private String paymentType;

    private Integer checkNumber;

    private List<CommitmentCustomField> commitmentCustomFields;

    private List<DistributionLine> distributionLines;

    private Date startDate = Calendar.getInstance().getTime();

    private Date endDate;

    private Date pledgeDate = Calendar.getInstance().getTime();

    private Date pledgeCancelDate;

    private String pledgeCancelReason;

    private String status = STATUS_ACTIVE;

    private String pledgeStatus = PLEDGE_STATUS_PENDING;

    private Date createDate;

    private Date updateDate;

    private boolean autoPay = false;

    private String notes;

    private PaymentSource paymentSource = new PaymentSource();

    private Address address = new Address();

    private Phone phone = new Phone();

    private Email email = new Email();

    private String frequency;

    private Boolean sendAcknowledgment = false;
    
    private Date acknowledgmentDate;

    private RecurringGift recurringGift;

    private Date lastEntryDate;

    private boolean recurring = false;

    private Date projectedDate;

    private Map<String, CustomField> customFieldMap = null;

    private Map<String, String> fieldLabelMap = null;

    private Map<String, Object> fieldValueMap = null;
    
    private Map<String, FieldDefinition> fieldTypeMap = null;

    private PaymentSource selectedPaymentSource = new PaymentSource();

    private Address selectedAddress = new Address();

    private Phone selectedPhone = new Phone();

    private Email selectedEmail = new Email();

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

    public Boolean getSendAcknowledgment() {
        return sendAcknowledgment;
    }

    public void setSendAcknowledgment(Boolean sendAcknowledgment) {
        this.sendAcknowledgment = sendAcknowledgment;
    }

    public Date getAcknowledgmentDate() {
        return acknowledgmentDate;
    }

    public void setAcknowledgmentDate(Date acknowledgmentDate) {
        this.acknowledgmentDate = acknowledgmentDate;
    }

    public List<CommitmentCustomField> getCustomFields() {
        if (commitmentCustomFields == null) {
            commitmentCustomFields = new ArrayList<CommitmentCustomField>();
        }
        return commitmentCustomFields;
    }

//    @SuppressWarnings("unchecked")  // TODO
//    public Map<String, CustomField> getCustomFieldMap() {
//        if (customFieldMap == null) {
//            customFieldMap = CommitmentCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
//        }
//        return customFieldMap;
//    }

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
 //       Utilities.populateIfNullPerson(paymentSource, person);  // TODO
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Address getAddress() {
//        Utilities.populateIfNullPerson(address, person); // TODO
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
//        Utilities.populateIfNullPerson(phone, person);  // TODO
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Email getEmail() {
//       Utilities.populateIfNullPerson(email, person);  // TODO
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
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
 //       Utilities.populateIfNullPerson(selectedPaymentSource, person); // TODO
        return selectedPaymentSource;
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        this.selectedPaymentSource = selectedPaymentSource;
    }

    public Address getSelectedAddress() {
 //       Utilities.populateIfNullPerson(selectedAddress, person);// TODO
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Phone getSelectedPhone() {
//        Utilities.populateIfNullPerson(selectedPhone, person);// TODO
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public Email getSelectedEmail() {
//        Utilities.populateIfNullPerson(selectedEmail, person);// TODO
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
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
	
    @Override
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
