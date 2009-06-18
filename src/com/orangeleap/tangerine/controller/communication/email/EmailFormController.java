package com.orangeleap.tangerine.controller.communication.email;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.orangeleap.tangerine.controller.TangerineConstituentAttributesFormController;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.util.StringConstants;

public class EmailFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String emailId = request.getParameter(StringConstants.EMAIL_ID);
        return emailService.readByIdCreateIfNull(emailId, getConstituentId(request));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
        Map refData = super.referenceData(request, command, errors);
        emailService.findReferenceDataByConstituentId(refData, getConstituentId(request), "emails", "currentEmails", "currentCorrespondenceEmails");
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Email email = (Email) command;
        if (emailService.alreadyExists(email) != null) {
            errors.reject("errorEmailExists");
            return showForm(request, response, errors);
        }
        emailService.save(email);
        return super.onSubmit(request, response, command, errors);
    }
}
