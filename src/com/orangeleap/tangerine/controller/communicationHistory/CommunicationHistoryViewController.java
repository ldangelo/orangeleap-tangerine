package com.orangeleap.tangerine.controller.communicationHistory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.util.StringConstants;

public class CommunicationHistoryViewController extends CommunicationHistoryFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        return communicationHistoryService.readCommunicationHistoryById(super.getIdAsLong(request, StringConstants.COMMUNICATION_HISTORY_ID));
    }
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        CommunicationHistory communicationHistory = (CommunicationHistory) command;
        CommunicationHistory current = communicationHistory; // TODO communicationHistoryService.editCommunicationHistory(communicationHistory);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.COMMUNICATION_HISTORY_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getConstituentId(request));
    }
}
