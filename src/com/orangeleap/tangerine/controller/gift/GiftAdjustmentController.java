package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.util.StringConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindException;

/**
 * @version 1.0
 */
public class GiftAdjustmentController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Gift ret = giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));

        AdjustedGift ag = new AdjustedGift();
        ag.setGift(ret);
        ag.setOriginalAmount(ret.getAmount());
        ag.setAmount(null);   // clear the amount. It will hold the adjustment

        // clear percentages. Don't matter for an adjustment
        for(DistributionLine line : ag.getDistributionLines()) {
            line.setPercentage(null);
        }


        return ag;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        AdjustedGift gift = (AdjustedGift) command;
        giftService.adjustGift(gift.getGift());
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + gift.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
