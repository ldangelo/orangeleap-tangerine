/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.annotation.NotAuditable;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.util.AES;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
public class PaymentSource extends AbstractEntity implements Inactivatible, AddressAware, PhoneAware {

    private static final long serialVersionUID = 1L;
    public static final String ACH = "ACH";
    public static final String CREDIT_CARD = "Credit Card";
    public static final String CASH = "Cash";
    public static final String CHECK = "Check";
    public static final String OTHER = "Other";

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
	private String checkHolderName;
	@NotAuditable
	protected String checkAccountNumber;
	@NotAuditable
	protected String checkAccountNumberEncrypted;
	protected String checkRoutingNumber;
	private String lastFourDigits;

	private Constituent constituent;
	private Address address = new Address(); // Created only because spring binds to it
    private Phone phone = new Phone(); // Created only because spring binds to it

    public PaymentSource() {
    }

    public PaymentSource(Constituent constituent) {
        this();
        this.constituent = constituent;
    }

	public PaymentSource(Long id, Constituent constituent) {
	    this(constituent);
	    this.id = id;
	}

    public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
        if (address != null && constituent != null) {
            address.setConstituentId(constituent.getId());
        }
        if (phone != null && constituent != null) {
            phone.setConstituentId(constituent.getId());
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
        return creditCardHolderName;
    }

    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    public String getAchHolderName() {
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
        }
        else {
            creditCardNumberEncrypted = null;
        }
    }

    public String getCreditCardNumberDisplay() {
        return AES.decryptAndMask(creditCardNumberEncrypted);
    }

    public void setCreditCardNumberDisplay(String str) {
        // no-op
    }

    public String getCreditCardNumberReadOnly() {
        return getCreditCardNumberDisplay();
    }

    public void setCreditCardNumberReadOnly(String str) {
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
        return AES.mask(achRoutingNumber);
    }

    public void setAchRoutingNumberDisplay(String str) {
        // no-op
    }

    public String getAchRoutingNumberReadOnly() {
        return getAchRoutingNumberDisplay();
    }

    public void setAchRoutingNumberReadOnly(String str) {
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
        }
        else {
            achAccountNumberEncrypted = null;
        }
    }

    public String getAchAccountNumberDisplay() {
        return AES.decryptAndMask(achAccountNumberEncrypted);
    }

    public void setAchAccountNumberDisplay(String str) {
        // no-op
    }

    public String getAchAccountNumberReadOnly() {
        return getAchAccountNumberDisplay();
    }

    public void setAchAccountNumberReadOnly(String str) {
        // no-op
    }

	public String getCheckHolderName() {
		return checkHolderName;
	}

	public void setCheckHolderName(String checkHolderName) {
		this.checkHolderName = checkHolderName;
	}

	public String getCheckAccountNumberEncrypted() {
		return checkAccountNumberEncrypted;
	}

	public void setCheckAccountNumberEncrypted(String checkAccountNumberEncrypted) {
		this.checkAccountNumberEncrypted = checkAccountNumberEncrypted;
	}

	public String getCheckAccountNumber() {
		if (checkAccountNumberEncrypted != null) {
		    return AES.decrypt(checkAccountNumberEncrypted);
		}
		return null;
	}

	public void setCheckAccountNumber(String checkAccountNumber) {
		this.checkAccountNumber = checkAccountNumber;
		if (checkAccountNumber != null) {
		    checkAccountNumberEncrypted = AES.encrypt(checkAccountNumber);
		}
		else {
		    checkAccountNumberEncrypted = null;
		}
	}

	public String getCheckAccountNumberReadOnly() {
		return AES.decryptAndMask(checkAccountNumberEncrypted);
	}

	public void setCheckAccountNumberReadOnly(String str) {
	    // no-op
	}

	public String getCheckAccountNumberDisplay() {
		return getCheckAccountNumberReadOnly();
	}

	public void setCheckAccountNumberDisplay(String str) {
	    // no-op
	}

	public String getCheckRoutingNumber() {
		return checkRoutingNumber;
	}

	public void setCheckRoutingNumber(String checkRoutingNumber) {
		this.checkRoutingNumber = checkRoutingNumber;
	}

	public String getCheckRoutingNumberDisplay() {
		return AES.mask(checkRoutingNumber);
	}

	public void setCheckRoutingNumberDisplay(String str) {
	    // no-op
	}

	public String getLastFourDigits() {
	    return lastFourDigits;
	}

	public void setLastFourDigits(String lastFourDigits) {
	    this.lastFourDigits = lastFourDigits;
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
        return constituent != null ? constituent.getSite() : null;
    }

    public static List<String> getExpirationMonthList() {
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

    public static List<String> getExpirationYearList() {
        List<String> yearList = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            yearList.add(String.valueOf(year + i));
        }
        return yearList;
    }

    public void setFromAddressAware(AddressAware addressAware) {
        setAddress(addressAware.getAddress());
    }

    public void setFromPhoneAware(PhoneAware phoneAware) {
        setPhone(phoneAware.getPhone());
    }

    /**
     * If no profile name was entered, create one from the last 4 digits of the account/card/check account number
     */
    public void createDefaultProfileName() {
        if (this.profile == null) {
            StringBuilder sb = new StringBuilder();
            if (ACH.equals(paymentType)) {
                sb.append(TangerineMessageAccessor.getMessage(ACH));
                sb.append(StringConstants.MASK_START);
                sb.append(getLastFourDigits());
                this.profile = sb.toString();
            }
            else if (CREDIT_CARD.equals(paymentType)) {
                if (creditCardType != null) {
                    sb.append(creditCardType);
                }
	            sb.append(StringConstants.MASK_START);
                sb.append(getLastFourDigits());
                this.profile = sb.toString();
            }
	        else if (CHECK.equals(paymentType)) {
	            sb.append(TangerineMessageAccessor.getMessage("check"));
	            sb.append(StringConstants.MASK_START);
				sb.append(getLastFourDigits());
	            this.profile = sb.toString();
            }
        }
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields are populated
     *
     * @return true if this PaymentSource has all required fields populated
     */
    public boolean isValid() {
        if (ACH.equals(paymentType)) {
            return StringUtils.hasText(getAchHolderName()) &&
                    StringUtils.hasText(getAchAccountNumber()) &&
                    StringUtils.hasText(getAchRoutingNumber());
        }
        else if (CREDIT_CARD.equals(paymentType)) {
            return StringUtils.hasText(getCreditCardHolderName()) &&
                    StringUtils.hasText(getCreditCardType()) &&
                    StringUtils.hasText(getCreditCardNumber());
        }
        else if (CHECK.equals(paymentType)) {
            return StringUtils.hasText(getCheckAccountNumber()) &&
		            StringUtils.hasText(getCheckRoutingNumber());
        }
        else if (CASH.equals(paymentType)) {
            return true;
        }
        return false;
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (paymentType != null) {
            if (ACH.equals(paymentType)) {
                clearCredit();
	            clearCheck();
                if (getLastFourDigits() == null) {
                    setLastFourDigits(AES.findLastFourDigits(getAchAccountNumber()));
                }
            }
            else if (CREDIT_CARD.equals(paymentType)) {
                clearACH();
	            clearCheck();
                if (getLastFourDigits() == null) {
                    setLastFourDigits(AES.findLastFourDigits(getCreditCardNumber()));
                }
            }
            else if (CHECK.equals(paymentType)) {
                clearCredit();
                clearACH();
	            if (getLastFourDigits() == null) {
	                setLastFourDigits(AES.findLastFourDigits(getCheckAccountNumber()));
	            }
            }
	        else {
	            clearACH();
		        clearCheck();
	            clearCredit();
	            setLastFourDigits(null);
            }
        }
        createDefaultProfileName();
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

	public void clearCheck() {
		setCheckHolderName(null);
	    setCheckRoutingNumber(null);
	    setCheckAccountNumber(null);
	}

    @Override
    public String getAuditShortDesc() {
        return (getProfile() == null || getProfile().length() == 0) ? "" + getId() : getProfile();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("profile", profile).append("paymentType", paymentType).append(creditCardHolderName, "creditCardHolderName").
                append("creditCardType", creditCardType).append("achHolderName", achHolderName).
		        append("inactive", inactive).append("address", address).append("phone", phone).append("constituent", constituent).
                toString();
    }
}
