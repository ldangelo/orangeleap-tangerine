package com.mpower.controller.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mpower.domain.PaymentSource;
import com.mpower.service.PaymentSourceService;

public class PaymentSourceDeleteController implements Controller {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentSourceService paymentSourceService;

    public void setPaymentSourceService(PaymentSourceService paymentSourceService) {
        this.paymentSourceService = paymentSourceService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String paymentSourceId = request.getParameter("paymentSourceId");
        String personId = request.getParameter("personId");
        PaymentSource ps = paymentSourceService.readPaymentSource(Long.valueOf(paymentSourceId));
        ps.setInactive(true);
        paymentSourceService.maintainPaymentSource(ps);
        // Gift gift = giftService.refundGift(Long.valueOf(giftId));
        ModelAndView mav = new ModelAndView("redirect:/paymentManager.htm?personId=" + personId);
        // mav.addObject("paymentSourceId", gift.getId());
        return mav;
    }
}
