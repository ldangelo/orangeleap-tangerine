package com.orangeleap.tangerine.domain.paymentInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.list.UnmodifiableList;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;

@SuppressWarnings("unchecked")
public class GiftInKind extends AbstractCustomizableEntity implements EmailAware {
    private static final long serialVersionUID = 1L;
    
    private BigDecimal fairMarketValue;
    private String currencyCode = StringConstants.USD;
    private Date donationDate = new Date();
    private String motivationCode;
    private String other_motivationCode;
    private boolean anonymous = false;
    private String recognitionName;
    private boolean sendAcknowledgment = false;
    private Date acknowledgmentDate;
    private Date transactionDate;
   
    private Long giftId;
    private Person person;

    private FormBeanType emailType;
    private Email email = new Email();
    private Email selectedEmail = new Email();

    /** Form bean representation of the GiftInKindDetails */
    protected List<GiftInKindDetail> mutableDetails = LazyList.decorate(new ArrayList<GiftInKindDetail>(), new Factory() {
        public Object create() {
            return new GiftInKindDetail();
        }
    });
    
    /** Domain object representation of the GiftInKindDetails */
    private List<GiftInKindDetail> details;
    
    /* Used by the form for cloning */
    protected final List<GiftInKindDetail> dummyDetails;

    public GiftInKind() {
        super();
        List<GiftInKindDetail> details = new ArrayList<GiftInKindDetail>();
        GiftInKindDetail detail = new GiftInKindDetail();
        detail.setDefaults();
        details.add(detail);
        dummyDetails = UnmodifiableList.decorate(details);
    }

    public GiftInKind(BigDecimal fairMarketValue, String currencyCode, Date donationDate, String motivationCode, String other_motivationCode, 
                        boolean anonymous, String recognitionName, boolean sendAcknowledgment, Date acknowledgmentDate, FormBeanType emailType) {
        this();
        this.fairMarketValue = fairMarketValue;
        this.currencyCode = currencyCode;
        this.donationDate = donationDate;
        this.motivationCode = motivationCode;
        this.other_motivationCode = other_motivationCode;
        this.anonymous = anonymous;
        this.recognitionName = recognitionName;
        this.sendAcknowledgment = sendAcknowledgment;
        this.acknowledgmentDate = acknowledgmentDate;
        this.emailType = emailType;
    }

    public BigDecimal getFairMarketValue() {
        return fairMarketValue;
    }

    public void setFairMarketValue(BigDecimal fairMarketValue) {
        this.fairMarketValue = fairMarketValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
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

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getRecognitionName() {
        return recognitionName;
    }

    public void setRecognitionName(String recognitionName) {
        this.recognitionName = recognitionName;
    }

    public boolean isSendAcknowledgment() {
        return sendAcknowledgment;
    }

    public void setSendAcknowledgment(boolean sendAcknowledgment) {
        this.sendAcknowledgment = sendAcknowledgment;
    }

    public Date getAcknowledgmentDate() {
        return acknowledgmentDate;
    }

    public void setAcknowledgmentDate(Date acknowledgmentDate) {
        this.acknowledgmentDate = acknowledgmentDate;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FormBeanType getEmailType() {
        return emailType;
    }

    public void setEmailType(FormBeanType emailType) {
        this.emailType = emailType;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Email getSelectedEmail() {
        if (selectedEmail == null) {
            selectedEmail = new Email(); // created only because spring needs to bind to it
        }
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public List<GiftInKindDetail> getDetails() {
        return details;
    }

    public void setDetails(List<GiftInKindDetail> details) {
        this.details = details;
    }

    public List<GiftInKindDetail> getMutableDetails() {
        return mutableDetails;
    }

    public void setMutableDetails(List<GiftInKindDetail> mutableDetails) {
        this.mutableDetails = mutableDetails;
    }
    
    public void removeEmptyMutableDetails() {
        Iterator<GiftInKindDetail> mutableDetailsIter = mutableDetails.iterator();
        details = new ArrayList<GiftInKindDetail>();
        while (mutableDetailsIter.hasNext()) {
            GiftInKindDetail detail = mutableDetailsIter.next();
            if (detail != null) {
                if (detail.isFieldEntered() == false) {
                    mutableDetailsIter.remove();
                }
                else {
                    details.add(detail);
                }
            }
        }
        if (details.isEmpty()) {
            details.add(new GiftInKindDetail());
        }
    }

    public void filterValidDetails() {
        details = new ArrayList<GiftInKindDetail>();
        Iterator<GiftInKindDetail> mutableDetailsIter = mutableDetails.iterator();
        while (mutableDetailsIter.hasNext()) {
            GiftInKindDetail detail = mutableDetailsIter.next();
            if (detail != null && detail.isValid()) {
                details.add(detail);
            }
        }
    }

    public List<GiftInKindDetail> getDummyDetails() {
        return dummyDetails;
    }

    @Override
    public void setDefaults() {
        super.setDefaults();
        if (recognitionName == null) {
            setRecognitionName(person.getRecognitionName());
        }
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (this.anonymous) {
            setRecognitionName(StringConstants.ANONYMOUS_UPPER_CASE);
        }
    }

    public Site getSite() {
        return person != null ? person.getSite() : null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("fairMarketValue", fairMarketValue).append("currencyCode", currencyCode).
            append("donationDate", donationDate).append("sendAcknowledgment", sendAcknowledgment).append("acknowledgmentDate", acknowledgmentDate).
            append("motivationCode", motivationCode).append("other_motivationCode", other_motivationCode).append("anonymous", anonymous).append("recognitionName", recognitionName).
            append("constituent", person).append("selectedEmail", selectedEmail).append("giftId", giftId).append("transactionDate", transactionDate).
            toString();
    }
}
