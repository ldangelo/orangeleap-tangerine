package com.mpower.domain.model.paymentInfo;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mpower.domain.model.AbstractCustomizableEntity;
import com.mpower.domain.model.AddressAware;
import com.mpower.domain.model.EmailAware;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.PaymentSourceAware;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.PhoneAware;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.util.StringConstants;

@SuppressWarnings("serial")
public abstract class AbstractPaymentInfoEntity extends AbstractCustomizableEntity implements PaymentSourceAware, AddressAware, PhoneAware, EmailAware  {

    protected String comments;
    protected String currencyCode = StringConstants.USD;
    protected String paymentType;
    protected Integer checkNumber;
    protected boolean sendAcknowledgment = false;
    protected Date acknowledgmentDate;
    
    protected Person person;
    protected List<DistributionLine> distributionLines;

    protected Address address = new Address();
    protected Phone phone = new Phone();
    protected Email email = new Email();
    protected PaymentSource paymentSource = new PaymentSource(person);
    
    protected Address selectedAddress = new Address();
    protected Phone selectedPhone = new Phone();
    protected Email selectedEmail = new Email();
    protected PaymentSource selectedPaymentSource = new PaymentSource(person);

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCurrencyCode() {
        if (currencyCode == null) {
            currencyCode = StringConstants.USD;
        }
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String getPaymentType() {
        return paymentType;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Integer getCheckNumber() {
        return checkNumber;
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

    public List<DistributionLine> getDistributionLines() {
    //        if (distributionLines == null) { // TODO: lazyList for IBatis?
    //            distributionLines = LazyList.decorate(new ArrayList<DistributionLine>(), FactoryUtils.instantiateFactory(DistributionLine.class, new Class[] { Gift.class }, new Object[] { this }));
    //        }
            return distributionLines;
        }

    public void setDistributionLines(List<DistributionLine> distributionLines) {
            this.distributionLines = distributionLines;
    //        this.distributionLines = LazyList.decorate(distributionLines, FactoryUtils.instantiateFactory(DistributionLine.class, new Class[] { Gift.class }, new Object[] { this }));
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
    public PaymentSource getPaymentSource() {
        return paymentSource;
    }

    @Override
    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public Email getEmail() {
        return email;
    }

    @Override
    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public PaymentSource getSelectedPaymentSource() {
        return selectedPaymentSource;
    }

    public void setSelectedPaymentSource(PaymentSource selectedPaymentSource) {
        this.selectedPaymentSource = selectedPaymentSource;
    }

    @Override
    public Address getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    @Override
    public Phone getSelectedPhone() {
        return selectedPhone;
    }

    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    @Override
    public Email getSelectedEmail() {
        return selectedEmail;
    }

    public void setSelectedEmail(Email selectedEmail) {
        this.selectedEmail = selectedEmail;
    }

    public Site getSite() {
        return person != null ? person.getSite() : null;
    }
}
