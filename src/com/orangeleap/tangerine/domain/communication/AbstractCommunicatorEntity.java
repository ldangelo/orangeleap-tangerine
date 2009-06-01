/*
 * Objects of this type contain AbstractCommunication entities
 */

package com.orangeleap.tangerine.domain.communication;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PhoneService;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings("serial")
public abstract class AbstractCommunicatorEntity extends AbstractCustomizableEntity {
	
	
    private Address primaryAddress = new Address();
    private Email primaryEmail = new Email();
    private Phone primaryPhone = new Phone();

    private List<Address> addresses = new ArrayList<Address>();
    private List<Email> emails = new ArrayList<Email>();
    private List<Phone> phones = new ArrayList<Phone>();

    
    // Called by CustomizableSqlMapClientTemplate when object is loaded.
    public void setCommunicationFields(ApplicationContext applicationContext) {
    	
    	if (applicationContext == null) {
            return;
        }
    	
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

    	addresses = addressService.readByConstituentId(getId());
    	emails = emailService.readByConstituentId(getId());
    	phones = phoneService.readByConstituentId(getId());

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

    public List<Email> getEmails() {
        if (emails == null) {
            emails = new ArrayList<Email>();
        }
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
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

	public boolean canReceiveMail() {
		List<Address> addresses = getAddresses();
		for (Address a: addresses) {
			if(a.isValid() && a.isReceiveCorrespondence() && !a.isInactive()) return true;
		}
		return false;
	}
	
    public boolean canReceiveEmail() {
    	List<Email> emails = getEmails();
    	
    	for (Email e: emails) {
    		if (e.isReceiveCorrespondence() && !e.isInactive() && !e.isUndeliverable()) return true;
    	}
    	
    	return false;
    }
    
}