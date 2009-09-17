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

/*
 * Objects of this type contain AbstractCommunication entities
 */

package com.orangeleap.tangerine.domain.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlType;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PhoneService;

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
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
            addressService = (AddressService) applicationContext.getBean("addressService");
            emailService = (EmailService) applicationContext.getBean("emailService");
            phoneService = (PhoneService) applicationContext.getBean("phoneService");
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
    
    @Override
    public Set<String> getFullTextSearchKeywords() {
		Set<String> set = new TreeSet<String>();
    	set.addAll(super.getFullTextSearchKeywords());
    	for (Address address: this.getAddresses()) {
    		set.addAll(address.getFullTextSearchKeywords());
    	}
    	for (Phone phone: this.getPhones()) {
    		set.addAll(phone.getFullTextSearchKeywords());
    	}
    	for (Email email: this.getEmails()) {
    		set.addAll(email.getFullTextSearchKeywords());
    	}
    	return set;
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
        for (Address a : addresses) {
            if (a.isValid() && a.isReceiveCorrespondence() && !a.isInactive()) return true;
        }
        return false;
    }

    public boolean canReceiveEmail() {
        List<Email> emails = getEmails();

        for (Email e : emails) {
            if (e.isReceiveCorrespondence() && !e.isInactive() && !e.isUndeliverable()) return true;
        }

        return false;
    }

}