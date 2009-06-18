package com.orangeleap.tangerine.controller.gift.commitment.recurringGift;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.service.RecurringGiftService;
import com.orangeleap.tangerine.util.StringConstants;

public class RecurringGiftSelectorController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name="recurringGiftService")
    private RecurringGiftService recurringGiftService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long constituentId = Long.parseLong(request.getParameter(StringConstants.CONSTITUENT_ID));
        return new ModelAndView(getViewName(), recurringGiftService.findGiftAppliableRecurringGiftsForConstituent(constituentId, request.getParameter("selectedRecurringGiftIds")));
    }
}
