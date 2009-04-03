package com.orangeleap.tangerine.controller.commitment.pledge;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.service.PledgeService;
import com.orangeleap.tangerine.util.StringConstants;

public class PledgeSelectorController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="pledgeService")
    private PledgeService pledgeService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long constituentId = Long.parseLong(request.getParameter(StringConstants.CONSTITUENT_ID));
        return new ModelAndView(getViewName(), pledgeService.findNotCancelledPledges(constituentId, request.getParameter("selectedPledgeIds")));
    }
}
