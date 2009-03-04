package com.mpower.controller.membership;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.controller.NoneStringTrimmerEditor;
import com.mpower.controller.payment.PaymentSourceEditor;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.CommitmentService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.ConstituentService;
import com.mpower.service.SiteService;
import com.mpower.type.CommitmentType;
import com.mpower.type.PageType;
import com.mpower.util.TangerineUserHelper;

public class MembershipFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
    @Resource(name="commitmentService")
    private CommitmentService commitmentService;

    @Resource(name="paymentSourceService")
    private PaymentSourceService paymentSourceService;

    @Resource(name="constituentService")
    private ConstituentService personService;

    @Resource(name="siteService")
    private SiteService siteService;

    private PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
        binder.registerCustomEditor(String.class, new NoneStringTrimmerEditor(true));
        binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(request.getParameter("personId")));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = new HashMap();
        String personIdString = request.getParameter("personId");
        Long personId = null;
        if (personIdString == null) {
            String commitmentId = request.getParameter("commitmentId");
            if (commitmentId != null) {
                Commitment commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
                if (commitment != null) {
                    personId = commitment.getPerson().getId();
                    List<Gift> gifts = commitmentService.getCommitmentGifts(commitment);
                    refData.put("gifts",gifts);
                    Iterator<Gift> giftIter = gifts.iterator();
                    BigDecimal giftSum = new BigDecimal(0);
                    while (giftIter.hasNext()) {
                        giftSum = giftSum.add(giftIter.next().getAmount());
                    }
                    refData.put("giftSum",giftSum);
                }

            }
        } else {
            personId = Long.valueOf(personIdString);
        }
        if (personId != null) {
            List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(personId);
            refData.put("paymentSources", paymentSources);
        }
        return refData;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String commitmentId = request.getParameter("commitmentId");
        Commitment commitment = null;
        if (commitmentId == null) {
            // create person
            String personId = request.getParameter("personId");
            // TODO: if the user navigates directly to gift.htm with no personId, we should redirect to giftSearch.htm
            Person person = null;
            if (personId != null) {
                person = personService.readConstituentById(Long.valueOf(personId));
                if (person == null) {
                    logger.error("**** person not found for id: " + personId);
                    return commitment;
                }
                commitment = commitmentService.createDefaultCommitment(person, CommitmentType.MEMBERSHIP);
                commitment.setPerson(person);
            }
        } else {
            commitment = commitmentService.readCommitmentById(new Long(commitmentId));
        }
        if (isFormSubmission(request)) {
            Map<String, String> fieldLabelMap = siteService.readFieldLabels(pageType, tangerineUserHelper.lookupUserRoles(), request.getLocale());
            commitment.setFieldLabelMap(fieldLabelMap);

            Map<String, Object> valueMap = siteService.readFieldValues(pageType, tangerineUserHelper.lookupUserRoles(), commitment);
            commitment.setFieldValueMap(valueMap);
        }
        return commitment;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        Commitment commitment = (Commitment) command;

        // validate required fields
        Commitment current = commitmentService.maintainCommitment(commitment);

        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView("redirect:/membershipView.htm", errors.getModel());
        mav.addObject("commitmentId", current.getId());
        mav.addObject("saved", true);
        mav.addObject("id", current.getId());
        mav.addObject("myErrors",errors);
        return mav;
    }
}
