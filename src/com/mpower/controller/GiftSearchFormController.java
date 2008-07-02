package com.mpower.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.domain.User;
import com.mpower.service.GiftService;
import com.mpower.web.common.SessionUtils;

public class GiftSearchFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private GiftService giftService;

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        logger.info("**** in formBackingObject");

        Person p = new Person();
        User user = SessionUtils.lookupUser(request);
        p.setSite(user.getSite());
        Gift g = new Gift();
        g.setPerson(p);
        return g;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        logger.info("**** in onSubmit()");
        Map<String, String> params = new HashMap<String, String>();
        Gift gift = (Gift) command;

        if (gift.getId() != null) {
            params.put("id", gift.getId().toString());
        }

        List<Gift> giftList = giftService.readGifts(gift.getPerson().getSite().getId(), params);
        // TODO: Adding errors.getModel() to our ModelAndView is a "hack" to allow our
        // form to post results back to the same page. We need to get the
        // command from errors and then add our search results to the model.
        ModelAndView mav = new ModelAndView(getSuccessView(), errors.getModel());
        mav.addObject("giftList", giftList);
        mav.addObject("giftListSize", giftList.size());
        return mav;
    }
}
