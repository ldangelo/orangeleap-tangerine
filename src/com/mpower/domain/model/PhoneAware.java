package com.mpower.domain.model;

import com.mpower.domain.model.communication.Phone;
import com.mpower.type.FormBeanType;

public interface PhoneAware {

    public Phone getPhone();

    public void setPhone(Phone phone);

    public Phone getSelectedPhone();

    public void setSelectedPhone(Phone phone);
    
    public void setPhoneType(FormBeanType type);
    
    public FormBeanType getPhoneType();
}
