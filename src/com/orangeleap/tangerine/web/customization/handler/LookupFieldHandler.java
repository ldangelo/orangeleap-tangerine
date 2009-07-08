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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Locale;

public class LookupFieldHandler extends GenericFieldHandler {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    protected PledgeService pledgeService;
    protected RecurringGiftService recurringGiftService;
    protected GiftService giftService;

    public LookupFieldHandler(ApplicationContext appContext) {
        super(appContext);
        pledgeService = (PledgeService) appContext.getBean("pledgeService");
        recurringGiftService = (RecurringGiftService) appContext.getBean("recurringGiftService");
        giftService = (GiftService) appContext.getBean("giftService");
    }

    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        boolean isCustom = currentField.getFieldDefinition().isCustom();
        Object propertyValue = super.getPropertyValue(model, fieldVO);

        if (propertyValue != null && isCustom && propertyValue instanceof String) {
            ReferenceType referenceType = currentField.getFieldDefinition().getReferenceType();

            String[] ids = ((String) propertyValue).split(StringConstants.CUSTOM_FIELD_SEPARATOR); // TODO: safe to do?
            StringBuffer sb = new StringBuffer();
            for (String id : ids) {
                if (id.length() > 0) {
                    if (sb.length() > 0) {
                        sb.append(StringConstants.CUSTOM_FIELD_SEPARATOR);
                    }
                    if (NumberUtils.isDigits(id)) {
                        Long longId = Long.valueOf(id);
                        sb.append(resolve(longId, referenceType));
                        fieldVO.addId(longId);
                    }
                }
            }
            fieldVO.setDisplayValue(sb.toString());
            fieldVO.setReferenceType(referenceType);
        }
        return fieldVO;
    }

    protected String resolve(Long id, ReferenceType referenceType) {
        String val = new StringBuilder(id == null ? StringConstants.EMPTY : id.toString()).toString();
        if (referenceType == ReferenceType.constituent) {
            Constituent constituent = constituentService.readConstituentById(id);
            if (constituent == null) {
                logger.warn("resolve: Could not find constituent for ID = " + id);
            } else {
                val = constituent.getDisplayValue();
            }
        } else if (referenceType == ReferenceType.pledge) {
            Pledge pledge = pledgeService.readPledgeById(id);
            if (pledge == null) {
                logger.warn("resolve: Could not find pledge for ID = " + id);
            } else {
                val = pledge.getShortDescription();
            }
        } else if (referenceType == ReferenceType.recurringGift) {
            RecurringGift recurringGift = recurringGiftService.readRecurringGiftById(id);
            if (recurringGift == null) {
                logger.warn("resolve: Could not find recurringGift for ID = " + id);
            } else {
                val = recurringGift.getShortDescription();
            }
        } else if (referenceType == ReferenceType.gift) {
            Gift gift = giftService.readGiftById(id);
            if (gift == null) {
                logger.warn("resolve: Could not find gift for ID = " + id);
            } else {
                val = gift.getShortDescription();
            }
        }
        return val;
    }
}
