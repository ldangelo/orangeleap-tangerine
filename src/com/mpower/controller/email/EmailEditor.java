package com.mpower.controller.email;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.Email;
import com.mpower.service.EmailService;
import com.mpower.service.PersonService;

public class EmailEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private EmailService emailService;

    public EmailEditor() {
        super();
    }

    public EmailEditor(EmailService emailService, PersonService personService, String personId) {
        super(personService, personId);
        setEmailService(emailService);
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long emailId = NumberUtils.createLong(text);
            Email a = emailService.readEmail(emailId);
            setValue(a);
        }
        else if ("new".equals(text)) {
            Email a = new Email(super.getPerson());
            a.setActivationStatus("permanent");
            a.setEmailType("home");
            a.setUserCreated(true);

            setValue(a);
        }
    }
}
