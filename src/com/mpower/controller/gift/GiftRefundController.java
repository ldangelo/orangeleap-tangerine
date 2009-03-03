package com.mpower.controller.gift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.GiftService;

public class GiftRefundController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    private GiftService giftService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String giftId = request.getParameter("giftId");
        Gift gift = giftService.refundGift(Long.valueOf(giftId));
        return new ModelAndView(getViewName(), "giftId", gift.getId()); // TODO: must append personId=1 to URL that is going to be redirected
    }
}
