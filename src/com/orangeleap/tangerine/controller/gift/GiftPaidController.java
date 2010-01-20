package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class GiftPaidController extends AbstractMutableGridFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="giftService")
    protected GiftService giftService;

	@Resource(name = "pledgeService")
	protected PledgeService pledgeService;

	@Resource(name = "recurringGiftService")
	protected RecurringGiftService recurringGiftService;
	
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    protected GiftControllerHelper giftControllerHelper;

    public void setGiftControllerHelper(GiftControllerHelper giftControllerHelper) {
        this.giftControllerHelper = giftControllerHelper;
    }

	@Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Gift gift = giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));
        if (gift != null) {
            gift.setAdjustedGifts(adjustedGiftService.readAdjustedGiftsForOriginalGiftId(gift.getId()));
            clearAddressFields(gift);
            clearPhoneFields(gift);
        }
        return gift;
    }

	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
	    ModelAndView mav = super.showForm(request, response, errors);
	    TangerineForm form = (TangerineForm) formBackingObject(request);
	    Gift gift = (Gift) form.getDomainObject();

	    if (giftControllerHelper.showGiftPostedView(gift)) {
	        String redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPostedUrl(), gift, getConstituentId(request));
	        if (Boolean.TRUE.toString().equalsIgnoreCase(request.getParameter(StringConstants.SAVED))) {
	            redirectUrl = appendSaved(redirectUrl);
	        }
	        mav = new ModelAndView(redirectUrl);
	    }
	    return mav;
	}

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refMap = super.referenceData(request, command, errors);

	    String selectedPledgeId = request.getParameter("selectedPledgeId");
	    String selectedRecurringGiftId = request.getParameter("selectedRecurringGiftId");
	    if (NumberUtils.isDigits(selectedPledgeId)) {
	        Pledge pledge = pledgeService.readPledgeById(Long.parseLong(selectedPledgeId));
	        refMap.put("associatedPledge", pledge);
	    }
	    else if (NumberUtils.isDigits(selectedRecurringGiftId)) {
	        RecurringGift recurringGift = recurringGiftService.readRecurringGiftById(Long.parseLong(selectedRecurringGiftId));
	        refMap.put("associatedRecurringGift", recurringGift);
	    }

	    TangerineForm form = (TangerineForm) command;
		Gift gift = (Gift) form.getDomainObject();
        refMap.put(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(gift));
        return refMap;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    Gift gift = (Gift) form.getDomainObject();
	    giftService.checkAssociatedPledgeIds(gift);
	    giftService.checkAssociatedRecurringGiftIds(gift);
        giftControllerHelper.validateGiftStatusChange(gift);

        ModelAndView mav;
	    try {
            gift = giftService.editGift(gift, true);
            if (giftControllerHelper.showGiftPostedView(gift)) {
                String redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPostedUrl(), gift, getConstituentId(request));
                mav = new ModelAndView(super.appendSaved(redirectUrl));
            }
            else {
                mav = new ModelAndView(super.appendSaved(giftControllerHelper.appendGiftParameters(getSuccessView(), gift, getConstituentId(request))));
            }
	    }
	    catch (BindException domainErrors) {
		    bindDomainErrorsToForm(request, formErrors, domainErrors, form, gift);
            mav = showForm(request, formErrors, getFormView());
	    }
        return mav;
    }
}