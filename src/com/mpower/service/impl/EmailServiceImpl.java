package com.mpower.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.CommunicationDao;
import com.mpower.dao.interfaces.EmailDao;
import com.mpower.domain.model.communication.Email;
import com.mpower.service.EmailService;

@Service("emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl extends AbstractCommunicationService<Email> implements EmailService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

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
