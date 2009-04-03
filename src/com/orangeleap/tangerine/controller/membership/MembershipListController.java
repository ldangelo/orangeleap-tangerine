package com.orangeleap.tangerine.controller.membership;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.orangeleap.tangerine.service.ConstituentService;

public class MembershipListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

//    @Resource(name="commitmentService")
//    private CommitmentService commitmentService;

    @Resource(name="constituentService")
    private ConstituentService constituentService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if (logger.isDebugEnabled()) {
//            logger.debug("handleRequestInternal:");
//        }
//        String personId = request.getParameter("personId");
//
//        List<Commitment> commitmentList = commitmentService.readCommitments(Long.valueOf(personId), CommitmentType.MEMBERSHIP);
//        Person constituent = constituentService.readConstituentById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
//        if (constituent != null) {
//            mav.addObject("person", constituent);
//        }
//        mav.addObject("commitmentList", commitmentList);
//        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
