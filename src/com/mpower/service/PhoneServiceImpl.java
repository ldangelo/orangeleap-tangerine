package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PhoneDao;
import com.mpower.domain.Phone;

@Service("phoneService")
public class PhoneServiceImpl implements PhoneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "phoneDao")
    private PhoneDao phoneDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Phone savePhone(Phone phone) {
        boolean found = false;
        if (phone.getId() == null) {
            List<Phone> phoneList = readPhones(phone.getPerson().getId());
            for (Phone p : phoneList) {
                if (phone.equals(p)) {
                    phone = p;
                    found = true;
                }
            }
        }
        if (!found) {
            phone = phoneDao.maintainPhone(phone);
            if (phone.isInactive()) {
                auditService.auditObjectInactive(phone);
            } else {
                auditService.auditObject(phone);
            }
        }
        return phone;
    }

    public List<Phone> readPhones(Long personId) {
        return phoneDao.readPhones(personId);
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    public Phone readPhone(Long phoneId) {
        return phoneDao.readPhone(phoneId);
    }

    public List<Phone> readCurrentPhones(Long personId, Calendar calendar, boolean mailOnly) {
        return phoneDao.readCurrentPhones(personId, calendar, mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivatePhones() {
        phoneDao.inactivatePhones();
    }
}
