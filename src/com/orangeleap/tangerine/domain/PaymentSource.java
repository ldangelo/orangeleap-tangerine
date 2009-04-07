package com.orangeleap.tangerine.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.annotation.NotAuditable;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.AES;

public class PaymentSource extends AbstractEntity implements Inactivatible, AddressAware, PhoneAware {//SiteAware, ConstituentInfo TODO: put back for IBatis

    private static final long serialVersionUID = 1L;
    public static final String ACH = "ACH";
    public static final String CREDIT_CARD = "Credit Card";
    public static final String CASH = "Cash";
    public static final String CHECK = "Check";

    private String profile;
    private String paymentType = CREDIT_CARD; // TODO: use PaymentType enum
    private String creditCardHolderName;
    private String creditCardType;
    @NotAuditable
    private String creditCardNumberEncrypted;
    private Date creditCardExpiration;
    private String achHolderName;
    @NotAuditable
    private String achRoutingNumber;
    @NotAuditable
    private String achAccountNumberEncrypted;
    private boolean inactive = false;
    private Integer creditCardExpirationMonth;
    private String creditCardExpirationMonthText;
    private Integer creditCardExpirationYear;
    @NotAuditable
    private String creditCardNumber;
    // absolutely don't store this in the db - see VISA merchant rules only used for processing
    @NotAuditable
    private String creditCardSecurityCode;
    @NotAuditable
    private String achAccountNumber;

    private FormBeanType addressType;
    private FormBeanType phoneType;
    private Person person;
    private Address address = new Address(); // Created only because spring binds to it
    private Phone phone = new Phone(); // Created only because spring binds to it
    private Address selectedAddress = new Address(); // Created only because spring binds to it
    private Phone selectedPhone = new Phone(); // Created only because spring binds to it
    private boolean userCreated = false;
  
    public PaymentSource() {
    }

    public PaymentSource(Person person) {
        this();
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        if (address != null && person != null) {
            address.setPersonId(person.getId());
        }
        if (phone != null && person != null) {
            phone.setPersonId(person.getId());
        }
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String type) {
        this.paymentType = type;
    }

    public String getCreditCardHolderName() {
        if (creditCardHolderName == null && person != null) {
            creditCardHolderName = person.getFirstLast();
        }
        return creditCardHolderName;
    }

    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    public String getAchHolderName() {
        if (achHolderName == null && person != null) {
            achHolderName = person.getFirstLast();
        }
        return achHolderName;
    }

    public void setAchHolderName(String achHolderName) {
        this.achHolderName = achHolderName;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumberEncrypted() {
        return creditCardNumberEncrypted;
    }

    public void setCreditCardNumberEncrypted(String creditCardNumberEncrypted) {
        this.creditCardNumberEncrypted = creditCardNumberEncrypted;
    }

    public String getCreditCardNumber() {
        if (creditCardNumberEncrypted != null) {
            return AES.decrypt(creditCardNumberEncrypted);
        }
        return null;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
        if (creditCardNumber != null) {
            creditCardNumberEncrypted = AES.encrypt(creditCardNumber);
        } else {
            creditCardNumberEncrypted = null;
        }
    }

    public String getCreditCardNumberDisplay() {
        return decryptAndMask(creditCardNumberEncrypted);
    }

    public void setCreditCardNumberDisplay(String str) {
        // no-op
    }

    public Date getCreditCardExpiration() {
        return creditCardExpiration;
    }

    public void setCreditCardExpiration(Date creditCardExpiration) {
        this.creditCardExpiration = creditCardExpiration;
    }

    public String getAchRoutingNumber() {
        return achRoutingNumber;
    }

    public void setAchRoutingNumber(String achRoutingNumber) {
        this.achRoutingNumber = achRoutingNumber;
    }
    
    public String getAchRoutingNumberDisplay() {
        return mask(achRoutingNumber);
    }

    public void setAchRoutingNumberDisplay(String str) {
        // no-op
    }

    public String getAchAccountNumberEncrypted() {
        return achAccountNumberEncrypted;
    }

    public void setAchAccountNumberEncrypted(String achAccountNumberEncrypted) {
        this.achAccountNumberEncrypted = achAccountNumberEncrypted;
    }

    public String getAchAccountNumber() {
        if (achAccountNumberEncrypted != null) {
            return AES.decrypt(achAccountNumberEncrypted);
        }
        return null;
    }

    public void setAchAccountNumber(String achAccountNumber) {
        this.achAccountNumber = achAccountNumber;
        if (achAccountNumber != null) {
            achAccountNumberEncrypted = AES.encrypt(achAccountNumber);
        } else {
            achAccountNumberEncrypted = null;
        }
    }

    public String getAchAccountNumberDisplay() {
        return decryptAndMask(achAccountNumberEncrypted);
    }

    public void setAchAccountNumberDisplay(String str) {
        // no-op
    }

    @Override
    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    @Override
    public boolean isInactive() {
        return inactive;
    }

    public Integer getCreditCardExpirationMonth() {
        if (getCreditCardExpiration() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getCreditCardExpiration());
            creditCardExpirationMonth = calendar.get(Calendar.MONTH) + 1;
        }
        return creditCardExpirationMonth;
    }

    public String getCreditCardExpirationMonthText() {
        if (getCreditCardExpiration() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getCreditCardExpiration());
            creditCardExpirationMonth = calendar.get(Calendar.MONTH) + 1;
            String month = String.valueOf(creditCardExpirationMonth);
            if (month.length() == 1) {
                month = "0" + month;
            }
            creditCardExpirationMonthText = month;
        }
        return creditCardExpirationMonthText;
    }

    public void setCreditCardExpirationMonth(Integer creditCardExpirationMonth) {
        setExpirationDate(creditCardExpirationMonth, null);
        this.creditCardExpirationMonth = creditCardExpirationMonth;
    }

    public Integer getCreditCardExpirationYear() {
        if (getCreditCardExpiration() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(getCreditCardExpiration());
            creditCardExpirationYear = calendar.get(Calendar.YEAR);
        }
        return creditCardExpirationYear;
    }

    public void setCreditCardExpirationYear(Integer creditCardExpirationYear) {
        setExpirationDate(null, creditCardExpirationYear);
        this.creditCardExpirationYear = creditCardExpirationYear;
    }

    private void setExpirationDate(Integer month, Integer year) {
        Calendar calendar = Calendar.getInstance();
        if (getCreditCardExpiration() != null) {
            calendar.setTime(getCreditCardExpiration());
        }
        if (month != null) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        if (year != null) {
            calendar.set(Calendar.YEAR, year);
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1); // need to reset to 1 prior to
        // getting max day
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        setCreditCardExpiration(calendar.getTime());
    }

    public String getCreditCardSecurityCode() {
        return creditCardSecurityCode;
    }

    public void setCreditCardSecurityCode(String creditCardSecurityCode) {
        this.creditCardSecurityCode = creditCardSecurityCode;
    }

    public Site getSite() {
        return person != null ? person.getSite() : null;
    }

    public List<String> getExpirationMonthList() {
        List<String> monthList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            String month = String.valueOf(i);
            if (month.length() == 1) {
                month = "0" + month;
            }
            monthList.add(month);
        }
        return monthList;
    }

    public List<String> getExpirationYearList() {
        List<String> yearList = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            yearList.add(String.valueOf(year + i));
        }
        return yearList;
    }
    
    public void setFromAddressAware(AddressAware addressAware) {
        setSelectedAddress(addressAware.getSelectedAddress());
        setAddress(addressAware.getSelectedAddress());
        setAddressType(addressAware.getAddressType());
    }

    @Override
    public Address getSelectedAddress() {
        if (selectedAddress == null) {
            selectedAddress = new Address(); // created only because spring needs to bind to it
        }
        return selectedAddress;
    }

    @Override
    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
    
    public void setFromPhoneAware(PhoneAware phoneAware) {
        setSelectedPhone(phoneAware.getSelectedPhone());
        setPhone(phoneAware.getSelectedPhone());
        setPhoneType(phoneAware.getPhoneType());
    }

    @Override
    public Phone getSelectedPhone() {
        if (selectedPhone == null) {
            selectedPhone = new Phone(); // created only because spring needs to bind to it
        }
        return selectedPhone;
    }

    @Override
    public void setSelectedPhone(Phone selectedPhone) {
        this.selectedPhone = selectedPhone;
    }

    @Override
    public FormBeanType getAddressType() {
        return this.addressType;
    }

    @Override
    public void setAddressType(FormBeanType type) {
        this.addressType = type;
    }

    @Override
    public FormBeanType getPhoneType() {
        return this.phoneType;
    }

    @Override
    public void setPhoneType(FormBeanType type) {
        this.phoneType = type;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * If no profile name was entered, create one from the last 4 digits of the account/card number
     */
    public void createDefaultProfileName() {
        if (this.profile == null) {
            StringBuilder sb = new StringBuilder();

            if (ACH.equals(paymentType)) {
                sb.append(ACH); // TODO: move to message bundle and lookup
                sb.append("****");
                sb.append(findLastFourDigits(this.achAccountNumber));
                this.profile = sb.toString();
            }
            else if (CREDIT_CARD.equals(paymentType)) {
                if (creditCardType != null) {
                    sb.append(creditCardType);
                }
                sb.append("****");
                sb.append(findLastFourDigits(this.creditCardNumber));
                this.profile = sb.toString();
            }
        }
    }

    private String findLastFourDigits(String number) {
        return number == null ? "" : (number.length() > 4 ? number.substring(number.length() - 4, number.length()) : number);
    }
    
    private String decryptAndMask(String encryptedString) {
        String clear = null;
        if (encryptedString != null) {
            clear = AES.decrypt(encryptedString);
            clear = mask(clear);
        }
        return clear;
    }
    
    private String mask(String clear) {
        if (clear != null && clear.length() >= 4) {
            return "****" + clear.substring(clear.length() - 4);
        }
        return clear;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields are populated
     * @return true if this PaymentSource has all required fields populated
     */
    public boolean isValid() {
        if (ACH.equals(paymentType)) {
            return StringUtils.hasText(achHolderName) &&
                StringUtils.hasText(achAccountNumber) &&
                StringUtils.hasText(achRoutingNumber);
        }
        else if (CREDIT_CARD.equals(paymentType)) {
            return StringUtils.hasText(creditCardHolderName) &&
                StringUtils.hasText(creditCardType) &&
                StringUtils.hasText(creditCardNumber);
        }
        else if (CASH.equals(paymentType) || CHECK.equals(paymentType)) {
            return true; // TODO: what are the validity constraints for CASH and CHECK?
        }
        return false;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof PaymentSource)) {
//            return false;
//        }
//        PaymentSource ps = (PaymentSource) obj;
//        EqualsBuilder eb = new EqualsBuilder();
//        eb.append(getPaymentType(), ps.getPaymentType());
//        if (ACH.equals(getPaymentType())) {
//            eb.append(achHolderName, ps.achHolderName).append(achAccountNumber, ps.achAccountNumber).append(achAccountNumberEncrypted, ps.achAccountNumberEncrypted);
//        } 
//        else if (CREDIT_CARD.equals(getPaymentType())) {
//            eb.append(creditCardHolderName, ps.creditCardHolderName).append(creditCardType, ps.creditCardType).append(creditCardNumberEncrypted, ps.creditCardNumberEncrypted);
//        }
//        return eb.isEquals();
//    }
//
//    @Override
//    public int hashCode() {
//        HashCodeBuilder hcb = new HashCodeBuilder();
//        hcb.append(getPaymentType());
//        if (ACH.equals(getPaymentType())) {
//            hcb.append(achHolderName).append(achAccountNumber).append(achRoutingNumber);
//        } 
//        else if (CREDIT_CARD.equals(getPaymentType())) {
//            hcb.append(creditCardHolderName).append(creditCardType).append(creditCardNumber);
//        }
//        return hcb.hashCode();
//    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (paymentType != null) {
            if (ACH.equals(paymentType)) {
                clearCredit();
            }
            else if (CREDIT_CARD.equals(paymentType)) {
                clearACH();
            }
            else if (CHECK.equals(paymentType) || CASH.equals(paymentType)) {
                clearCredit();
                clearACH();
            }
        }
    }
    
    private void clearCredit() {
        setCreditCardHolderName(null);
        setCreditCardExpiration(null);
        setCreditCardNumber(null);
        setCreditCardSecurityCode(null);
        setCreditCardType(null);       
    }
    
    private void clearACH() {
        setAchHolderName(null);
        setAchAccountNumber(null);
        setAchRoutingNumber(null);
    }
    
    @Override
    public String getAuditShortDesc() {
    	return (getProfile() == null || getProfile().length() == 0) ? ""+getId() : getProfile();
    }

    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("profile", profile).append("paymentType", paymentType).append(creditCardHolderName, "creditCardHolderName").
            append("creditCardType", creditCardType).append("achHolderName", achHolderName).append("inactive", inactive).append("address", address).append("phone", phone).append("constituent", person).
            append("userCreated", userCreated).append("selectedAddress", selectedAddress).append("selectedPhone", selectedPhone).
            toString();
    }
}
