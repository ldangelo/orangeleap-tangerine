package com.mpower.controller.communicationHistory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.CommunicationHistory;
import com.mpower.domain.Viewable;
import com.mpower.service.CommunicationHistoryService;
import com.mpower.util.StringConstants;

public class CommunicationHistoryFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected CommunicationHistoryService communicationHistoryService;

    public void setCommunicationHistoryService(CommunicationHistoryService communicationHistoryService) {
        this.communicationHistoryService = communicationHistoryService;
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        // TODO: if the user navigates directly to CommunicationHistory.htm with no personId, we should redirect to CommunicationHistorySearch.htm
        return communicationHistoryService.readCommunicationHistoryByIdCreateIfNull(request.getParameter(StringConstants.COMMUNICATION_HISTORY_ID), super.getPerson(request));
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBind(request, command, errors);
        CommunicationHistory communicationHistory = (CommunicationHistory) command;
    }
   
    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);
        if (errors.hasErrors()) {
            CommunicationHistory communicationHistory = (CommunicationHistory) command;
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        CommunicationHistory communicationHistory = (CommunicationHistory) command;
        CommunicationHistory current = communicationHistoryService.addCommunicationHistory(communicationHistory);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.COMMUNICATION_HISTORY_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getPersonId(request));
    }
}
