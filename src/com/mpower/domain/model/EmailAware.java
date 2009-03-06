package com.mpower.domain.model;

import com.mpower.domain.model.communication.Email;
import com.mpower.type.FormBeanType;

public interface EmailAware {

    public Email getEmail();

    public void setEmail(Email email);

    public Email getSelectedEmail();

    public void setSelectedEmail(Email email);
    
    public void setEmailType(FormBeanType type);
    
    public FormBeanType getEmailType();
}
