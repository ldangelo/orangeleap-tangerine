package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;

import com.mpower.controller.communication.address.AddressEditor;
import com.mpower.controller.communication.email.EmailEditor;
import com.mpower.controller.communication.phone.PhoneEditor;
import com.mpower.controller.payment.PaymentSourceEditor;
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
import com.mpower.type.FormBeanType;
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
        Person constituent = null;
        if (constituentId != null) {
            constituent = constituentService.readConstituentById(constituentId); // TODO: do we need to check if the user can view this person (authorization)?
        }
        if (constituent == null) {
            throw new IllegalArgumentException("The constituent ID was not found");
        }
        return constituent;
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
            binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(paymentSourceService, this.getConstituentIdString(request)));
        }
        if (bindAddress) {
            binder.registerCustomEditor(Address.class, new AddressEditor(addressService, this.getConstituentIdString(request)));
        }
        if (bindPhone) {
            binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService, this.getConstituentIdString(request)));
        }
        if (bindEmail) {
            binder.registerCustomEditor(Email.class, new EmailEditor(emailService, this.getConstituentIdString(request)));
        }
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        if (isFormSubmission(request)) {
            if (bindAddress && command instanceof AddressAware) {
                this.bindAddress(request, (AddressAware)command);
            }
            if (bindPhone && command instanceof PhoneAware) {
                this.bindPhone(request, (PhoneAware)command);
            }
            if (bindEmail && command instanceof EmailAware) {
                this.bindEmail(request, (EmailAware)command);
            }
            if (bindPaymentSource && command instanceof PaymentSourceAware) {
                this.bindPaymentSource(request, (PaymentSourceAware)command);
            }
        }        
        super.onBind(request, command, errors);
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

    protected void bindAddress(HttpServletRequest request, AddressAware addressAware) {
        String selectedAddress = request.getParameter(StringConstants.SELECTED_ADDRESS);
        if (StringConstants.NEW.equals(selectedAddress)) {
            addressAware.setAddressType(FormBeanType.NEW);
            addressAware.getAddress().setUserCreated(true);
        }
        else if (StringConstants.NONE.equals(selectedAddress)) {
            addressAware.setAddressType(FormBeanType.NONE);
        }
        else {
            addressAware.setAddressType(FormBeanType.EXISTING);
        }
    }

    protected void bindPhone(HttpServletRequest request, PhoneAware phoneAware) {
        String selectedPhone = request.getParameter(StringConstants.SELECTED_PHONE);
        if (StringConstants.NEW.equals(selectedPhone)) {
            phoneAware.setPhoneType(FormBeanType.NEW);
            phoneAware.getPhone().setUserCreated(true);
        }
        else if (StringConstants.NONE.equals(selectedPhone)) {
            phoneAware.setPhoneType(FormBeanType.NONE);
        }
        else {
            phoneAware.setPhoneType(FormBeanType.EXISTING);
        }
    }

    protected void bindEmail(HttpServletRequest request, EmailAware emailAware) {
        String selectedEmail = request.getParameter(StringConstants.SELECTED_EMAIL);
        if (StringConstants.NEW.equals(selectedEmail)) {
            emailAware.setEmailType(FormBeanType.NEW);
            emailAware.getEmail().setUserCreated(true);
        }
        else if (StringConstants.NONE.equals(selectedEmail)) {
            emailAware.setEmailType(FormBeanType.NONE);
        }
        else {
            emailAware.setEmailType(FormBeanType.EXISTING);
        }
    }
    
    protected void bindPaymentSource(HttpServletRequest request, PaymentSourceAware paymentSourceAware) {
        String selectedPaymentSource = request.getParameter(StringConstants.SELECTED_PAYMENT_SOURCE);
        if (StringConstants.NEW.equals(selectedPaymentSource)) {
            paymentSourceAware.setPaymentSourceType(FormBeanType.NEW);
            paymentSourceAware.getPaymentSource().setUserCreated(true);
            paymentSourceAware.setPaymentSourcePaymentType();
        }
        else if (StringConstants.NONE.equals(selectedPaymentSource)) {
            paymentSourceAware.setPaymentSourceType(FormBeanType.NONE);
        }
        else {
            paymentSourceAware.setPaymentSourceType(FormBeanType.EXISTING);
            paymentSourceAware.setPaymentSourceAwarePaymentType();
        }
    }
}
