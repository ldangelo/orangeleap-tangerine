package com.mpower.controller.email;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.mpower.controller.TangerineConstituentAttributesFormController;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.Email;

public class EmailFormController extends TangerineConstituentAttributesFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    protected AbstractEntity findEntity(HttpServletRequest request) {
        String emailId = request.getParameter("emailId");
        Email email = null;
        if (emailId == null) {
            email = new Email(super.getConstituentId(request));
        }
        else {
            email = emailService.readEmail(Long.valueOf(emailId));
        }
        return email;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Map referenceData(HttpServletRequest request) throws Exception {
        Map refData = super.referenceData(request);
        List<Email> emails = emailService.readEmails(super.getConstituentId(request));
        refData.put("emails", emails);
        List<Email> currentEmails = emailService.readCurrentEmails(super.getConstituentId(request), Calendar.getInstance(), false);
        refData.put("currentEmails", currentEmails);
        List<Email> currentCorrespondenceEmails = emailService.readCurrentEmails(super.getConstituentId(request), Calendar.getInstance(), true);
        refData.put("currentCorrespondenceEmails", currentCorrespondenceEmails);

        if (logger.isDebugEnabled()) {
            for (Email e : emails) {
                logger.debug("referenceData: email = " + e.getEmailAddress());
            }
        }
        return refData;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        emailService.saveEmail((Email) command);
        return super.onSubmit(request, response, command, errors);
    }
}
