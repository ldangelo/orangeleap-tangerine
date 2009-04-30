package com.orangeleap.tangerine.controller.gift;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftAdjustmentController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return giftService.createdAdjustedGift(super.getIdAsLong(request, StringConstants.ORIGINAL_GIFT_ID));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        AdjustedGift adjustedGift = (AdjustedGift) command;
        giftService.adjustGift(adjustedGift);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + adjustedGift.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
