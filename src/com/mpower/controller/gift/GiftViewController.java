package com.mpower.controller.gift;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.domain.Gift;
import com.mpower.domain.Viewable;
import com.mpower.util.StringConstants;

public class GiftViewController extends GiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        return giftService.readGiftById(super.getIdAsLong(request, StringConstants.GIFT_ID));
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Gift gift = (Gift) command;
        Gift current = giftService.editGift(gift);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.GIFT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getPersonId(request));
    }
}
