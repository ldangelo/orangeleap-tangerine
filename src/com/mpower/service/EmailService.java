package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.Email;

public interface EmailService {

    public Email saveEmail(Email email);

    public List<Email> readEmails(Long personId);

    public void setAuditService(AuditService auditService);

    public Email readEmail(Long emailId);

    public List<Email> readCurrentEmails(Long personId, Calendar calendar, boolean receiveCorrespondence);
}
