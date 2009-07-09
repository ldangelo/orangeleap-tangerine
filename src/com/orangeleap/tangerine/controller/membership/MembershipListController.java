package com.orangeleap.tangerine.controller.membership;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.service.ConstituentService;

public class MembershipListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

//    @Resource(name="commitmentService")
//    private CommitmentService commitmentService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if (logger.isDebugEnabled()) {
//            logger.debug("handleRequestInternal:");
//        }
//        String constituentId = request.getParameter("constituentId");
//
//        List<Commitment> commitmentList = commitmentService.readCommitments(Long.valueOf(constituentId), CommitmentType.MEMBERSHIP);
//        Constituent constituent = constituentService.readConstituentById(Long.valueOf(constituentId));

        ModelAndView mav = new ModelAndView(super.getViewName());
//        if (constituent != null) {
//            mav.addObject("constituent", constituent);
//        }
//        mav.addObject("commitmentList", commitmentList);
//        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
