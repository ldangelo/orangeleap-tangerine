package com.mpower.controller.constituent;

import java.math.BigDecimal;
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

public class PersonViewController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    private GiftService giftService;

    @Resource(name="personService")
    private PersonService personService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String personId = request.getParameter("personId");
        Person person = personService.readConstituentById(Long.valueOf(personId));
        BigDecimal totalGiving = new BigDecimal(0);

        List<Gift> giftList = giftService.readGifts(Long.valueOf(personId));
        for (Gift gft : giftList) {
            totalGiving = totalGiving.add(gft.getAmount() == null ? BigDecimal.ZERO : gft.getAmount());
        }

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("totalGiving", totalGiving);
        mav.addObject("numberOfGifts", giftList.size());
        return mav;
    }
}
