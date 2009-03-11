/*
 * Objects of this type contain AbstractCommunication entities
 */

package com.mpower.domain.model.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.model.AbstractCustomizableEntity;
import com.mpower.service.AddressService;
import com.mpower.service.EmailService;
import com.mpower.service.PhoneService;

@SuppressWarnings("serial")
public abstract class AbstractCommunicatorEntity extends AbstractCustomizableEntity {
	
	
    private Address primaryAddress;
    private Email primaryEmail;
    private Phone primaryPhone;

    private List<Address> addresses;
    private List<Email> emails;
    private List<Phone> phones;

    private Map<String, Address> addressMap = null;
    private Map<String, Email> emailMap = null;
    private Map<String, Phone> phoneMap = null;
    
    
    // Called by CustomizableSqlMapClientTemplate when object is loaded.
    public void setCommunicationFields(ApplicationContext applicationContext) {
    	
    	if (applicationContext == null) return;
    	
    	AddressService addressService;
    	EmailService emailService;
    	PhoneService phoneService;
    	try {
	    	addressService = (AddressService)applicationContext.getBean("addressService");
	    	emailService = (EmailService)applicationContext.getBean("emailService");
	    	phoneService = (PhoneService)applicationContext.getBean("phoneService");
    	} catch (NoSuchBeanDefinitionException e) {
    		return;
    	}

    	addresses = addressService.filterValid(getId());
    	emails = emailService.filterValid(getId());
    	phones = phoneService.filterValid(getId());

    	primaryAddress = addressService.filterByPrimary(addresses, getId());
    	primaryEmail = emailService.filterByPrimary(emails, getId());
    	primaryPhone = phoneService.filterByPrimary(phones, getId());
    
    }

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Map<String, Address> getAddressMap() {
        if (addressMap == null) {
            addressMap = buildMap(getAddresses());
        }
        return addressMap;
    }

    public List<Email> getEmails() {
        if (emails == null) {
            emails = new ArrayList<Email>();
        }
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public Map<String, Email> getEmailMap() {
        if (emailMap == null) {
            emailMap = buildMap(getEmails());
        }
        return emailMap;
    }

    public List<Phone> getPhones() {
        if (phones == null) {
            phones = new ArrayList<Phone>();
        }
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Map<String, Phone> getPhoneMap() {
        if (phoneMap == null) {
            phoneMap = buildMap(getPhones());
        }
        return phoneMap;
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractCommunicationEntity> Map<String, T> buildMap(List<T> masterList) {
        Map<String, T> map = new HashMap<String, T>();
        if (masterList != null) {
            for (AbstractCommunicationEntity entity : masterList) {
                map.put(entity.getCommunicationType(), (T) entity);
            }
        }
        return map;
    }

	public void setPrimaryAddress(Address primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	public Address getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryEmail(Email primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public Email getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryPhone(Phone primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public Phone getPrimaryPhone() {
		return primaryPhone;
	}

    
    
}