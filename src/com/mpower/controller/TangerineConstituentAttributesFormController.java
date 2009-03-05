package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.mpower.controller.address.AddressEditor;
import com.mpower.controller.email.EmailEditor;
import com.mpower.controller.payment.PaymentSourceEditor;
import com.mpower.controller.phone.PhoneEditor;
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
import com.mpower.service.EmailService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;
import com.mpower.util.StringConstants;

public abstract class TangerineConstituentAttributesFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="paymentSourceService")
    protected PaymentSourceService paymentSourceService;
    
    @Resource(name="addressService")
    protected AddressService addressService;
    
    @Resource(name="phoneService")
    protected PhoneService phoneService;
    
    @Resource(name="emailService")
    protected EmailService emailService;
    
    protected boolean bindPaymentSource = true;
    protected boolean bindAddress = true;
    protected boolean bindPhone = true;
    protected boolean bindEmail = true;
    
    /**
     * If you do not want to bind to PaymentSources, set to false.  Default is true
     * @param bindPaymentSource
     */
    public void setBindPaymentSource(boolean bindPaymentSource) {
        this.bindPaymentSource = bindPaymentSource;
    }

    /**
     * If you do not want to bind to Addresses, set to false.  Default is true
     * @param bindAddress
     */
    public void setBindAddress(boolean bindAddress) {
        this.bindAddress = bindAddress;
    }

    /**
     * If you do not want to bind to Phones, set to false.  Default is true
     * @param bindPhone
     */
    public void setBindPhone(boolean bindPhone) {
        this.bindPhone = bindPhone;
    }

    /**
     * If you do not want to bind to Emails, set to false.  Default is true
     * @param bindEmail
     */
    public void setBindEmail(boolean bindEmail) {
        this.bindEmail = bindEmail;
    }
    
    protected Person getConstituent(HttpServletRequest request) {
        Long constituentId = getConstituentId(request);
        if (constituentId != null) {
            return constituentService.readConstituentById(constituentId); // TODO: do we need to check if the user can view this person (authorization)?
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    protected void addConstituentToReferenceData(HttpServletRequest request, Map refData) {
        Person constituent = getConstituent(request);
        if (constituent != null) {
            refData.put(StringConstants.PERSON, getConstituent(request));
        }
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        if (bindPaymentSource) {
            binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(this.getConstituentIdString(request)));
        }
        if (bindAddress) {
            binder.registerCustomEditor(Address.class, new AddressEditor(this.getConstituentIdString(request)));
        }
        if (bindPhone) {
            binder.registerCustomEditor(Phone.class, new PhoneEditor(this.getConstituentIdString(request)));
        }
        if (bindEmail) {
            binder.registerCustomEditor(Email.class, new EmailEditor(this.getConstituentIdString(request)));
        }
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        AbstractEntity entity = (AbstractEntity)super.formBackingObject(request);        
        if (isFormSubmission(request)) {
            userCreateNew(request, entity);
        }
        return entity;
    }
    
    protected void userCreateNew(HttpServletRequest request, AbstractEntity entity) {
        if (bindAddress && entity instanceof AddressAware) {
            this.userCreateNewAddress(request, (AddressAware)entity);
        }
        if (bindPhone && entity instanceof PhoneAware) {
            this.userCreateNewPhone(request, (PhoneAware)entity);
        }
        if (bindEmail && entity instanceof EmailAware) {
            this.userCreateNewEmail(request, (EmailAware)entity);
        }
        if (bindPaymentSource && entity instanceof PaymentSourceAware) {
            this.userCreateNewPaymentSource(request, (PaymentSourceAware)entity);
        }
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        if (bindPaymentSource && command instanceof PaymentSourceAware) {
            PaymentSourceAware obj = (PaymentSourceAware)command;
            PaymentSource selectedPaymentSource = obj.getSelectedPaymentSource();
            if (selectedPaymentSource != null) {
                if (selectedPaymentSource.getId() != null) {
                    obj.setPaymentSource(selectedPaymentSource);
                    obj.setPaymentType(selectedPaymentSource.getType());
                }
                else {
                    // new payment source, set the type
                    obj.getPaymentSource().setPaymentType(obj.getPaymentType());                    
                }
            }
        }

        if (bindAddress && command instanceof AddressAware) {
            AddressAware obj = (AddressAware) command;
            Address selectedAddress = obj.getSelectedAddress();
            if (selectedAddress != null && selectedAddress.getId() != null) {
                obj.setAddress(selectedAddress);
            }
        }

        if (bindPhone && command instanceof PhoneAware) {
            PhoneAware obj = (PhoneAware) command;
            Phone selectedPhone = obj.getSelectedPhone();
            if (selectedPhone != null && selectedPhone.getId() != null) {
                obj.setPhone(selectedPhone);
            }
        }

        if (bindEmail && command instanceof EmailAware) {
            EmailAware obj = (EmailAware) command;
            Email selectedEmail = obj.getSelectedEmail();
            if (selectedEmail != null && selectedEmail.getId() != null) {
                obj.setEmail(selectedEmail);
            }
        }
        super.onBind(request, command, errors);
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);

        /**
         * If NO address/phone/etc is requested, this must be done AFTER binding and validation
         */
        if (!errors.hasErrors() && isFormSubmission(request)) {
            if (bindAddress && command instanceof AddressAware) {
                this.setAddressToNone(request, (AddressAware)command);
            }
            if (bindPhone && command instanceof PhoneAware) {
                this.setPhoneToNone(request, (PhoneAware)command);
            }
            if (bindEmail && command instanceof EmailAware) {
                this.setEmailToNone(request, (EmailAware)command);
            }
            if (bindPaymentSource && command instanceof PaymentSourceAware) {
                this.setPaymentSourceToNone(request, (PaymentSourceAware)command);
            }
        }        
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        this.addConstituentToReferenceData(request, refData);

        if (bindPaymentSource) {
            List<PaymentSource> paymentSources = paymentSourceService.readActivePaymentSourcesACHCreditCard(this.getConstituentId(request));
            refData.put(StringConstants.PAYMENT_SOURCES, paymentSources);
        }
        if (bindAddress) {
            List<Address> addresses = addressService.filterValid(this.getConstituentId(request));
            refData.put(StringConstants.ADDRESSES, addresses);
        }
        if (bindPhone) {
            List<Phone> phones = phoneService.filterValid(this.getConstituentId(request));
            refData.put(StringConstants.PHONES, phones);
        }
        if (bindEmail) {
            List<Email> emails = emailService.filterValid(this.getConstituentId(request));
            refData.put(StringConstants.EMAILS, emails);
        }
        return refData;
    }

    protected void userCreateNewAddress(HttpServletRequest request, AddressAware addressAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_ADDRESS))) {
            Address addr = new Address(this.getConstituentId(request));
            addr.setUserCreated(true);
            addressAware.setAddress(addr);
        }
    }

    protected void userCreateNewPhone(HttpServletRequest request, PhoneAware phoneAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_PHONE))) {
            Phone phone = new Phone(this.getConstituentId(request));
            phone.setUserCreated(true);
            phoneAware.setPhone(phone);
        }
    }

    protected void userCreateNewEmail(HttpServletRequest request, EmailAware emailAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_EMAIL))) {
            Email email = new Email(this.getConstituentId(request));
            email.setUserCreated(true);
            emailAware.setEmail(email);
        }
    }

    protected void userCreateNewPaymentSource(HttpServletRequest request, PaymentSourceAware paymentSourceAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_PAYMENT_SOURCE))) {
            PaymentSource source = new PaymentSource(this.getConstituent(request));
            source.setUserCreated(true);
            paymentSourceAware.setPaymentSource(source);
        }
    }
    
    protected void setAddressToNone(HttpServletRequest request, AddressAware addressAware) {
        if (StringConstants.NONE.equals(request.getParameter(StringConstants.SELECTED_ADDRESS))) {
            addressAware.setAddress(new Address(this.getConstituentId(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }
    protected void setPhoneToNone(HttpServletRequest request, PhoneAware phoneAware) {
        if (StringConstants.NONE.equals(request.getParameter(StringConstants.SELECTED_PHONE))) {
            phoneAware.setPhone(new Phone(this.getConstituentId(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }

    protected void setEmailToNone(HttpServletRequest request, EmailAware emailAware) {
        if (StringConstants.NONE.equals(request.getParameter(StringConstants.SELECTED_EMAIL))) {
            emailAware.setEmail(new Email(this.getConstituentId(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }

    protected void setPaymentSourceToNone(HttpServletRequest request, PaymentSourceAware paymentSourceAware) {
        if (StringConstants.NONE.equals(request.getParameter(StringConstants.SELECTED_PAYMENT_SOURCE))) {
            paymentSourceAware.setPaymentSource(new PaymentSource(this.getConstituent(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }
}
