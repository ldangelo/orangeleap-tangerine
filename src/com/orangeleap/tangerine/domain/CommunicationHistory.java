package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.CommunicationHistoryType;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/*
 * Communication history (including generated correspondence) for Constituent
 */

// recordedBy and assignedTo have been moved to custom fields
@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
public class CommunicationHistory extends AbstractCustomizableEntity implements AddressAware, PhoneAware, EmailAware {
    private static final long serialVersionUID = 1L;

    private Date recordDate;
    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.MANUAL;
    private String entryType = "";
    private boolean systemGenerated = false;
    private String comments = StringConstants.EMPTY;
    private Constituent constituent;
    private Long constituentId; // this is used by SOAP API's so we don't have to embed an entire constituent
    private Long giftId;
    private Long pledgeId;
    private Long recurringGiftId;
    private FormBeanType addressType;
    private FormBeanType phoneType;
    private FormBeanType emailType;
    private Address selectedAddress;
    private Phone selectedPhone;
    private Email selectedEmail;

    public Site getSite() {
        return getConstituent().getSite();
    }

    public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
        if (constituent != null) constituentId = constituent.getId();
    }

    public CommunicationHistoryType getCommunicationHistoryType() {
        return communicationHistoryType;
    }

    public void setCommunicationHistoryType(CommunicationHistoryType communicationHistoryType) {
        this.communicationHistoryType = communicationHistoryType;
    }

    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    public void setSystemGenerated(boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
    }

    public Long getRecurringGiftId() {
        return recurringGiftId;
    }

    public void setRecurringGiftId(Long recurringGiftId) {
        this.recurringGiftId = recurringGiftId;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getEntryType() {
        return entryType;
    }

    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public Phone getSelectedPhone() {
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    public Email getSelectedEmail() {
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    @Override
    public FormBeanType getAddressType() {
        return addressType;
    }

    @Override
    public void setAddressType(FormBeanType type) {
        this.addressType = type;
    }

    @Override
    public FormBeanType getPhoneType() {
        return phoneType;
    }

    @Override
    public void setPhoneType(FormBeanType type) {
        this.phoneType = type;
    }

    @Override
    public FormBeanType getEmailType() {
        return emailType;
    }

    @Override
    public void setEmailType(FormBeanType type) {
        this.emailType = type;
    }

    public Long getConstituentId() {
        return constituentId;
    }

    public void setConstituentId(Long id) {
        constituentId = id;
    }
}
