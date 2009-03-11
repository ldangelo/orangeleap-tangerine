/*
 * Objects of this type contain AbstractCommunication entities
 */

package com.orangeleap.tangerine.domain.communication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PhoneService;

@SuppressWarnings("serial")
public abstract class AbstractCommunicatorEntity extends AbstractCustomizableEntity {
	
	
    private Address primaryAddress = new Address();
    private Email primaryEmail = new Email();
    private Phone primaryPhone = new Phone();

    private List<Address> addresses = new ArrayList<Address>();
    private List<Email> emails = new ArrayList<Email>();
    private List<Phone> phones = new ArrayList<Phone>();

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
            addressMap = buildMap(getAddresses(), Address.class);
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
            emailMap = buildMap(getEmails(), Email.class);
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
            phoneMap = buildMap(getPhones(), Phone.class);
        }
        return phoneMap;
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractCommunicationEntity> Map<String, T> buildMap(List<T> masterList, final Class clazz) {
        
    	Map<String, T> map = new LinkedHashMap<String, T>() {
        	  public T get(Object key) {
        	        T o = super.get(key);
        	        if (o == null) {
        	            o = getNew();
        	            super.put((String)key, o);
        	        }
        	        return o;
        	  }
        	  private T getNew() {
        		  try {
        			  return (T)clazz.newInstance();
        		  } catch (Exception e) {
        			  return null;
        		  }
        	  }
        };
        
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