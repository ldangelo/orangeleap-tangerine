package com.mpower.controller.commitment.pledge;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.Commitment;
import com.mpower.domain.Person;
import com.mpower.service.CommitmentService;
import com.mpower.service.PersonService;
import com.mpower.type.CommitmentType;

public class PledgeListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CommitmentService commitmentService;

    private PersonService personService;

    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("**** in handleRequest()");
        String personId = request.getParameter("personId");

        List<Commitment> commitmentList = commitmentService.readCommitments(Long.valueOf(personId), CommitmentType.PLEDGE);
        Person person = personService.readPersonById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("commitmentList", commitmentList);
        mav.addObject("commitmentListSize", commitmentList.size());
        return mav;
    }
}
