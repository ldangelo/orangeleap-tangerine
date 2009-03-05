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

import com.mpower.dao.interfaces.EmailDao;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.Email;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.EmailService;
import com.mpower.service.InactivateService;

@Service("emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl extends AbstractCommunicationService<Email> implements EmailService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "emailDAO")
    private EmailDao emailDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Email saveEmail(Email email) {
        if (logger.isDebugEnabled()) {
            logger.debug("saveEmail: email = " + email);
        }
        boolean found = false;
        if (email.getId() == null) {
            List<Email> emailList = readEmailsByConstituentId(email.getPersonId());
            for (Email e : emailList) {
                if (email.equals(e)) {
                    found = true;
                    Long id = e.getId();
                    try {
                        BeanUtils.copyProperties(e, email);
                        e.setId(id);
                    } 
                    catch (Exception ex) {
                        logger.debug(ex.getMessage(), ex);
                    }
                    email = e;
                }
            }
        }
        if (!found) {
            email = emailDao.maintainEmail(email);
            if (email.isInactive()) {
                auditService.auditObjectInactive(email);
            } 
            else {
                auditService.auditObject(email);
            }
        }
        return email;
    }

    @Override
    public List<Email> readEmailsByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readEmailsByConstituentId: constituentId = " + constituentId);
        }
        return emailDao.readEmailsByConstituentId(constituentId);
    }

    @Override
    public List<Email> filterValidEmails(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidEmails: constituentId = " + constituentId);
        }
        List<Email> emails = this.readEmailsByConstituentId(constituentId);
        List<Email> filteredEmails = new ArrayList<Email>();
        for (Email email : emails) {
            if (email.isValid()) {
                filteredEmails.add(email);
            }
        }
        return filteredEmails;
    }

    @Override
    public Email readEmail(Long emailId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readEmail: emailId = " + emailId);
        }
        return emailDao.readEmailById(emailId);
    }

    @Override
    public List<Email> readCurrentEmails(Long constituentId, Calendar calendar, boolean mailOnly) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCurrentEmails: constituentId = " + constituentId + " calendar = " + calendar + " mailOnly = " + mailOnly);
        }
        return filterByActivationType(emailDao.readActiveEmailsByConstituentId(constituentId), mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    // TODO: is this being used?
    public void inactivateEmails() {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivateEmails:");
        }
        emailDao.inactivateEmails();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivate(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate: id = " + id);
        }
        Email email = this.readEmail(id);
        email.setInactive(true);
        this.saveEmail(email);
    }

    @Override
    public AbstractEntity clone(AbstractEntity entity) {
        if (logger.isDebugEnabled()) {
            logger.debug("clone: entity = " + entity);
        }
        Email email = (Email)entity;
        if (email != null) {
            Email originalEmail = this.readEmail(email.getId());
            try {
                email = (Email) BeanUtils.cloneBean(originalEmail);
                email.setId(null);
            }
            catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("clone: Exception occurred", e);
                }
            }
        }
        return email;
    }
}
