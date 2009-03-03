package com.mpower.dao;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.Email;

@Deprecated
public interface EmailDao {

    public Email maintainEmail(Email email);

    public List<Email> readEmails(Long personId);

    public void deleteEmail(Email email);

    public Email readEmail(Long emailId);

    public List<Email> readCurrentEmails(Long personId, Calendar calendar, boolean mailOnly);

    public void inactivateEmails();
}
