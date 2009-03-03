package com.mpower.domain.model;

import com.mpower.domain.model.communication.Phone;

public interface PhoneAware {

    public Phone getPhone();

    public void setPhone(Phone phone);

    public Phone getSelectedPhone();
}
