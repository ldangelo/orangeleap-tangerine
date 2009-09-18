package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class GiftViewController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="giftService")
    protected GiftService giftService;

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

	@Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Gift gift = giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));
        if (gift != null) {
            gift.setAdjustedGifts(adjustedGiftService.readAdjustedGiftsForOriginalGiftId(gift.getId()));
        }
        return gift;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refMap = super.referenceData(request, command, errors);
	    TangerineForm form = (TangerineForm) command;
		Gift gift = (Gift) form.getDomainObject();
        refMap.put(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(gift));
        return refMap;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    Gift gift = (Gift) form.getDomainObject();
        validateGiftViewStatusChange(gift);

        ModelAndView mav;
	    try {
            gift = giftService.editGift(gift);
            mav = new ModelAndView(appendSaved(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + gift.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
	    }
	    catch (BindException domainErrors) {
		    bindDomainErrorsToForm(request, formErrors, domainErrors, form, gift);
            mav = showForm(request, formErrors, getFormView());
	    }
        return mav;
    }
    
	private void validateGiftViewStatusChange(Gift gift) {
		if (gift == null || gift.isNew() || gift.getId() == null) return;
		Gift oldgift = giftService.readGiftById(gift.getId());
		if (oldgift == null) return;
		if (Gift.STATUS_PAID.equals(oldgift.getGiftStatus()) && !Gift.STATUS_PAID.equals(gift.getGiftStatus())) {
			// Can't change from Paid to non-Paid in view
			gift.setGiftStatus(oldgift.getGiftStatus());
		}
	}

}