package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.model.communication.Email;

public interface EmailService {

    public Email saveEmail(Email email);

    public List<Email> readEmailsByConstituentId(Long constituentId);

    public List<Email> filterValidEmails(Long constituentId);

    public Email readEmail(Long emailId);

    public List<Email> readCurrentEmails(Long constituentId, Calendar calendar, boolean receiveCorrespondence);
}
