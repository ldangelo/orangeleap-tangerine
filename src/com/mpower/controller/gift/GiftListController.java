package com.mpower.controller.gift;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.service.GiftService;
import com.mpower.service.PersonService;

public class GiftListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    private GiftService giftService;

    @Resource(name="personService")
    private PersonService personService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("handleRequestInternal:");
        }
        String personId = request.getParameter("personId");

        List<Gift> giftList = giftService.readGifts(Long.valueOf(personId));
        Person person = personService.readConstituentById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("giftList", giftList);
        mav.addObject("giftListSize", giftList.size());
        return mav;
    }
}
