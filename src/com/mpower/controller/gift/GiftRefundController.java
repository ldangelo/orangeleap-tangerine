package com.mpower.controller.gift;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.Gift;
import com.mpower.service.GiftService;

public class GiftRefundController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private GiftService giftService;

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String giftId = request.getParameter("giftId");
        Gift gift = giftService.refundGift(Long.valueOf(giftId));
        ModelAndView mav = new ModelAndView("redirect:/giftView.htm");
        mav.addObject("giftId", gift.getId());
        return mav;
    }
}
