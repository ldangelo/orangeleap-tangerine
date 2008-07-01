package com.mpower.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;

public class GiftFormController extends SimpleFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private GiftService giftService;

    private PersonService personService;

    public void setGiftService(GiftService giftService) {
        this.giftService = giftService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {

        String giftId = request.getParameter("giftId");
        Gift gift = null;
        if (giftId == null) {
            // create person
            gift = giftService.createDefaultGift(1L);
            String personId = request.getParameter("personId");
            Person person = personService.readPersonById(Long.valueOf(personId));
            if (person == null) {
                logger.error("**** person not found for id: " + personId);
                return gift;
            }
            gift.setPerson(person);
        } else {
            // lookup gift
            gift = giftService.readGiftById(new Long(giftId));
        }

        return gift;
    }

    @Override
    public ModelAndView onSubmit(Object command, BindException errors) throws ServletException {
        Gift gift = (Gift) command;
        Gift current = giftService.maintainGift(gift);

        ModelAndView mav = new ModelAndView("redirect:/giftView.htm", errors.getModel());
        mav.addObject("giftId", current.getId());
        return mav;
    }
}
