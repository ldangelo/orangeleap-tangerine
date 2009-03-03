package com.mpower.controller.email;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.controller.constituent.RequiresConstituentEditor;
import com.mpower.domain.model.communication.Email;
import com.mpower.service.EmailService;
import com.mpower.type.ActivationType;

public class EmailEditor extends RequiresConstituentEditor {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name="emailService")
    private EmailService emailService;

    public EmailEditor() {
        super();
    }

    public EmailEditor(String personId) {
        super(personId);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (NumberUtils.isDigits(text)) {
            Long emailId = NumberUtils.createLong(text);
            Email a = emailService.readEmail(emailId);
            setValue(a);
        }
        else if ("new".equals(text)) {
            Email a = new Email(super.getPerson().getId());
            a.setActivationStatus(ActivationType.permanent);
            a.setEmailType("home");
            a.setUserCreated(true);

            setValue(a);
        }
    }
}
