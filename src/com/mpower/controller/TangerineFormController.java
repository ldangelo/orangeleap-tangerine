package com.mpower.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Address;
import com.mpower.domain.AddressAware;
import com.mpower.domain.Email;
import com.mpower.domain.EmailAware;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.PaymentSourceAware;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.domain.PhoneAware;
import com.mpower.domain.Viewable;
import com.mpower.service.PersonService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;
import com.mpower.util.StringConstants;

public abstract class TangerineFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected PersonService personService;
    protected SiteService siteService;
    protected String pageType;

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    /**
     * The default page type is the commandName.  Override for specific page types
     * @return pageType, the commandName
     */
    protected String getPageType() {
        return StringUtils.hasText(pageType) ? pageType: getCommandName();
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public Long getIdAsLong(HttpServletRequest request, String id) {
        return Long.valueOf(request.getParameter(id));
    }

    protected Long getPersonId(HttpServletRequest request) {
        return this.getIdAsLong(request, StringConstants.PERSON_ID);
    }

    protected String getPersonIdString(HttpServletRequest request) {
        return request.getParameter(StringConstants.PERSON_ID);
    }
    
    protected Person getPerson(HttpServletRequest request) {
        return personService.readPersonById(getPersonId(request)); // TODO: do we need to check if the user can view this person (authorization)?
    }

    @SuppressWarnings("unchecked")
    protected void addPersonToReferenceData(HttpServletRequest request, Map refData) {
        refData.put(StringConstants.PERSON, getPerson(request));
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true)); // TODO: custom date format
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Viewable viewable = findViewable(request);
        this.createFieldMaps(request, viewable);
        
        if (isFormSubmission(request)) {
            if (viewable instanceof AddressAware) {
                this.userCreateNewAddress(request, (AddressAware)viewable);
            }
            if (viewable instanceof PhoneAware) {
                this.userCreateNewPhone(request, (PhoneAware)viewable);
            }
            if (viewable instanceof EmailAware) {
                this.userCreateNewEmail(request, (EmailAware)viewable);
            }
            if (viewable instanceof PaymentSourceAware) {
                this.userCreateNewPaymentSource(request, (PaymentSourceAware)viewable);
            }
        }
        return viewable;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        if (command instanceof PaymentSourceAware) {
            PaymentSourceAware obj = (PaymentSourceAware)command;
            PaymentSource selectedPaymentSource = obj.getSelectedPaymentSource();
            if (selectedPaymentSource != null) {
                if (selectedPaymentSource.getId() != null) {
                    obj.setPaymentSource(selectedPaymentSource);
                    obj.setPaymentType(selectedPaymentSource.getType());
                }
                else {
                    // new payment source, set the type
                    obj.getPaymentSource().setType(obj.getPaymentType());                    
                }
            }
        }

        if (command instanceof AddressAware) {
            AddressAware obj = (AddressAware) command;
            Address selectedAddress = obj.getSelectedAddress();
            if (selectedAddress != null && selectedAddress.getId() != null) {
                obj.setAddress(selectedAddress);
            }
        }

        if (command instanceof PhoneAware) {
            PhoneAware obj = (PhoneAware) command;
            Phone selectedPhone = obj.getSelectedPhone();
            if (selectedPhone != null && selectedPhone.getId() != null) {
                obj.setPhone(selectedPhone);
            }
        }

        if (command instanceof EmailAware) {
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
        request.setAttribute(StringConstants.COMMAND_OBJECT, this.getCommandName()); // To be used by input.jsp to check for errors
        super.onBindAndValidate(request, command, errors);
        
        /**
         * If NO address/phone/etc is requested, this must be done AFTER binding and validation
         */
        if (!errors.hasErrors() && isFormSubmission(request)) {
            if (command instanceof AddressAware) {
                this.setAddressToNone(request, (AddressAware)command);
            }
            if (command instanceof PhoneAware) {
                this.setPhoneToNone(request, (PhoneAware)command);
            }
            if (command instanceof EmailAware) {
                this.setEmailToNone(request, (EmailAware)command);
            }
            if (command instanceof PaymentSourceAware) {
                this.setPaymentSourceToNone(request, (PaymentSourceAware)command);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        this.addPersonToReferenceData(request, refData);
        addRefData(request, this.getPersonId(request), refData);

        return refData;
    }

    protected void createFieldMaps(HttpServletRequest request, Viewable viewable) {
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(this.getPageType()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            viewable.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(this.getPageType()), SessionServiceImpl.lookupUserRoles(), viewable);
            viewable.setFieldValueMap(valueMap);
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        return new ModelAndView(new StringBuilder().append(getSuccessView()).append("?").append(StringConstants.PERSON_ID).append("=").append(getPersonId(request)).toString());
    }

    protected void userCreateNewAddress(HttpServletRequest request, AddressAware addressAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_ADDRESS))) {
            Address addr = new Address(this.getPerson(request));
            addr.setUserCreated(true);
            addressAware.setAddress(addr);
        }
    }

    protected void userCreateNewPhone(HttpServletRequest request, PhoneAware phoneAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_PHONE))) {
            Phone phone = new Phone(this.getPerson(request));
            phone.setUserCreated(true);
            phoneAware.setPhone(phone);
        }
    }

    protected void userCreateNewEmail(HttpServletRequest request, EmailAware emailAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_EMAIL))) {
            Email email = new Email(this.getPerson(request));
            email.setUserCreated(true);
            emailAware.setEmail(email);
        }
    }

    protected void userCreateNewPaymentSource(HttpServletRequest request, PaymentSourceAware paymentSourceAware) {
        if (StringConstants.NEW.equals(request.getParameter(StringConstants.SELECTED_PAYMENT_SOURCE))) {
            PaymentSource source = new PaymentSource(this.getPerson(request));
            source.setUserCreated(true);
            paymentSourceAware.setPaymentSource(source);
        }
    }
    
    protected void setAddressToNone(HttpServletRequest request, AddressAware addressAware) {
        if (false == StringUtils.hasText(request.getParameter(StringConstants.SELECTED_ADDRESS))) {
            addressAware.setAddress(new Address(this.getPerson(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }
    protected void setPhoneToNone(HttpServletRequest request, PhoneAware phoneAware) {
        if (false == StringUtils.hasText(request.getParameter(StringConstants.SELECTED_PHONE))) {
            phoneAware.setPhone(new Phone(this.getPerson(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }

    protected void setEmailToNone(HttpServletRequest request, EmailAware emailAware) {
        if (false == StringUtils.hasText(request.getParameter(StringConstants.SELECTED_EMAIL))) {
            emailAware.setEmail(new Email(this.getPerson(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }

    protected void setPaymentSourceToNone(HttpServletRequest request, PaymentSourceAware paymentSourceAware) {
        if (false == StringUtils.hasText(request.getParameter(StringConstants.SELECTED_PAYMENT_SOURCE))) {
            paymentSourceAware.setPaymentSource(new PaymentSource(this.getPerson(request))); // this is equivalent to setting it to the dummy (empty) value
        }
    }

    protected abstract Viewable findViewable(HttpServletRequest request);

    @SuppressWarnings("unchecked")
    protected abstract void addRefData(HttpServletRequest request, Long personId, Map refData);
}
