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

public class GiftAdjustmentController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    private String adjustedGiftPostedUrl;

    public void setAdjustedGiftPostedUrl(String adjustedGiftPostedUrl) {
        this.adjustedGiftPostedUrl = adjustedGiftPostedUrl;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return adjustedGiftService.readAdjustedGiftByIdCreateIfNull(getConstituent(request),
                request.getParameter(StringConstants.ADJUSTED_GIFT_ID), request.getParameter(StringConstants.GIFT_ID));
    }

    private boolean isPosted(AdjustedGift adjustedGift) {
        return adjustedGift != null && ! adjustedGift.isNew() && adjustedGift.isPosted();
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mav = super.showForm(request, response, errors);
        TangerineForm form = (TangerineForm) formBackingObject(request);
        AdjustedGift adjustedGift = (AdjustedGift) form.getDomainObject();

        if (isPosted(adjustedGift)) {
            mav = new ModelAndView(getRedirectUrl(request, adjustedGiftPostedUrl,
                    new Long(request.getParameter(StringConstants.ADJUSTED_GIFT_ID))));
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
            anAdjustedGift = adjustedGiftService.maintainAdjustedGift(anAdjustedGift);
            String view = isPosted(anAdjustedGift) ? adjustedGiftPostedUrl : getSuccessView();
            mav = new ModelAndView(appendSaved(getRedirectUrl(request, view, anAdjustedGift.getId())));
	    }
	    catch (BindException domainErrors) {
		    bindDomainErrorsToForm(request, formErrors, domainErrors, form, anAdjustedGift);
            mav = showForm(request, formErrors, getFormView());
	    }
	    return mav;
    }

    private String getRedirectUrl(HttpServletRequest request, String view, Long adjustedGiftId) {
        return new StringBuilder(view).append("?").append(StringConstants.ADJUSTED_GIFT_ID).append("=").append(adjustedGiftId).
                append("&").append(StringConstants.CONSTITUENT_ID).append("=").append(super.getConstituentId(request)).toString();
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