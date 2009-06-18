package com.orangeleap.tangerine.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftAdjustmentController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return adjustedGiftService.createdDefaultAdjustedGift(super.getIdAsLong(request, StringConstants.GIFT_ID));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        AdjustedGift anAdjustedGift = adjustedGiftService.maintainAdjustedGift((AdjustedGift) command);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.ADJUSTED_GIFT_ID + "=" + anAdjustedGift.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request));
    }
}
