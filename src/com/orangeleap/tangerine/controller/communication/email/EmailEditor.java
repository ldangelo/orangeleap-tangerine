package com.orangeleap.tangerine.controller.communication.email;

import com.orangeleap.tangerine.controller.communication.CommunicationEditor;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.service.CommunicationService;
import com.orangeleap.tangerine.service.EmailService;

public class EmailEditor extends CommunicationEditor<Email> {

    private EmailService emailService;

    public EmailEditor() {
        super();
    }

    public EmailEditor(EmailService emailService, String constituentId) {
        super(constituentId);
        this.emailService = emailService;
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
