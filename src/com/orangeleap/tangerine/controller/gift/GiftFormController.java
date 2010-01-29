/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.type.PaymentType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

public class GiftFormController extends AbstractMutableGridFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    protected GiftControllerHelper giftControllerHelper;

    public void setGiftControllerHelper(GiftControllerHelper giftControllerHelper) {
        this.giftControllerHelper = giftControllerHelper;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Gift gift = giftService.readGiftByIdCreateIfNull(getConstituent(request), request.getParameter(StringConstants.GIFT_ID));
        if ( ! gift.isNew()) {
            gift.setAdjustedGifts(adjustedGiftService.readAdjustedGiftsForOriginalGiftId(gift.getId()));
        }
        clearPaymentSourceFields(gift);
        clearAddressFields(gift);
        clearPhoneFields(gift);
	    return gift;
    }

    private boolean canReprocessGift(Gift gift) {
    	return giftControllerHelper.isEnteredGift(gift) && (Gift.PAY_STATUS_DECLINED.equals(gift.getPaymentStatus()) || Gift.PAY_STATUS_ERROR.equals(gift.getPaymentStatus()));
    }

	@Override
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		ModelAndView mav = super.showForm(request, response, errors);
        TangerineForm form = (TangerineForm) formBackingObject(request);
		Gift gift = (Gift) form.getDomainObject();

        String redirectUrl = null;
        if (giftControllerHelper.showGiftPaidView(gift)) {
            redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPaidUrl(), gift, getConstituentId(request));
        }
        else if (giftControllerHelper.showGiftPostedView(gift)) {
            redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPostedUrl(), gift, getConstituentId(request));
        }
        if (redirectUrl != null) {
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

        giftControllerHelper.addSelectedPledgeRecurringGiftRefData(request, refMap);
	    
        TangerineForm form = (TangerineForm) command;
	    Gift gift = (Gift) form.getDomainObject();
        if (canReprocessGift(gift)) {
        	refMap.put("allowReprocess", Boolean.TRUE);
        }
        if ( ! gift.isNew()) {
            boolean allowAdjustment = Gift.STATUS_PAID.equals(gift.getGiftStatus()) && ! adjustedGiftService.isAdjustedAmountEqualGiftAmount(gift) &&
                    (PaymentType.ACH.getPaymentName().equals(gift.getPaymentType()) || PaymentType.CREDIT_CARD.getPaymentName().equals(gift.getPaymentType())) &&
                            ! StringUtils.hasText(gift.getPaymentStatus());
            refMap.put(StringConstants.SHOW_ADJUST_GIFT_BUTTON, allowAdjustment);
        }
        return refMap;
    }

	@Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
		giftControllerHelper.initBinderAssociations(binder);
    }

	@Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
		TangerineForm form = (TangerineForm) command;
		Gift gift = (Gift) form.getDomainObject();
        giftControllerHelper.checkAssociations(gift);
        giftControllerHelper.validateGiftStatusChange(gift);

        boolean saved = true;
        try {
        	if ("true".equals(request.getParameter("doReprocess")) && canReprocessGift(gift)) {
        		gift = giftService.reprocessGift(gift);
        	}
        	else if (gift.isNew()) {
        		gift = giftService.maintainGift(gift);
        	}
            else {
                gift = giftService.editGift(gift, true);
            }
        }
        catch (BindException domainErrors) {
            saved = false;
	        bindDomainErrorsToForm(request, formErrors, domainErrors, form, gift);
        }

        ModelAndView mav;
        if (saved) {
            if (giftControllerHelper.showGiftPaidView(gift)) {
                String redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPaidUrl(), gift, getConstituentId(request));
                mav = new ModelAndView(redirectUrl);
            }
            else if (giftControllerHelper.showGiftPostedView(gift)) {
                String redirectUrl = giftControllerHelper.appendGiftParameters(giftControllerHelper.getGiftPostedUrl(), gift, getConstituentId(request));
                mav = new ModelAndView(redirectUrl);
            }
            else {
                mav = new ModelAndView(super.appendSaved(giftControllerHelper.appendGiftParameters(getSuccessView(), gift, getConstituentId(request))));
            }
        }
        else {
			giftControllerHelper.checkAssociations(gift);
			mav = showForm(request, formErrors, getFormView());
        }
        return mav;
    }
}