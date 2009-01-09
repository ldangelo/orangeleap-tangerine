package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PhoneDao;
import com.mpower.domain.Phone;
import com.mpower.domain.Viewable;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.InactivateService;
import com.mpower.service.PhoneService;

@Service("phoneService")
public class PhoneServiceImpl implements PhoneService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "phoneDao")
    private PhoneDao phoneDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Phone savePhone(Phone phone) {
        boolean found = false;
        if (phone.getId() == null) {
            List<Phone> phoneList = readPhones(phone.getPerson().getId());
            for (Phone p : phoneList) {
                if (phone.equals(p)) {
                    found = true;
                    Long id = p.getId();
                    try {
                        BeanUtils.copyProperties(p, phone);
                        p.setId(id);
                    } catch (Exception e) {
                        logger.debug(e.getMessage(), e);
                    }
                    phone = p;
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

    @Override
    public List<Phone> filterValidPhones(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidPhones: personId = " + personId);
        }
        List<Phone> phones = this.readPhones(personId);
        List<Phone> filteredPhones = new ArrayList<Phone>();
        for (Phone phone : phones) {
            if (phone.isValid()) {
                filteredPhones.add(phone);
            }
        }
        return filteredPhones;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivate(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate: id = " + id);
        }
        Phone phone = this.readPhone(id);
        phone.setInactive(true);
        this.savePhone(phone);
    }

    @Override
    public Viewable clone(Viewable viewable) {
        Phone phone = (Phone)viewable;
        if (phone.getId() != null) {
            Phone originalPhone = this.readPhone(phone.getId());

            try {
                phone = (Phone)BeanUtils.cloneBean(originalPhone);
                phone.setId(null);
            }
            catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("clone: Exception occurred", e);
                }
            }
        }
        return phone;
    }
}
