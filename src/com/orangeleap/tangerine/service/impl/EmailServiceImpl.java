package com.orangeleap.tangerine.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.CommunicationDao;
import com.orangeleap.tangerine.dao.EmailDao;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.service.EmailService;

@Service("emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl extends AbstractCommunicationService<Email> implements EmailService {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "emailDAO")
    private EmailDao emailDao;

    @Override
    protected CommunicationDao<Email> getDao() {
        return emailDao;
    }

    @Override
    protected Email createEntity(Long constituentId) {
        return new Email(constituentId);
    }
}
