package com.mpower.controller.communicationHistory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.mpower.domain.CommunicationHistory;
import com.mpower.domain.Person;
import com.mpower.service.CommunicationHistoryService;
import com.mpower.service.PersonService;

public class CommunicationHistoryListController extends ParameterizableViewController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private CommunicationHistoryService communicationHistoryService;

    private PersonService personService;

    public void setcommunicationHistoryService(CommunicationHistoryService communicationHistoryService) {
        this.communicationHistoryService = communicationHistoryService;
    }

    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("**** in handleRequestInternal()");
        String personId = request.getParameter("personId");

        List<CommunicationHistory> communicationHistoryList = communicationHistoryService.readCommunicationHistoryByClient(Long.valueOf(personId));
        Person person = personService.readPersonById(Long.valueOf(personId));

        ModelAndView mav = new ModelAndView(super.getViewName());
        if (person != null) {
            mav.addObject("person", person);
        }
        mav.addObject("communicationHistoryList", communicationHistoryList);
        mav.addObject("communicationHistoryListSize", communicationHistoryList.size());
        return mav;
    }
}
