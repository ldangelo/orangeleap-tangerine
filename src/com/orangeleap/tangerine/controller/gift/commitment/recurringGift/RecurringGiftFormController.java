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

package com.orangeleap.tangerine.controller.gift.commitment.recurringGift;

import com.orangeleap.tangerine.controller.gift.commitment.CommitmentFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RecurringGiftFormController extends CommitmentFormController<RecurringGift> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="recurringGiftService")
    protected RecurringGiftService recurringGiftService;

	@Resource(name = "picklistItemService")
	protected PicklistItemService picklistItemService;
    
    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        RecurringGift recurringGift = recurringGiftService.readRecurringGiftByIdCreateIfNull(request.getParameter(StringConstants.RECURRING_GIFT_ID), super.getConstituent(request));
        
        if (!StringUtils.hasText(recurringGift.getCurrencyCode())) {
        	Picklist ccPicklist = picklistItemService.getPicklist("currencyCode");
        	if (ccPicklist != null && !ccPicklist.getActivePicklistItems().isEmpty()) {
        		recurringGift.setCurrencyCode(ccPicklist.getActivePicklistItems().get(0).getItemName());
        	}
        }
        return recurringGift;
    }

    @Override
    protected RecurringGift maintainCommitment(RecurringGift entity) throws BindException {
        return recurringGiftService.maintainRecurringGift(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        refData.put(StringConstants.CAN_APPLY_PAYMENT, recurringGiftService.canApplyPayment((RecurringGift)command));
        return refData;
    }

    @Override
    protected String getParamId() {
        return StringConstants.RECURRING_GIFT_ID;
    }
}
