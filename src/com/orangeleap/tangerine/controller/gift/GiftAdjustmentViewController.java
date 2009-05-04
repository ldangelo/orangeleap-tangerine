package com.orangeleap.tangerine.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftAdjustmentViewController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String adjustedGiftIdStr = request.getParameter(StringConstants.ADJUSTED_GIFT_ID);
        if (NumberUtils.isDigits(adjustedGiftIdStr) == false) {
            throw new IllegalArgumentException("The adjustedGiftId is invalid");
        }
        AdjustedGift adjustedGift = adjustedGiftService.readAdjustedGiftById(Long.parseLong(adjustedGiftIdStr));
        return new ModelAndView(getViewName(), "adjustedGift", adjustedGift);
    }
}
