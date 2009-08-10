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
import org.springframework.validation.Errors;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class GiftAdjustmentViewController extends TangerineConstituentAttributesFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
	    TangerineForm form = (TangerineForm) command;
	    AdjustedGift anAdjustedGift = (AdjustedGift) form.getDomainObject();
        refData.put(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(anAdjustedGift));
        return refData;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String adjustedGiftIdStr = request.getParameter(StringConstants.ADJUSTED_GIFT_ID);
        if (!NumberUtils.isDigits(adjustedGiftIdStr)) {
            throw new IllegalArgumentException("The adjustedGiftId is invalid");
        }
        AdjustedGift adjustedGift = adjustedGiftService.readAdjustedGiftById(Long.parseLong(adjustedGiftIdStr));
        request.setAttribute(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(adjustedGift));
        return adjustedGift;
    }

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException formErrors) throws Exception {
		TangerineForm form = (TangerineForm) command;
		AdjustedGift anAdjustedGift = (AdjustedGift) form.getDomainObject();

        ModelAndView mav;
		try {
	        anAdjustedGift = adjustedGiftService.editAdjustedGift(anAdjustedGift);
            mav = new ModelAndView(appendSaved(getSuccessView() + "?" + StringConstants.ADJUSTED_GIFT_ID + "=" + anAdjustedGift.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request)));
		}
		catch (BindException domainErrors) {
			bindDomainErrorsToForm(request, formErrors, domainErrors, form, anAdjustedGift);
            mav = showForm(request, formErrors, getFormView());
		}
	    return mav;
	}
}