package com.mpower.controller.email;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Email;
import com.mpower.service.EmailService;

public class EmailEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private EmailService emailService;

    public EmailEditor() {
        super();
    }

    public EmailEditor(EmailService emailService) {
        super();
        setEmailService(emailService);
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long emailId = NumberUtils.createLong(text);
            Email a = emailService.readEmail(emailId);
            setValue(a);
        } else {
            Email a = new Email();
            a.setActivationStatus("permanent");
            a.setEmailType("home");
            setValue(a);
        }
    }
}
