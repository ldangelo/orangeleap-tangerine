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

    /**
     * If a new address/phone is to be created, this must be done prior to binding so that fields will be bound to the new object, not the old one
     */
    protected void createNew(HttpServletRequest request, PaymentSource paymentSource) {
        if (StringConstants.NEW.equals(request.getParameter("selectedPhone"))) {
            paymentSource.userCreateNewPhone();
        }
        if (StringConstants.NEW.equals(request.getParameter("selectedAddress"))) {
            paymentSource.userCreateNewAddress();
        }
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);

        if (!errors.hasErrors()) {
            if (isFormSubmission(request)) {
                createNone(request, command, errors);
            }
        }
    }

    /**
     * If NO address/phone is associated with this paymentSource, this must be done AFTER binding and validation
     */
    protected void createNone(HttpServletRequest request, Object command, BindException errors) {
        if (StringConstants.EMPTY.equals(request.getParameter("selectedPhone"))) {
            ((PaymentSource)command).createNewPhone(); // this is equivalent to setting it to the dummy (empty) phone
        }
        if (StringConstants.EMPTY.equals(request.getParameter("selectedAddress"))) {
            ((PaymentSource)command).createNewAddress(); // this is equivalent to setting it to the dummy (empty) address
        }
    }
}
