package com.orangeleap.tangerine.controller.communicationHistory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.service.CommunicationHistoryService;
import com.orangeleap.tangerine.util.StringConstants;

public class CommunicationHistoryViewController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="communicationHistoryService")
    protected CommunicationHistoryService communicationHistoryService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        // TODO: if the user navigates directly to CommunicationHistory.htm with no constituentId, we should redirect to CommunicationHistorySearch.htm
        return communicationHistoryService.readCommunicationHistoryByIdCreateIfNull(request.getParameter(StringConstants.COMMUNICATION_HISTORY_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        CommunicationHistory communicationHistory = (CommunicationHistory) command;
        if (!communicationHistory.isSystemGenerated()) {
            communicationHistory = communicationHistoryService.maintainCommunicationHistory(communicationHistory);
        }
        return new ModelAndView(getSuccessView() + "?" + StringConstants.COMMUNICATION_HISTORY_ID + "=" + communicationHistory.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request));
    }
    
}
