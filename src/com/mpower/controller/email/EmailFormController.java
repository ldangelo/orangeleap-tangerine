package com.mpower.controller.email;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;

import com.mpower.controller.TangerineFormController;
import com.mpower.domain.Email;
import com.mpower.domain.Viewable;
import com.mpower.service.EmailService;

public class EmailFormController extends TangerineFormController {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected EmailService emailService;

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected Viewable findViewable(HttpServletRequest request) {
        String emailId = request.getParameter("emailId");
        Email email = null;
        if (emailId == null) {
            email = new Email(super.getPerson(request));
        }
        else {
            email = emailService.readEmail(Long.valueOf(emailId));
        }
        return email;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addRefData(HttpServletRequest request, Long personId, Map refData) {
        List<Email> emails = emailService.readEmails(personId);
        refData.put("emails", emails);
        List<Email> currentEmails = emailService.readCurrentEmails(personId, Calendar.getInstance(), false);
        refData.put("currentEmails", currentEmails);
        List<Email> currentCorrespondenceEmails = emailService.readCurrentEmails(personId, Calendar.getInstance(), true);
        refData.put("currentCorrespondenceEmails", currentCorrespondenceEmails);

        if (logger.isDebugEnabled()) {
            for (Email e : emails) {
                logger.debug("referenceData: email = " + e.getEmailAddress());
            }
        }
    }

    @Override
    protected void save(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        emailService.saveEmail((Email) command);
    }
}
