package com.orangeleap.tangerine.controller.gift;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftAdjustmentViewController extends GiftAdjustmentController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        refData.put(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount((AdjustedGift) command));
        return refData;
    }

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String adjustedGiftIdStr = request.getParameter(StringConstants.ADJUSTED_GIFT_ID);
        if (NumberUtils.isDigits(adjustedGiftIdStr) == false) {
            throw new IllegalArgumentException("The adjustedGiftId is invalid");
        }
        AdjustedGift adjustedGift = adjustedGiftService.readAdjustedGiftById(Long.parseLong(adjustedGiftIdStr));
        request.setAttribute(StringConstants.HIDE_ADJUST_GIFT_BUTTON, adjustedGiftService.isAdjustedAmountEqualGiftAmount(adjustedGift));
        return adjustedGift;
    }
}
