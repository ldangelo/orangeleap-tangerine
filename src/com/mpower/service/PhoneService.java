package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import com.mpower.domain.model.communication.Phone;

public interface PhoneService {

    public Phone savePhone(Phone phone);

    public List<Phone> readPhones(Long constituentId);

    public List<Phone> filterValidPhones(Long constituentId);

//    public void setAuditService(AuditService auditService);

    public Phone readPhone(Long phoneId);

    public List<Phone> readCurrentPhones(Long constituentId, Calendar calendar, boolean receiveCorrespondence);
}
