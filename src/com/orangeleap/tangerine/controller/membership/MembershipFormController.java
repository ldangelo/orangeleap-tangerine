package com.orangeleap.tangerine.controller.membership;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.orangeleap.tangerine.controller.NoneStringTrimmerEditor;
import com.orangeleap.tangerine.controller.payment.PaymentSourceEditor;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class MembershipFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
//    @Resource(name="commitmentService")
//    private CommitmentService commitmentService;

    @Resource(name="paymentSourceService")
    private PaymentSourceService paymentSourceService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

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
        binder.registerCustomEditor(PaymentSource.class, new PaymentSourceEditor(paymentSourceService, request.getParameter("constituentId")));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = new HashMap();
//        String constituentIdString = request.getParameter("constituentId");
//        Long constituentId = null;
//        if (constituentIdString == null) {
//            String commitmentId = request.getParameter("commitmentId");
//            if (commitmentId != null) {
//                Commitment commitment = commitmentService.readCommitmentById(Long.valueOf(commitmentId));
//                if (commitment != null) {
//                    constituentId = commitment.getConstituent().getId();
//                    List<Gift> gifts = commitmentService.getCommitmentGifts(commitment);
//                    refData.put("gifts",gifts);
//                    Iterator<Gift> giftIter = gifts.iterator();
//                    BigDecimal giftSum = new BigDecimal(0);
//                    while (giftIter.hasNext()) {
//                        giftSum = giftSum.add(giftIter.next().getAmount());
//                    }
//                    refData.put("giftSum",giftSum);
//                }
//
//            }
//        } else {
//            constituentId = Long.valueOf(constituentIdString);
//        }
//        if (constituentId != null) {
//            List<PaymentSource> paymentSources = paymentSourceService.readPaymentSources(constituentId);
//            refData.put("paymentSources", paymentSources);
//        }
        return refData;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        String commitmentId = request.getParameter("commitmentId");
//        Commitment commitment = null;
//        if (commitmentId == null) {
//            // create constituent
//            String constituentId = request.getParameter("constituentId");
//            // TODO: if the user navigates directly to gift.htm with no constituentId, we should redirect to giftSearch.htm
//            Constituent constituent = null;
//            if (constituentId != null) {
//                constituent = constituentService.readConstituentById(Long.valueOf(constituentId));
//                if (constituent == null) {
//                    logger.error("**** constituent not found for id: " + constituentId);
//                    return commitment;
//                }
////                commitment = commitmentService.createDefaultCommitment(constituent, CommitmentType.MEMBERSHIP);
////                commitment.setConstituent(constituent);
//            }
//        } else {
//            commitment = commitmentService.readCommitmentById(new Long(commitmentId));
//        }
//        if (isFormSubmission(request)) {
//            Map<String, String> fieldLabelMap = siteService.readFieldLabels(pageType, tangerineUserHelper.lookupUserRoles(), request.getLocale());
//            commitment.setFieldLabelMap(fieldLabelMap);
//
//            Map<String, Object> valueMap = siteService.readFieldValues(pageType, tangerineUserHelper.lookupUserRoles(), commitment);
//            commitment.setFieldValueMap(valueMap);
//        }
        return null;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
//        Commitment commitment = (Commitment) command;

        // validate required fields
//        Commitment current = commitmentService.maintainCommitment(commitment);

        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView("redirect:/membershipView.htm", errors.getModel());
//        mav.addObject("commitmentId", current.getId());
//        mav.addObject("saved", true);
//        mav.addObject("id", current.getId());
//        mav.addObject("myErrors",errors);
        return mav;
    }
}
