package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.type.GiftEntryType;
import com.mpower.util.GiftCustomFieldMap;
import com.mpower.util.Utilities;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "GIFT")
public class Gift implements SiteAware, PaymentSourceAware, AddressAware, PhoneAware, EmailAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "GIFT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @ManyToOne(optional = true)
    @JoinColumn(name = "COMMITMENT_ID")
    private Commitment commitment;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "DEDUCTIBLE_AMOUNT")
    private BigDecimal deductibleAmount;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "TRANSACTION_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    
    @Column(name = "DONATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date donationDate;

    @Column(name = "POSTMARK_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postmarkDate;
    
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CHECK_NUMBER")
    private Integer checkNumber;

    @Column(name = "AUTH_CODE")
    private String authCode = "";

    @Column(name = "ORIGINAL_GIFT_ID")
    private Long originalGiftId;

    @Column(name = "REFUND_GIFT_ID")
    private Long refundGiftId;

    @Column(name = "REFUND_GIFT_TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date refundGiftTransactionDate;

    @Column(name = "SEND_ACKNOWLEDGMENT")
    private Boolean sendAcknowledgment = false;
    
    @Column(name = "ACKNOWLEDGMENT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date acknowledgmentDate;
    
    @OneToMany(mappedBy = "gift", cascade = CascadeType.ALL)
    private List<GiftCustomField> giftCustomFields;

    @OneToMany(mappedBy = "gift", cascade = CascadeType.ALL)
    private List<DistributionLine> distributionLines;

    @Column(name = "DEDUCTIBLE")
    private boolean deductible = false;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_SOURCE_ID")
    private PaymentSource paymentSource = new PaymentSource(person);

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address = new Address(person);

    @ManyToOne
    @JoinColumn(name = "PHONE_ID")
    private Phone phone = new Phone(person);

    @ManyToOne
    @JoinColumn(name = "EMAIL_ID")
    private Email email = new Email(person);

    @Column(name = "ENTRY_TYPE")
    @Enumerated(EnumType.STRING)
    private GiftEntryType entryType = GiftEntryType.MANUAL;

    @Column(name = "PAYMENT_TXREFNUM")
    private String txRefNum;
    
    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;
    
    @Column(name="PAYMENT_MESSAGE")
    private String paymentMessage;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;
    
    @Transient
    private Map<String, FieldDefinition> fieldTypeMap = null;

    @Transient
    private PaymentSource selectedPaymentSource = new PaymentSource(person);

    @Transient
    private Address selectedAddress = new Address(person);

    @Transient
    private Phone selectedPhone = new Phone(person);

    @Transient
    private Email selectedEmail = new Email(person);

    public Gift() {
    }

    public Gift(Commitment commitment, Date transactionDate) {
        this.commitment = commitment;
        this.person = commitment.getPerson();
        this.transactionDate = transactionDate;
        this.amount = commitment.getAmountPerGift();
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
            throw new RuntimeException("Invalid custom field name "+fieldName);
        }
    	customField.setValue(value);
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

    public Commitment getCommitment() {
        return commitment;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDeductibleAmount() {
        return deductibleAmount;
    }

    public void setDeductibleAmount(BigDecimal deductibleAmount) {
        this.deductibleAmount = deductibleAmount;
    }

    public String getCurrencyCode() {
        if (currencyCode == null) {
            currencyCode = "USD";
        }
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDonationDate() {
        if (donationDate == null) {
            donationDate = new Date();
        }
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public Date getPostmarkDate() {
        return postmarkDate;
    }

    public void setPostmarkDate(Date postmarkDate) {
        this.postmarkDate = postmarkDate;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Long getOriginalGiftId() {
        return originalGiftId;
    }

    public void setOriginalGiftId(Long originalGiftId) {
        this.originalGiftId = originalGiftId;
    }

    public Long getRefundGiftId() {
        return refundGiftId;
    }

    public void setRefundGiftId(Long refundGiftId) {
        this.refundGiftId = refundGiftId;
    }

    public Date getRefundGiftTransactionDate() {
        return refundGiftTransactionDate;
    }

    public void setRefundGiftTransactionDate(Date refundGiftTransactionDate) {
        this.refundGiftTransactionDate = refundGiftTransactionDate;
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

    public List<GiftCustomField> getCustomFields() {
        if (giftCustomFields == null) {
            giftCustomFields = new ArrayList<GiftCustomField>();
        }
        return giftCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = GiftCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
        }
        return customFieldMap;
    }

    @SuppressWarnings("unchecked")
    public List<DistributionLine> getDistributionLines() {
        if (distributionLines == null) {
            distributionLines = LazyList.decorate(new ArrayList<DistributionLine>(), FactoryUtils.instantiateFactory(DistributionLine.class, new Class[] { Gift.class }, new Object[] { this }));
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

    @Override
    public Site getSite() {
        return person != null ? person.getSite() : null;
    }

    public boolean isDeductible() {
        return deductible;
    }

    public void setDeductible(boolean deductible) {
        this.deductible = deductible;
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

    public Email getEmail() {
        Utilities.populateIfNullPerson(email, person);
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public GiftEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(GiftEntryType entryType) {
        this.entryType = entryType;
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

    public Email getSelectedEmail() {
        Utilities.populateIfNullPerson(selectedEmail, person);
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (deductibleAmount == null) {
            deductibleAmount = amount;
        }
    }


	public void setTxRefNum(String txRefNum) {
		this.txRefNum = txRefNum;
	}

	public String getTxRefNum() {
		return txRefNum;
	}
	
	public void setPaymentStatus(String status) {
		this.paymentStatus = status;
		
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}
	
	public void setPaymentMessage(String message) {
		this.paymentMessage = message;
	}

	public String getPaymentMessage() {
		return paymentMessage;
	}
	
	public Boolean getIsAuthorized() {
		return !StringUtils.trimToEmpty(authCode).equals("");
	}
	
	public Boolean getIsCaptured() {
		return !StringUtils.trimToEmpty(txRefNum).equals("");
	}
	
	public Boolean getIsProcessed() {
		return !StringUtils.trimToEmpty(txRefNum).equals("");
	}
	
	public Boolean getIsDeclined() {
		return !StringUtils.trimToEmpty(paymentStatus).equals("");
	}
	
	
}
