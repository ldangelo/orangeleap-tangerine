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
import com.orangeleap.tangerine.service.SessionService;
import com.orangeleap.tangerine.util.StringConstants;

public class CommunicationHistoryFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="communicationHistoryService")
    protected CommunicationHistoryService communicationHistoryService;

    @Resource(name="sessionService")
    private SessionService sessionService;

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        sessionService.lookupSite(); // call lookupSite to make sure TangerineAuthenticationToken has the constituentId set
        return communicationHistoryService.readCommunicationHistoryByIdCreateIfNull(request.getParameter(StringConstants.COMMUNICATION_HISTORY_ID), super.getConstituent(request));
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        CommunicationHistory communicationHistory = (CommunicationHistory) command;
        
        boolean saved = true;
        CommunicationHistory current = null;
        try {
            current = communicationHistoryService.maintainCommunicationHistory(communicationHistory);
        } catch (BindException e) {
            saved = false;
            current = communicationHistory;
            errors.addAllErrors(e);
        }

        ModelAndView mav = null;
        if (saved) {
            mav = new ModelAndView(getSuccessView() + "?" + StringConstants.COMMUNICATION_HISTORY_ID + "=" + current.getId() + "&" + StringConstants.CONSTITUENT_ID + "=" + super.getConstituentId(request));
        }
        else {
            mav = super.showForm(request, errors, getFormView());
        }
        return mav;
    }
    
}
