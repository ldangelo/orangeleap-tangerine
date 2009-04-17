package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.AddressService;
import com.orangeleap.tangerine.service.AuditService;
import com.orangeleap.tangerine.service.EmailService;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.service.PhoneService;
import com.orangeleap.tangerine.type.FormBeanType;

public abstract class AbstractPaymentService extends AbstractTangerineService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    protected AuditService auditService;

    @Resource(name = "addressService")
    protected AddressService addressService;

    @Resource(name = "phoneService")
    protected PhoneService phoneService;

    @Resource(name = "emailService")
    protected EmailService emailService;

    @Resource(name = "paymentSourceService")
    protected PaymentSourceService paymentSourceService;

    public void maintainEntityChildren(AbstractEntity entity, Person constituent) {
        if (entity instanceof AddressAware) {
            AddressAware addressAware = (AddressAware)entity;
            maintainAddressChild(addressAware, constituent);
        }
        if (entity instanceof PhoneAware) { 
            PhoneAware phoneAware = (PhoneAware)entity;
            maintainPhoneChild(phoneAware, constituent);
        }
        if (entity instanceof EmailAware) {
            EmailAware emailAware = (EmailAware)entity;
            maintainEmailChild(emailAware, constituent);
        }
        if (entity instanceof PaymentSourceAware) {
            maintainPaymentSourceChild(entity, constituent);
        }
    }

    private void maintainAddressChild(AddressAware addressAware, Person constituent) {
        if (FormBeanType.NEW.equals(addressAware.getAddressType())) {
            Address newAddress = addressAware.getAddress();
            newAddress.setPersonId(constituent.getId());
            
            Address existingAddress = addressService.alreadyExists(newAddress);
            if (existingAddress != null) {
                addressAware.setAddress(existingAddress);
                addressAware.setSelectedAddress(existingAddress);
                addressAware.setAddressType(FormBeanType.EXISTING);
            }
            else {
                addressAware.setAddress(addressService.save(newAddress));
                addressAware.setSelectedAddress(addressAware.getAddress());
            }
        }
        else if (FormBeanType.NONE.equals(addressAware.getAddressType())) {
            addressAware.setSelectedAddress(null);
            addressAware.setAddress(null);
        }
    }
    
    private void maintainPhoneChild(PhoneAware phoneAware, Person constituent) {
        if (FormBeanType.NEW.equals(phoneAware.getPhoneType())) {
            Phone newPhone = phoneAware.getPhone();
            newPhone.setPersonId(constituent.getId());
            
            Phone existingPhone = phoneService.alreadyExists(newPhone);
            if (existingPhone != null) {
                phoneAware.setPhone(existingPhone);
                phoneAware.setSelectedPhone(existingPhone);
                phoneAware.setPhoneType(FormBeanType.EXISTING);
            }
            else {
                phoneAware.setPhone(phoneService.save(newPhone));
                phoneAware.setSelectedPhone(phoneAware.getPhone());
            }
        }
        else if (FormBeanType.NONE.equals(phoneAware.getPhoneType())) {
            phoneAware.setSelectedPhone(null);
            phoneAware.setPhone(null);
        }
    }
    
    private void maintainEmailChild(EmailAware emailAware, Person constituent) {
        if (FormBeanType.NEW.equals(emailAware.getEmailType())) {
            Email newEmail = emailAware.getEmail();
            newEmail.setPersonId(constituent.getId());
            
            Email existingEmail = emailService.alreadyExists(newEmail);
            if (existingEmail != null) {
                emailAware.setEmail(existingEmail);
                emailAware.setSelectedEmail(existingEmail);
                emailAware.setEmailType(FormBeanType.EXISTING);
            }
            else {
                emailAware.setEmail(emailService.save(newEmail));
                emailAware.setSelectedEmail(emailAware.getEmail());
            }
        }
        else if (FormBeanType.NONE.equals(emailAware.getEmailType())) {
            emailAware.setSelectedEmail(null);
            emailAware.setEmail(null);
        }
    }
    
    private void maintainPaymentSourceChild(AbstractEntity entity, Person constituent) {
        PaymentSourceAware paymentSourceAware = (PaymentSourceAware)entity;
        if (PaymentSource.CASH.equals(paymentSourceAware.getPaymentType()) || 
            PaymentSource.CHECK.equals(paymentSourceAware.getPaymentType()) || 
            FormBeanType.NONE.equals(paymentSourceAware.getPaymentSourceType())) {
            paymentSourceAware.setSelectedPaymentSource(null);
            paymentSourceAware.setPaymentSource(null);
        }
        else if (PaymentSource.ACH.equals(paymentSourceAware.getPaymentType()) || 
                PaymentSource.CREDIT_CARD.equals(paymentSourceAware.getPaymentType())) {
            if (FormBeanType.NEW.equals(paymentSourceAware.getPaymentSourceType())) {
                PaymentSource newPaymentSource = paymentSourceAware.getPaymentSource();
                newPaymentSource.setPerson(constituent);
                newPaymentSource.setPaymentType(paymentSourceAware.getPaymentType());
                
                if (entity instanceof AddressAware && FormBeanType.NONE.equals(((AddressAware)entity).getAddressType()) == false) {
                    newPaymentSource.setFromAddressAware((AddressAware)entity);
                }
                if (entity instanceof PhoneAware && FormBeanType.NONE.equals(((PhoneAware)entity).getPhoneType()) == false) { 
                    newPaymentSource.setFromPhoneAware((PhoneAware)entity);
                }
    
                paymentSourceAware.setPaymentSource(paymentSourceService.maintainPaymentSource(newPaymentSource));
                paymentSourceAware.setSelectedPaymentSource(paymentSourceAware.getPaymentSource());
            }
            else if (FormBeanType.EXISTING.equals(paymentSourceAware.getPaymentSourceType())) {
                paymentSourceService.maintainPaymentSource(paymentSourceAware.getSelectedPaymentSource());
                paymentSourceAware.setPaymentSource(paymentSourceAware.getSelectedPaymentSource());
            }
        }
    }
}
