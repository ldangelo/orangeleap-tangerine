package com.mpower.controller.payment;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;

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
import com.mpower.util.StringConstants;

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

        String personId = request.getParameter(StringConstants.PERSON_ID);
        binder.registerCustomEditor(Address.class, new AddressEditor(addressService, personService, personId));
        binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService, personService, personId));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(personId);
        refData.put("paymentSources", paymentSources);
        List<Address> addresses = addressService.readAddresses(Long.valueOf(personId));
        refData.put("addresses", addresses);
        List<Phone> phones = phoneService.readPhones(Long.valueOf(personId));
        refData.put("phones", phones);
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        String paymentSourceId = request.getParameter("paymentSourceId");
        PaymentSource paymentSource = null;
        if (paymentSourceId == null) {
            paymentSource = new PaymentSource(super.getPerson(request));
        }
        else {
            paymentSource = paymentSourceService.readPaymentSource(Long.valueOf(paymentSourceId));
        }
        return paymentSource;
    }

    @Override
    protected void save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        paymentSourceService.maintainPaymentSource((PaymentSource)command);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        PaymentSource paymentSource = (PaymentSource)super.formBackingObject(request);

        if (isFormSubmission(request)) {
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
}
