package com.orangeleap.tangerine.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.dao.PhoneDao;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.service.PhoneService;

@Service("phoneService")
@Transactional(propagation = Propagation.REQUIRED)
public class PhoneServiceImpl extends AbstractCommunicationService<Phone> implements PhoneService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "phoneDAO")
    private PhoneDao phoneDao;

    @Override
    protected CommunicationDao<Phone> getDao() {
        return phoneDao;
    }

    @Override
    protected Phone createEntity(Long constituentId) {
        return new Phone(constituentId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void maintainResetReceiveCorrespondenceText(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainResetReceiveCorrespondenceText: constituentId = " + constituentId);
        }
        List<Phone> entities = readByConstituentId(constituentId);
        if (entities != null) {
            for (Phone phone : entities) {
                resetReceiveCorrespondenceText(phone);
                getDao().maintainEntity(phone);
            }
        }
    }

    @Override
    public void resetReceiveCorrespondenceText(Phone entity) {
        if (logger.isTraceEnabled()) {
            logger.trace("resetReceiveCorrespondenceText: entity.id = " + entity.getId());
        }
        if (entity != null) {
            entity.setReceiveCorrespondenceText(false);
        }
    }

}
