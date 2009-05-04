package com.orangeleap.tangerine.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class GiftViewController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "adjustedGiftService")
    private AdjustedGiftService adjustedGiftService;
    
    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        Gift gift = giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));
        gift.setAdjustedGifts(adjustedGiftService.readAdjustedGiftsForOriginalGiftId(gift.getId()));
        return gift;
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Gift gift = (Gift) command;
        Gift current = giftService.editGift(gift);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
