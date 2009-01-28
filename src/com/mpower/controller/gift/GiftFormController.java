package com.mpower.controller.gift;

import java.util.Iterator;
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
import com.mpower.controller.email.EmailEditor;
import com.mpower.controller.payment.PaymentSourceEditor;
import com.mpower.controller.phone.PhoneEditor;
import com.mpower.domain.Address;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Email;
import com.mpower.domain.Gift;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.service.AddressService;
import com.mpower.service.EmailService;
import com.mpower.service.GiftService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;
import com.mpower.util.StringConstants;

public class GiftFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected GiftService giftService;
    protected PaymentSourceService paymentSourceService;
    protected AddressService addressService;
    protected PhoneService phoneService;
    protected EmailService emailService;

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(paymentSourceService, personService, super.getPersonIdString(request)));
        binder.registerCustomEditor(Address.class, new AddressEditor(addressService, personService, super.getPersonIdString(request)));
        binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService, personService, super.getPersonIdString(request)));
        binder.registerCustomEditor(Email.class, new EmailEditor(emailService, personService, super.getPersonIdString(request)));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        List<PaymentSource> paymentSources = paymentSourceService.readActivePaymentSourcesACHCreditCard(personId);
        refData.put(StringConstants.PAYMENT_SOURCES, paymentSources);
        List<Address> addresses = addressService.filterValidAddresses(personId);
        refData.put(StringConstants.ADDRESSES, addresses);
        List<Phone> phones = phoneService.filterValidPhones(personId);
        refData.put(StringConstants.PHONES, phones);
        List<Email> emails = emailService.filterValidEmails(personId);
        refData.put(StringConstants.EMAILS, emails);
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
        return giftService.readGiftByIdCreateIfNull(request.getParameter(StringConstants.GIFT_ID), request.getParameter(StringConstants.COMMITMENT_ID), super.getPerson(request));
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Gift gift = (Gift) command;

        // validate required fields

        // TODO: This code is temporary validation to strip out invalid distribution lines.
        Iterator<DistributionLine> distLineIter = gift.getDistributionLines().iterator();
        while (distLineIter.hasNext()) {
            DistributionLine line = distLineIter.next();
            if (line == null || line.getAmount() == null) {
                distLineIter.remove();
            }
        }

        Gift current = giftService.maintainGift(gift);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getPersonId(request));
    }
}
