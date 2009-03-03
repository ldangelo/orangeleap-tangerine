package com.mpower.domain.model;

import com.mpower.domain.model.communication.Email;

public interface EmailAware {

    public Email getEmail();

    public void setEmail(Email email);

    public Email getSelectedEmail();
}
