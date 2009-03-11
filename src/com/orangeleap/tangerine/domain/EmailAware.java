package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.FormBeanType;

public interface EmailAware {

    public Email getEmail();

    public void setEmail(Email email);

    public Email getSelectedEmail();

    public void setSelectedEmail(Email email);
    
    public void setEmailType(FormBeanType type);
    
    public FormBeanType getEmailType();
}
