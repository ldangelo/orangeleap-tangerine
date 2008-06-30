package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.mpower.domain.Gift;
import com.mpower.service.GiftService;

public class GiftListController extends AbstractCommandController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private GiftService giftService;

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        logger.info("**** in handleRequestInternal()");

        String personId = request.getParameter("personId");

        Map<String, String> params = new HashMap<String, String>();

        System.out.println("*** map has: " + params);

        List<Gift> giftList = giftService.readGifts(Long.valueOf(personId));
        System.out.println("**** list size: " + giftList.size());
        System.out.println("**** Gift List" + giftList);

        // Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView("giftList", errors.getModel());
        mav.addObject("giftList", giftList);
        mav.addObject("giftListSize", giftList.size());
        return mav;
    }
}
