package com.mpower.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.AddressAware;
import com.mpower.domain.model.EmailAware;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.PaymentSourceAware;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.PhoneAware;
import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.EmailService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;
import com.mpower.type.FormBeanType;

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
            PaymentSourceAware paymentSourceAware = (PaymentSourceAware)entity;
            maintainPaymentSourceChild(paymentSourceAware, constituent);
        }
    }

    private void maintainAddressChild(AddressAware addressAware, Person constituent) {
        if (FormBeanType.NEW.equals(addressAware.getAddressType())) {
            Address newAddress = addressAware.getAddress();
            newAddress.setPersonId(constituent.getId());
            addressAware.setAddress(addressService.save(newAddress));
            addressAware.setSelectedAddress(addressAware.getAddress());
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
            phoneAware.setPhone(phoneService.save(newPhone));
            phoneAware.setSelectedPhone(phoneAware.getPhone());
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
            emailAware.setEmail(emailService.save(newEmail));
            emailAware.setSelectedEmail(emailAware.getEmail());
        }
        else if (FormBeanType.NONE.equals(emailAware.getEmailType())) {
            emailAware.setSelectedEmail(null);
            emailAware.setEmail(null);
        }
    }
    
    private void maintainPaymentSourceChild(PaymentSourceAware paymentSourceAware, Person constituent) {
        if (FormBeanType.NEW.equals(paymentSourceAware.getPaymentSourceType())) {
            PaymentSource newPaymentSource = paymentSourceAware.getPaymentSource();
            newPaymentSource.setPerson(constituent);
            paymentSourceAware.setPaymentSource(paymentSourceService.maintainPaymentSource(newPaymentSource));
            paymentSourceAware.setSelectedPaymentSource(paymentSourceAware.getPaymentSource());
        }
        else if (FormBeanType.NONE.equals(paymentSourceAware.getPaymentSourceType())) {
            paymentSourceAware.setSelectedPaymentSource(null);
            paymentSourceAware.setPaymentSource(null);
        }
    }
}
