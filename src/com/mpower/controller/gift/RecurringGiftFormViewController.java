package com.mpower.controller.gift;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.domain.Commitment;
import com.mpower.util.StringConstants;

public class RecurringGiftFormViewController extends RecurringGiftFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Commitment commitment = (Commitment)command;
        Commitment current = commitmentService.maintainCommitment(commitment);
        return new ModelAndView(getSuccessView() + "?" + StringConstants.COMMITMENT_ID + "=" + current.getId() + "&" + StringConstants.PERSON_ID + "=" + super.getPersonId(request));
    }
}
