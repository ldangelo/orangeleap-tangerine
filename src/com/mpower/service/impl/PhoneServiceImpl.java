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

import com.mpower.dao.interfaces.PhoneDao;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.Phone;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.InactivateService;
import com.mpower.service.PhoneService;

@Service("phoneService")
@Transactional(propagation = Propagation.REQUIRED)
public class PhoneServiceImpl implements PhoneService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "phoneDAO")
    private PhoneDao phoneDao;

// TODO: remove
//    public void setAuditService(AuditService auditService) {
//        this.auditService = auditService;
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Phone savePhone(Phone phone) {
        if (logger.isDebugEnabled()) {
            logger.debug("savePhone: phone = " + phone);
        }
        boolean found = false;
        if (phone.getId() == null) {
            List<Phone> phoneList = readPhonesByConstituentId(phone.getPersonId());
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

    @Override
    public List<Phone> readPhonesByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPhonesByConstituentId: constituentId = " + constituentId);
        }
        return phoneDao.readPhonesByConstituentId(constituentId);
    }

    @Override
    public List<Phone> filterValidPhones(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidPhones: constituentId = " + constituentId);
        }
        List<Phone> phones = this.readPhonesByConstituentId(constituentId);
        List<Phone> filteredPhones = new ArrayList<Phone>();
        for (Phone phone : phones) {
            if (phone.isValid()) {
                filteredPhones.add(phone);
            }
        }
        return filteredPhones;
    }

    @Override
    public Phone readPhone(Long phoneId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPhone: phoneId = " + phoneId);
        }
        return phoneDao.readPhoneById(phoneId);
    }

    @Override
    public List<Phone> readCurrentPhones(Long constituentId, Calendar calendar, boolean mailOnly) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCurrentPhones: constituentId = " + constituentId + " calendar = " + calendar + " mailOnly = " + mailOnly);
        }
        return phoneDao.readActivePhonesByConstituentId(constituentId);
        // TODO: move filtering logic from JPAPhoneDao to here
    }

    @Transactional(propagation = Propagation.REQUIRED)
    // TODO: is this being used?
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
    public AbstractEntity clone(AbstractEntity entity) {
        if (logger.isDebugEnabled()) {
            logger.debug("clone: entity = " + entity);
        }
        Phone phone = (Phone)entity;
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
