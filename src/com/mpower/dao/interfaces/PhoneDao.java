package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.communication.Phone;

public interface PhoneDao {

    public Phone maintainPhone(Phone phone);

    public Phone readPhoneById(Long phoneId);
    
    public List<Phone> readPhonesByConstituentId(Long constituentId);

    public List<Phone> readActivePhonesByConstituentId(Long constituentId);

    public void inactivatePhones();
    
}
