package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.communication.Email;

public interface EmailDao {

    public Email maintainEmail(Email email);

    public List<Email> readEmailsByConstituentId(Long personId);

    public Email readEmailById(Long emailId);

    public List<Email> readActiveEmailsByConstituentId(Long constituentId);

    public void inactivateEmails();
}
