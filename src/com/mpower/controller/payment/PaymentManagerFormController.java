package com.mpower.controller.payment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineFormController;
import com.mpower.controller.address.AddressEditor;
import com.mpower.controller.phone.PhoneEditor;
import com.mpower.domain.Address;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.service.AddressService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;

public class PaymentManagerFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;
    private AddressService addressService;
    private PhoneService phoneService;

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);

        binder.registerCustomEditor(Address.class, new AddressEditor(addressService, personService, super.getPersonIdString(request)));
        binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService, personService, super.getPersonIdString(request)));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        List<PaymentSource> paymentSources = paymentSourceService.readActivePaymentSourcesACHCreditCard(personId);
        refData.put("paymentSources", paymentSources);
        List<Address> addresses = addressService.filterValidAddresses(Long.valueOf(personId));
        refData.put("addresses", addresses);
        List<Phone> phones = phoneService.filterValidPhones(Long.valueOf(personId));
        refData.put("phones", phones);
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        boolean isGet = !super.isFormSubmission(request);
        return paymentSourceService.readPaymentSourceCreateIfNull(request.getParameter("paymentSourceId"), super.getPerson(request), isGet);
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        paymentSourceService.maintainPaymentSource((PaymentSource)command);
        return super.onSubmit(request, response, command, errors);
    }
}
