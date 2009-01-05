package com.mpower.controller.gift;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.controller.address.AddressEditor;
import com.mpower.controller.email.EmailEditor;
import com.mpower.controller.payment.PaymentSourceEditor;
import com.mpower.controller.phone.PhoneEditor;
import com.mpower.domain.Address;
import com.mpower.domain.Commitment;
import com.mpower.domain.DistributionLine;
import com.mpower.domain.Email;
import com.mpower.domain.Gift;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.service.AddressService;
import com.mpower.service.CommitmentService;
import com.mpower.service.EmailService;
import com.mpower.service.GiftService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PersonService;
import com.mpower.service.PhoneService;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.GiftEntryType;
import com.mpower.type.PageType;

public class GiftFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CommitmentService commitmentService;

    private GiftService giftService;

    private PaymentSourceService paymentSourceService;

    private PersonService personService;

    private SiteService siteService;

    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    public void setSiteService(SiteService siteService) {
        this.siteService = siteService;
    }

    private AddressService addressService;

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    private PhoneService phoneService;

    public void setPhoneService(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    private EmailService emailService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(paymentSourceService));
        binder.registerCustomEditor(Address.class, new AddressEditor(addressService));
        binder.registerCustomEditor(Phone.class, new PhoneEditor(phoneService));
        binder.registerCustomEditor(Email.class, new EmailEditor(emailService));
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
        List<Email> emails = emailService.readEmails(Long.valueOf(personId));
        refData.put("emails", emails);
        return refData;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String giftId = request.getParameter("giftId");
        Gift gift = null;
        if (giftId == null) {
            String commitmentId = request.getParameter("commitmentId");
            Commitment commitment = null;
            if (commitmentId != null) {
                commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
                if (commitment == null) {
                    logger.error("**** commitment not found for id: " + commitmentId);
                    return gift;
                }
                gift = giftService.createGift(commitment, GiftEntryType.MANUAL);
                // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
                gift.setPerson(commitment.getPerson());
            }
            if (gift == null) {
                String personId = request.getParameter("personId");
                Person person = null;
                if (personId != null) {
                    person = personService.readPersonById(Long.valueOf(personId));
                    if (person == null) {
                        logger.error("**** person not found for id: " + personId);
                        return gift;
                    }
                    gift = giftService.createDefaultGift(person);
                    // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
                    gift.setPerson(person);
                }
            }
        } else {
            gift = giftService.readGiftById(new Long(giftId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), request.getLocale());
            gift.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(getCommandName()), SessionServiceImpl.lookupUserRoles(), gift);
            gift.setFieldValueMap(valueMap);
        }
        return gift;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
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

        ModelAndView mav = new ModelAndView("redirect:/giftView.htm");
        mav.addObject("giftId", current.getId());
        return mav;
    }
}
