package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;

public interface PhoneAware {

    public Phone getPhone();

    public void setPhone(Phone phone);

    public Phone getSelectedPhone();

    public void setSelectedPhone(Phone phone);
    
    public void setPhoneType(FormBeanType type);
    
    public FormBeanType getPhoneType();
}
