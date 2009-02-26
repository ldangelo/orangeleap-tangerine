package com.mpower.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.Normalizable;
import com.mpower.domain.customization.FieldDefinition;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.type.GiftEntryType;
import com.mpower.util.Utilities;

public class Gift implements 
//SiteAware, PaymentSourceAware, AddressAware, PhoneAware, EmailAware, Customizable, Viewable, 
Normalizable, GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private final Log logger = LogFactory.getLog(getClass());

    private Long id;

    private Person person;

    private Commitment commitment;

    private String comments;

    private BigDecimal amount;

    private BigDecimal deductibleAmount;

    private String currencyCode;

    private Date transactionDate;
    
    private Date donationDate;

    private Date postmarkDate;
    
    private String paymentType;

    private Integer checkNumber;

    private String authCode = "";

    private Long originalGiftId;

    private Long refundGiftId;

    private Date refundGiftTransactionDate;

    private Boolean sendAcknowledgment = false;
    
    private Date acknowledgmentDate;
    
    private List<GiftCustomField> giftCustomFields;

    private List<DistributionLine> distributionLines;

    private boolean deductible = false;

    private PaymentSource paymentSource = new PaymentSource(person);

    private Address address = new Address();

    private Phone phone = new Phone();

    private Email email = new Email();

    private GiftEntryType entryType = GiftEntryType.MANUAL;

    private String txRefNum;
    
    private String paymentStatus;
    
    private String paymentMessage;

    private Map<String, CustomField> customFieldMap = null;

    private Map<String, String> fieldLabelMap = null;

    private Map<String, Object> fieldValueMap = null;
    
    private Map<String, FieldDefinition> fieldTypeMap = null;

    private PaymentSource selectedPaymentSource = new PaymentSource(person);

    private Address selectedAddress = new Address();

    private Phone selectedPhone = new Phone();

    private Email selectedEmail = new Email();

    public Gift() {
    }

    public Gift(Commitment commitment, Date transactionDate) {
        this.commitment = commitment;
        this.person = commitment.getPerson();
        this.transactionDate = transactionDate;
        this.amount = commitment.getAmountPerGift();
    }

//    public String getCustomFieldValue(String fieldName) {
//    	CustomField customField = getCustomFieldMap().get(fieldName);
//    	if (customField == null || customField.getValue() == null) {
//            return null;
//        }
//        return customField.getValue();
//    }
//
//    public void setCustomFieldValue(String fieldName, String value) {
//    	CustomField customField = getCustomFieldMap().get(fieldName);
//    	if (customField == null) {
//            throw new RuntimeException("Invalid custom field name "+fieldName);
//        }
//    	customField.setValue(value);
//    }


    
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

//    @SuppressWarnings("unchecked")
//    public Map<String, CustomField> getCustomFieldMap() {
//        if (customFieldMap == null) {
//            customFieldMap = GiftCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
//        }
//        return customFieldMap;
//    }

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
//
//    @Override
//    public Site getSite() {
//        return person != null ? person.getSite() : null;
//    }

    public boolean isDeductible() {
        return deductible;
    }

    public void setDeductible(boolean deductible) {
        this.deductible = deductible;
    }

    public PaymentSource getPaymentSource() {
//        Utilities.populateIfNullPerson(paymentSource, person); //TODO
        return paymentSource;
    }

    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    public Address getAddress() {
//        Utilities.populateIfNullPerson(address, person);//TODO
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Phone getPhone() {
///        Utilities.populateIfNullPerson(phone, person);//TODO
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Email getEmail() {
//        Utilities.populateIfNullPerson(email, person);//TODO
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
//        Utilities.populateIfNullPerson(selectedPaymentSource, person);// TODO
        return selectedPaymentSource;
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        this.selectedPaymentSource = selectedPaymentSource;
    }

    public Address getSelectedAddress() {
//        Utilities.populateIfNullPerson(selectedAddress, person);//TODO
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Phone getSelectedPhone() {
//        Utilities.populateIfNullPerson(selectedPhone, person);//TODO
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public Email getSelectedEmail() {
//        Utilities.populateIfNullPerson(selectedEmail, person);//TODO
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    @Override
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
		return authCode.compareTo("") != 0;
	}
	
	public Boolean getIsCaptured() {
		return txRefNum.compareTo("") != 0;
	}
	
	public Boolean getIsProcessed() {
		return txRefNum.compareTo("") != 0;
	}
	
	public Boolean getIsDeclined() {
		return paymentStatus.compareTo("") == 0;
	}
	
}
