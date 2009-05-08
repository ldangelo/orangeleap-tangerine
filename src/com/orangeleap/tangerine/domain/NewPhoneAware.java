package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Phone;

public interface NewPhoneAware extends PhoneAware {

    public Phone getPhone();

    public void setPhone(Phone phone);

}
