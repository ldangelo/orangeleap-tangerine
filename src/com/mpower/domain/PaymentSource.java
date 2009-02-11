package com.mpower.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.NotAuditable;
import com.mpower.util.AES;
import com.mpower.util.Utilities;

@Entity
@Table(name = "PAYMENT_SOURCE")
public class PaymentSource implements SiteAware, AddressAware, PhoneAware, ConstituentInfo, Inactivatible, Serializable {

    private static final long serialVersionUID = 1L;
    public static final String ACH = "ACH";
    public static final String CREDIT_CARD = "Credit Card";
    public static final String CASH = "Cash";
    public static final String CHECK = "Check";

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PAYMENT_SOURCE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address = new Address(person);

    @ManyToOne
    @JoinColumn(name = "PHONE_ID")
    private Phone phone = new Phone(person);

    @Column(name = "PAYMENT_PROFILE")
    private String profile;

    @Column(name = "PAYMENT_TYPE")
    private String type = CREDIT_CARD;

    @Column(name = "CREDIT_CARD_HOLDER_NAME")
    private String creditCardHolderName;

    @Column(name = "CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CREDIT_CARD_NUMBER")
    @NotAuditable
    private String creditCardNumberEncrypted;

    @Column(name = "CREDIT_CARD_EXPIRATION")
    private Date creditCardExpiration;

    @Column(name = "ACH_HOLDER_NAME")
    private String achHolderName;

    @Column(name = "ACH_ROUTING_NUMBER")
    @NotAuditable
    private String achRoutingNumber;

    @Column(name = "ACH_ACCOUNT_NUMBER")
    @NotAuditable
    private String achAccountNumberEncrypted;

    @Column(name = "INACTIVE")
    private boolean inactive = false;

    @Transient
    private Integer creditCardExpirationMonth;

    @Transient
    private String creditCardExpirationMonthText;

    @Transient
    private Integer creditCardExpirationYear;

    @Transient
    @NotAuditable
    private String creditCardNumber;

    // absolutely don't store this in the db - see VISA merchant rules only used for processing
    @Transient
    @NotAuditable
    private String creditCardSecurityCode;

    @Transient
    @NotAuditable
    private String achAccountNumber;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    @Transient
    private Address selectedAddress = new Address(person);

    @Transient
    private Phone selectedPhone = new Phone(person);

    @Transient
    private boolean userCreated = false;
  
    public PaymentSource() {
    }

    public PaymentSource(Person person) {
        this();
        this.person = person;
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

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

            if (ACH.equals(type)) {
                sb.append(ACH); // TODO: move to message bundle and lookup
                sb.append("****");
                sb.append(findLastFourDigits(this.achAccountNumber));
                this.profile = sb.toString();
            }
            else if (CREDIT_CARD.equals(type)) {
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
        if (ACH.equals(type)) {
            return org.springframework.util.StringUtils.hasText(achHolderName) &&
            org.springframework.util.StringUtils.hasText(achAccountNumber) &&
            org.springframework.util.StringUtils.hasText(achRoutingNumber);
        }
        else if (CREDIT_CARD.equals(type)) {
            return org.springframework.util.StringUtils.hasText(creditCardHolderName) &&
            org.springframework.util.StringUtils.hasText(creditCardType) &&
            org.springframework.util.StringUtils.hasText(creditCardNumber);

        }
        else if (CASH.equals(type) || CHECK.equals(type)) {
            return true; // TODO: what are the validity constraints for CASH and CHECK?
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PaymentSource)) {
            return false;
        }
        PaymentSource ps = (PaymentSource) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getType(), ps.getType());
        if (ACH.equals(getType())) {
            eb.append(achHolderName, ps.achHolderName).append(achAccountNumber, ps.achAccountNumber).append(achAccountNumberEncrypted, ps.achAccountNumberEncrypted);
        } else if (CREDIT_CARD.equals(getType())) {
            eb.append(creditCardHolderName, ps.creditCardHolderName).append(creditCardType, ps.creditCardType).append(creditCardNumberEncrypted, ps.creditCardNumberEncrypted);
        }
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getType());
        if (ACH.equals(getType())) {
            hcb.append(achHolderName).append(achAccountNumber).append(achRoutingNumber);
        } else if (CREDIT_CARD.equals(getType())) {
            hcb.append(creditCardHolderName).append(creditCardType).append(creditCardNumber);
        }
        return hcb.hashCode();
    }

    @PrePersist
    @PreUpdate
    public void normalize() {
        if (type != null) {
            if (ACH.equals(getType())) {
                clearCredit();
            }
            else if (CREDIT_CARD.equals(getType())) {
                clearACH();
            }
            else if (CHECK.equals(getType()) || CASH.equals(getType())) {
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
}
