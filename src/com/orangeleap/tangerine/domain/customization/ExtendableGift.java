package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.FormBeanType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Simple class that delegates to an internal gift so that it can
 *  be decorated with additional fields
 */
public class ExtendableGift extends Gift {

    private Gift gift;


    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public Gift getGift() {
        return gift;
    }

    public String getGiftStatus() {
        return gift.getGiftStatus();
    }

    public void setGiftStatus(String giftStatus) {
        gift.setGiftStatus(giftStatus);
    }

    public GiftType getGiftType() {
        return gift.getGiftType();
    }

    public void setGiftType(GiftType giftType) {
        gift.setGiftType(giftType);
    }

    public Long getRecurringGiftId() {
        return gift.getRecurringGiftId();
    }

    public void setRecurringGiftId(Long recurringGiftId) {
        gift.setRecurringGiftId(recurringGiftId);
    }

    public Long getPledgeId() {
        return gift.getPledgeId();
    }

    public void setPledgeId(Long pledgeId) {
        gift.setPledgeId(pledgeId);
    }

    public BigDecimal getAmount() {
        return gift.getAmount();
    }

    public void setAmount(BigDecimal amount) {
        gift.setAmount(amount);
    }

    public BigDecimal getDeductibleAmount() {
        return gift.getDeductibleAmount();
    }

    public void setDeductibleAmount(BigDecimal deductibleAmount) {
        gift.setDeductibleAmount(deductibleAmount);
    }

    public Date getTransactionDate() {
        return gift.getTransactionDate();
    }

    public void setTransactionDate(Date transactionDate) {
        gift.setTransactionDate(transactionDate);
    }

    public Date getDonationDate() {
        return gift.getDonationDate();
    }

    public void setDonationDate(Date donationDate) {
        gift.setDonationDate(donationDate);
    }

    public Date getPostmarkDate() {
        return gift.getPostmarkDate();
    }

    public void setPostmarkDate(Date postmarkDate) {
        gift.setPostmarkDate(postmarkDate);
    }

    public String getAuthCode() {
        return gift.getAuthCode();
    }

    public void setAuthCode(String authCode) {
        gift.setAuthCode(authCode);
    }

    public Long getOriginalGiftId() {
        return gift.getOriginalGiftId();
    }

    public void setOriginalGiftId(Long originalGiftId) {
        gift.setOriginalGiftId(originalGiftId);
    }

    public Long getRefundGiftId() {
        return gift.getRefundGiftId();
    }

    public void setRefundGiftId(Long refundGiftId) {
        gift.setRefundGiftId(refundGiftId);
    }

    public Date getRefundGiftTransactionDate() {
        return gift.getRefundGiftTransactionDate();
    }

    public void setRefundGiftTransactionDate(Date refundGiftTransactionDate) {
        gift.setRefundGiftTransactionDate(refundGiftTransactionDate);
    }

    public String getRefundDetails() {
        return gift.getRefundDetails();
    }

    public void setRefundDetails(String refundDetails) {
        gift.setRefundDetails(refundDetails);
    }

    public boolean isDeductible() {
        return gift.isDeductible();
    }

    public void setDeductible(boolean deductible) {
        gift.setDeductible(deductible);
    }

    public GiftEntryType getEntryType() {
        return gift.getEntryType();
    }

    public void setEntryType(GiftEntryType entryType) {
        gift.setEntryType(entryType);
    }

    public String getTxRefNum() {
        return gift.getTxRefNum();
    }

    public void setTxRefNum(String txRefNum) {
        gift.setTxRefNum(txRefNum);
    }

    public String getPaymentStatus() {
        return gift.getPaymentStatus();
    }

    public void setPaymentStatus(String status) {
        gift.setPaymentStatus(status);
    }

    public String getPaymentMessage() {
        return gift.getPaymentMessage();
    }

    public void setPaymentMessage(String message) {
        gift.setPaymentMessage(message);
    }

    public List<Long> getAssociatedPledgeIds() {
        return gift.getAssociatedPledgeIds();
    }

    public void setAssociatedPledgeIds(List<Long> associatedPledgeIds) {
        gift.setAssociatedPledgeIds(associatedPledgeIds);
    }

    public void addAssociatedPledgeId(Long pledgeId) {
        gift.addAssociatedPledgeId(pledgeId);
    }

    public Boolean getIsAuthorized() {
        return gift.getIsAuthorized();
    }

    public Boolean getIsCaptured() {
        return gift.getIsCaptured();
    }

    public Boolean getIsProcessed() {
        return gift.getIsProcessed();
    }

    public Boolean getIsDeclined() {
        return gift.getIsDeclined();
    }

    public void setGiftForGiftInKind(GiftInKind giftInKind) {
        gift.setGiftForGiftInKind(giftInKind);
    }

    public String getShortDescription() {
        return gift.getShortDescription();
    }

    @Override
    public void prePersist() {
        gift.prePersist();
    }

    @Override
    public String toString() {
        return gift.toString();
    }

    public Person getPerson() {
        return gift.getPerson();
    }

    public void setPerson(Person person) {
        gift.setPerson(person);
    }

    public String getComments() {
        return gift.getComments();
    }

    public void setComments(String comments) {
        gift.setComments(comments);
    }

    public String getCurrencyCode() {
        return gift.getCurrencyCode();
    }

    public void setCurrencyCode(String currencyCode) {
        gift.setCurrencyCode(currencyCode);
    }

    @Override
    public void setPaymentType(String paymentType) {
        gift.setPaymentType(paymentType);
    }

    @Override
    public String getPaymentType() {
        return gift.getPaymentType();
    }

    public void setCheckNumber(Integer checkNumber) {
        gift.setCheckNumber(checkNumber);
    }

    public Integer getCheckNumber() {
        return gift.getCheckNumber();
    }

    public boolean isSendAcknowledgment() {
        return gift.isSendAcknowledgment();
    }

    public void setSendAcknowledgment(boolean sendAcknowledgment) {
        gift.setSendAcknowledgment(sendAcknowledgment);
    }

    public Date getAcknowledgmentDate() {
        return gift.getAcknowledgmentDate();
    }

    public void setAcknowledgmentDate(Date acknowledgmentDate) {
        gift.setAcknowledgmentDate(acknowledgmentDate);
    }

    public List<DistributionLine> getDistributionLines() {
        return gift.getDistributionLines();
    }

    public void setDistributionLines(List<DistributionLine> distributionLines) {
        gift.setDistributionLines(distributionLines);
    }

    public List<DistributionLine> getMutableDistributionLines() {
        return gift.getMutableDistributionLines();
    }

    public void setMutableDistributionLines(List<DistributionLine> mutableDistributionLines) {
        gift.setMutableDistributionLines(mutableDistributionLines);
    }

    public void removeEmptyMutableDistributionLines() {
        gift.removeEmptyMutableDistributionLines();
    }

    public void filterValidDistributionLines() {
        gift.filterValidDistributionLines();
    }

    @Override
    public FormBeanType getAddressType() {
        return gift.getAddressType();
    }

    @Override
    public void setAddressType(FormBeanType type) {
        gift.setAddressType(type);
    }

    @Override
    public FormBeanType getPhoneType() {
        return gift.getPhoneType();
    }

    @Override
    public void setPhoneType(FormBeanType type) {
        gift.setPhoneType(type);
    }

    @Override
    public FormBeanType getEmailType() {
        return gift.getEmailType();
    }

    @Override
    public void setEmailType(FormBeanType type) {
        gift.setEmailType(type);
    }

    @Override
    public FormBeanType getPaymentSourceType() {
        return gift.getPaymentSourceType();
    }

    @Override
    public void setPaymentSourceType(FormBeanType type) {
        gift.setPaymentSourceType(type);
    }

    @Override
    public PaymentSource getPaymentSource() {
        return gift.getPaymentSource();
    }

    @Override
    public void setPaymentSource(PaymentSource paymentSource) {
        gift.setPaymentSource(paymentSource);
    }

    @Override
    public Address getAddress() {
        return gift.getAddress();
    }

    @Override
    public void setAddress(Address address) {
        gift.setAddress(address);
    }

    @Override
    public Phone getPhone() {
        return gift.getPhone();
    }

    @Override
    public void setPhone(Phone phone) {
        gift.setPhone(phone);
    }

    @Override
    public Email getEmail() {
        return gift.getEmail();
    }

    @Override
    public void setEmail(Email email) {
        gift.setEmail(email);
    }

    @Override
    public PaymentSource getSelectedPaymentSource() {
        return gift.getSelectedPaymentSource();
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        gift.setSelectedPaymentSource(selectedPaymentSource);
    }

    @Override
    public Address getSelectedAddress() {
        return gift.getSelectedAddress();
    }

    public void setSelectedAddress(Address selectedAddress) {
        gift.setSelectedAddress(selectedAddress);
    }

    @Override
    public Phone getSelectedPhone() {
        return gift.getSelectedPhone();
    }

    public void setSelectedPhone(Phone selectedPhone) {
        gift.setSelectedPhone(selectedPhone);
    }

    @Override
    public Email getSelectedEmail() {
        return gift.getSelectedEmail();
    }

    public void setSelectedEmail(Email selectedEmail) {
        gift.setSelectedEmail(selectedEmail);
    }

    public Site getSite() {
        return gift.getSite();
    }

    @Override
    public void setPaymentSourcePaymentType() {
        gift.setPaymentSourcePaymentType();
    }

    @Override
    public void setPaymentSourceAwarePaymentType() {
        gift.setPaymentSourceAwarePaymentType();
    }

    public List<DistributionLine> getDummyDistributionLines() {
        return gift.getDummyDistributionLines();
    }

    @Override
    public void setDefaults() {
        gift.setDefaults();
    }

    public static Map<String, CustomField> createCustomFieldMap(AbstractCustomizableEntity entity) {
        return Gift.createCustomFieldMap(entity);
    }

    public Map<String, CustomField> getCustomFieldMap() {
        return gift.getCustomFieldMap();
    }

    public void setCustomFieldMap(Map<String, CustomField> customFieldMap) {
        gift.setCustomFieldMap(customFieldMap);
    }

    public String getCustomFieldValue(String fieldName) {
        return gift.getCustomFieldValue(fieldName);
    }

    public void setCustomFieldValue(String fieldName, String value) {
        gift.setCustomFieldValue(fieldName, value);
    }

    public void setDefaultCustomFieldValue(String fieldName, String value) {
        gift.setDefaultCustomFieldValue(fieldName, value);
    }

    public void removeCustomFieldValue(String fieldName, String value) {
        gift.removeCustomFieldValue(fieldName, value);
    }

    public void addCustomFieldValue(String fieldName, String value) {
        gift.addCustomFieldValue(fieldName, value);
    }

    public AbstractCustomizableEntity createCopy() {
        return gift.createCopy();
    }

    public Long getId() {
        return gift.getId();
    }

    public void setId(Long id) {
        gift.setId(id);
    }

    public void resetIdToNull() {
        gift.resetIdToNull();
    }

    public String getType() {
        return gift.getType();
    }

    public Map<String, String> getFieldLabelMap() {
        return gift.getFieldLabelMap();
    }

    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        gift.setFieldLabelMap(fieldLabelMap);
    }

    public Map<String, Object> getFieldValueMap() {
        return gift.getFieldValueMap();
    }

    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        gift.setFieldValueMap(fieldValueMap);
    }

    public Map<String, FieldDefinition> getFieldTypeMap() {
        return gift.getFieldTypeMap();
    }

    public void setFieldTypeMap(Map<String, FieldDefinition> fieldTypeMap) {
        gift.setFieldTypeMap(fieldTypeMap);
    }

    public void postRead() {
        gift.postRead();
    }

    public String getAuditShortDesc() {
        return gift.getAuditShortDesc();
    }

    public void setCreateDate(Date createDate) {
        gift.setCreateDate(createDate);
    }

    public Date getCreateDate() {
        return gift.getCreateDate();
    }

    public void setUpdateDate(Date updateDate) {
        gift.setUpdateDate(updateDate);
    }

    public Date getUpdateDate() {
        return gift.getUpdateDate();
    }
}
