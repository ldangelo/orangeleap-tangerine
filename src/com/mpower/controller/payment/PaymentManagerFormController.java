package com.mpower.controller.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.controller.address.AddressEditor;
import com.mpower.controller.phone.PhoneEditor;
import com.mpower.domain.Address;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.service.AddressService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;
import com.mpower.service.PhoneService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;
import com.mpower.util.StringConstants;

public class PaymentManagerFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;
    private PersonService personService;
    private SiteService siteService;
    private AddressService addressService;
    private PhoneService phoneService;

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Address.class, new AddressEditor(addressService, personService, request.getParameter("personId")));
        binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService, personService, request.getParameter("personId")));
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String personId = request.getParameter("personId");
        String paymentSourceId = request.getParameter("paymentSourceId");
        PaymentSource paymentSource = null;
        if (paymentSourceId == null) {
            Person person = personService.readPersonById(Long.valueOf(personId));
            paymentSource = new PaymentSource();
            paymentSource.setPerson(person);
        }
        else {
            paymentSource = paymentSourceService.readPaymentSource(Long.valueOf(paymentSourceId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.paymentManager, SessionServiceImpl.lookupUserRoles(), request.getLocale());
            paymentSource.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.paymentManager, SessionServiceImpl.lookupUserRoles(), paymentSource);
            paymentSource.setFieldValueMap(valueMap);

            createNew(request, paymentSource);
        }
        return paymentSource;
    }

    protected void createNew(HttpServletRequest request, PaymentSource paymentSource) {
        if (StringConstants.NEW.equals(request.getParameter("selectedPhone"))) {
            paymentSource.createNewPhone();
        }
        if (StringConstants.NEW.equals(request.getParameter("selectedAddress"))) {
            paymentSource.createNewAddress();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personId = request.getParameter("personId");
        List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(Long.valueOf(personId));
        refData.put("paymentSources", paymentSources);
        List<Address> addresses = addressService.readAddresses(Long.valueOf(personId));
        refData.put("addresses", addresses);
        List<Phone> phones = phoneService.readPhones(Long.valueOf(personId));
        refData.put("phones", phones);
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        PaymentSource paymentSource = (PaymentSource) command;
        paymentSourceService.maintainPaymentSource(paymentSource);

        return new ModelAndView(super.getSuccessView() + "?personId=" + request.getParameter("personId"));
    }
}
