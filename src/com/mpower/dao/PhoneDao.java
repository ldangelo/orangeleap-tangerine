package com.mpower.dao;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.Phone;

public interface PhoneDao {

    public Phone maintainPhone(Phone phone);

    public List<Phone> readPhones(Long personId);

    public void deletePhone(Phone phone);

    public Phone readPhone(Long phoneId);

    public List<Phone> readCurrentPhones(Long personId, Calendar calendar, boolean mailOnly);

    public void inactivatePhones();
}
