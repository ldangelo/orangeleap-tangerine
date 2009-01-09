package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.Phone;

public interface PhoneService {

    public Phone savePhone(Phone phone);

    public List<Phone> readPhones(Long personId);

    public List<Phone> filterValidPhones(Long personId);

    public void setAuditService(AuditService auditService);

    public Phone readPhone(Long phoneId);

    public List<Phone> readCurrentPhones(Long personId, Calendar calendar, boolean receiveCorrespondence);
}
