package com.orangeleap.tangerine.controller.constituent;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;

public class PersonViewController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="giftService")
    private GiftService giftService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String constituentId = request.getParameter("personId");
        Person constituent = constituentService.readConstituentById(Long.valueOf(constituentId));
        BigDecimal totalGiving = new BigDecimal(0);

        List<Gift> giftList = giftService.readMonetaryGifts(Long.valueOf(constituentId));
        for (Gift gft : giftList) {
            totalGiving = totalGiving.add(gft.getAmount() == null ? BigDecimal.ZERO : gft.getAmount());
        }

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (constituent != null) {
            mav.addObject("person", constituent);
        }
        mav.addObject("totalGiving", totalGiving);
        mav.addObject("numberOfGifts", giftList.size());
        return mav;
    }
}
