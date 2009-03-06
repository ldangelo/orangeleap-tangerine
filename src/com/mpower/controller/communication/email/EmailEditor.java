package com.mpower.controller.communication.email;

import javax.annotation.Resource;

import com.mpower.controller.communication.CommunicationEditor;
import com.mpower.domain.model.communication.Email;
import com.mpower.service.CommunicationService;
import com.mpower.service.EmailService;

public class EmailEditor extends CommunicationEditor<Email> {

    @Resource(name="emailService")
    private EmailService emailService;

    public EmailEditor() {
        super();
    }

    public EmailEditor(String constituentId) {
        super(constituentId);
    }

    @Override
    protected Email createEntity(Long constituentId) {
        return new Email(constituentId);
    }

    @Override
    protected CommunicationService<Email> getCommunicationService() {
        return emailService;
    }
}
