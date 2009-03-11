package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    protected final Log logger = LogFactory.getLog(getClass());

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
}
