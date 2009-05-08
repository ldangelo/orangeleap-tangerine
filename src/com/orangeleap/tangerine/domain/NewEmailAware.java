package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Email;

public interface NewEmailAware extends EmailAware {

    public Email getEmail();

    public void setEmail(Email email);

}
