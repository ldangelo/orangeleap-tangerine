package com.mpower.controller.gift;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;

public class GiftListController extends ParameterizableViewController {

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
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("**** in handleRequestInternal()");
        String personId = request.getParameter("personId");

        List<Gift> giftList = giftService.readGifts(Long.valueOf(personId));
        Person person = personService.readPersonById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("giftList", giftList);
        mav.addObject("giftListSize", giftList.size());
        return mav;
    }
}
