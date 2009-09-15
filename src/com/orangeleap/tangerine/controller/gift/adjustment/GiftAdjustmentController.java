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
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.math.NumberUtils;
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

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        if (NumberUtils.isNumber(request.getParameter(StringConstants.ADJUSTED_GIFT_ID))) {
            return new AdjustedGift();
        }
        return adjustedGiftService.createdDefaultAdjustedGift(super.getIdAsLong(request, StringConstants.GIFT_ID));
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
        ModelAndView mav = super.showForm(request, response, errors);
        if (NumberUtils.isNumber(request.getParameter(StringConstants.ADJUSTED_GIFT_ID))) {
            mav = new ModelAndView(getRedirectUrl(request, new Long(request.getParameter(StringConstants.ADJUSTED_GIFT_ID))));
        }
        return mav;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
	    TangerineForm form = (TangerineForm) command;
	    AdjustedGift anAdjustedGift = (AdjustedGift) form.getDomainObject();

	    boolean saved = true;
	    try {
            anAdjustedGift = adjustedGiftService.maintainAdjustedGift(anAdjustedGift);
	    }
	    catch (BindException domainErrors) {
		    saved = false;
		    bindDomainErrorsToForm(request, formErrors, domainErrors, form, anAdjustedGift);
	    }
	    ModelAndView mav;
	    if (saved) {
	        mav = new ModelAndView(appendSaved(getRedirectUrl(request, anAdjustedGift.getId())));
	    }
	    else {
			mav = showForm(request, formErrors, getFormView());
	    }
	    return mav;
    }

    private String getRedirectUrl(HttpServletRequest request, Long adjustedGiftId) {
        return getSuccessView() + "?" + StringConstants.ADJUSTED_GIFT_ID + "=" + adjustedGiftId + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request);
    }
}