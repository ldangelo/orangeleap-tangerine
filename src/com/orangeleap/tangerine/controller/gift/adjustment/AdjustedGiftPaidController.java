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

package com.orangeleap.tangerine.controller.gift.adjustment;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.controller.gift.GiftControllerHelper;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdjustedGiftPaidController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    protected GiftControllerHelper giftControllerHelper;

    public void setGiftControllerHelper(GiftControllerHelper giftControllerHelper) {
        this.giftControllerHelper = giftControllerHelper;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        AdjustedGift adjustedGift = adjustedGiftService.readAdjustedGiftByIdCreateIfNull(getConstituent(request),
                request.getParameter(StringConstants.ADJUSTED_GIFT_ID), request.getParameter(StringConstants.GIFT_ID));
        if ( ! adjustedGift.isNew()) {
            request.setAttribute(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(adjustedGift));
        }
        return adjustedGift;
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mav = super.showForm(request, response, errors);
        TangerineForm form = (TangerineForm) formBackingObject(request);
        AdjustedGift adjustedGift = (AdjustedGift) form.getDomainObject();

        if (giftControllerHelper.showAdjustedGiftPostedView(adjustedGift)) {
            String redirectUrl = giftControllerHelper.appendAdjustedGiftParameters(giftControllerHelper.getAdjustedGiftPostedUrl(), adjustedGift, getConstituentId(request));
            if (Boolean.TRUE.toString().equalsIgnoreCase(request.getParameter(StringConstants.SAVED))) {
                redirectUrl = appendSaved(redirectUrl);
            }
            mav = new ModelAndView(redirectUrl);
        }
        return mav;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    AdjustedGift anAdjustedGift = (AdjustedGift) form.getDomainObject();
        validateAdjustedGiftStatusChange(anAdjustedGift);

        ModelAndView mav;
	    try {
            if (anAdjustedGift.isNew()) {
                anAdjustedGift = adjustedGiftService.maintainAdjustedGift(anAdjustedGift);
            }
            else {
                anAdjustedGift = adjustedGiftService.editAdjustedGift(anAdjustedGift, true);
            }
            if (giftControllerHelper.showAdjustedGiftPostedView(anAdjustedGift)) {
                String redirectUrl = giftControllerHelper.appendAdjustedGiftParameters(giftControllerHelper.getAdjustedGiftPostedUrl(), anAdjustedGift, getConstituentId(request));
                mav = new ModelAndView(super.appendSaved(redirectUrl));
            }
            else {
                mav = new ModelAndView(super.appendSaved(giftControllerHelper.appendAdjustedGiftParameters(getSuccessView(), anAdjustedGift, getConstituentId(request))));
            }
	    }
	    catch (BindException domainErrors) {
		    bindDomainErrorsToForm(request, formErrors, domainErrors, form, anAdjustedGift);
            mav = showForm(request, formErrors, getFormView());
	    }
	    return mav;
    }

    public void validateAdjustedGiftStatusChange(AdjustedGift adjustedGift) {
        if (adjustedGift != null && !adjustedGift.isNew()) {
            AdjustedGift oldAdjustedGift = adjustedGiftService.readAdjustedGiftById(adjustedGift.getId());
            if (oldAdjustedGift != null) {
                if (Gift.STATUS_PAID.equals(oldAdjustedGift.getAdjustedStatus()) && !Gift.STATUS_PAID.equals(adjustedGift.getAdjustedStatus())) {
                    // Can't change from Paid to non-Paid in view
                    adjustedGift.setAdjustedStatus(oldAdjustedGift.getAdjustedStatus());
                }
            }
        }
    }
}