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

package com.orangeleap.tangerine.web.customization.handler;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Locale;

public class SelectionFieldHandler extends GenericFieldHandler {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private PledgeService pledgeService;
    private GiftService giftService;
    private RecurringGiftService recurringGiftService;

    public SelectionFieldHandler(ApplicationContext appContext) {
        super(appContext);
        pledgeService = (PledgeService) appContext.getBean("pledgeService");
        giftService = (GiftService) appContext.getBean("giftService");
        recurringGiftService = (RecurringGiftService) appContext.getBean("recurringGiftService");
    }

    @SuppressWarnings("unchecked")
    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        if (propertyValue != null) {
            fieldVO.setReferenceType(currentField.getFieldDefinition().getReferenceType());
            if (ReferenceType.adjustedGift.equals(fieldVO.getReferenceType())) {
                List<AdjustedGift> adjustedGifts = (List<AdjustedGift>) propertyValue;
                for (AdjustedGift aAdjGift : adjustedGifts) {
                    fieldVO.addDisplayValue(aAdjGift.getShortDescription());
                    fieldVO.addId(aAdjGift.getId());
                }
            } else {
                List<Long> ids = (List<Long>) propertyValue;
                for (Long id : ids) {
                    if (ReferenceType.pledge.equals(fieldVO.getReferenceType())) {
                        Pledge pledge = pledgeService.readPledgeById(id);
                        fieldVO.addDisplayValue(pledge.getShortDescription());
                    } else if (ReferenceType.recurringGift.equals(fieldVO.getReferenceType())) {
                        RecurringGift recurringGift = recurringGiftService.readRecurringGiftById(id);
                        fieldVO.addDisplayValue(recurringGift.getShortDescription());
                    } else if (ReferenceType.gift.equals(fieldVO.getReferenceType())) {
                        Gift gift = giftService.readGiftById(id);
                        fieldVO.addDisplayValue(gift.getShortDescription());
                    }
                    fieldVO.addId(id);
                }
            }
        }
        return fieldVO;
    }
}
