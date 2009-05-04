package com.orangeleap.tangerine.web.customization.handler;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.type.ReferenceType;
import com.orangeleap.tangerine.web.customization.FieldVO;

public class SelectionFieldHandler extends GenericFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PledgeService pledgeService;
    private GiftService giftService;
    private RecurringGiftService recurringGiftService;
    
    public SelectionFieldHandler(ApplicationContext appContext) {
        super(appContext);
        pledgeService = (PledgeService)appContext.getBean("pledgeService");
        giftService = (GiftService)appContext.getBean("giftService");
        recurringGiftService = (RecurringGiftService)appContext.getBean("recurringGiftService");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FieldVO handleField(List<SectionField> sectionFields, SectionField currentField, Locale locale, Object model) {
        FieldVO fieldVO = super.handleField(sectionFields, currentField, locale, model);
        Object propertyValue = super.getPropertyValue(model, fieldVO);
        if (propertyValue != null) {
            fieldVO.setReferenceType(currentField.getFieldDefinition().getReferenceType());
            if (ReferenceType.adjustedGift.equals(fieldVO.getReferenceType())) {
                List<AdjustedGift> adjustedGifts = (List<AdjustedGift>)propertyValue;
                for (AdjustedGift aAdjGift : adjustedGifts) {
                    fieldVO.addDisplayValue(aAdjGift.getShortDescription());
                    fieldVO.addId(aAdjGift.getId());
                }
            }
            else {
                List<Long> ids = (List<Long>)propertyValue;
                for (Long id : ids) {
                    if (ReferenceType.pledge.equals(fieldVO.getReferenceType())) {
                        Pledge pledge = pledgeService.readPledgeById(id);
                        fieldVO.addDisplayValue(pledge.getShortDescription());
                    }
                    else if (ReferenceType.recurringGift.equals(fieldVO.getReferenceType())) {
                        RecurringGift recurringGift = recurringGiftService.readRecurringGiftById(id);
                        fieldVO.addDisplayValue(recurringGift.getShortDescription());
                    }
                    else if (ReferenceType.gift.equals(fieldVO.getReferenceType())) {
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
