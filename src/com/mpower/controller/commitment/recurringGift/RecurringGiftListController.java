package com.mpower.controller.commitment.recurringGift;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.service.CommitmentService;
import com.mpower.service.PersonService;
import com.mpower.type.CommitmentType;

//TODO: refactor this and PledgeListController into one class
public class RecurringGiftListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name="commitmentService")
    private CommitmentService commitmentService;

    @Resource(name="personService")
    private PersonService personService;

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("handleRequestInternal:");
        }
        String personId = request.getParameter("personId");

        List<Commitment> commitmentList = commitmentService.readCommitments(Long.valueOf(personId), CommitmentType.RECURRING_GIFT);
        Person person = personService.readConstituentById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("commitmentList", commitmentList);
        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
