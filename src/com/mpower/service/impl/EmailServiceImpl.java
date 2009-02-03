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

import com.mpower.dao.EmailDao;
import com.mpower.domain.Email;
import com.mpower.domain.Viewable;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.EmailService;
import com.mpower.service.InactivateService;

@Service("emailService")
@Transactional(propagation = Propagation.REQUIRED)
public class EmailServiceImpl implements EmailService, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "emailDao")
    private EmailDao emailDao;

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Email saveEmail(Email email) {
        boolean found = false;
        if (email.getId() == null) {
            List<Email> emailList = readEmails(email.getPerson().getId());
            for (Email e : emailList) {
                if (email.equals(e)) {
                    found = true;
                    Long id = e.getId();
                    try {
                        BeanUtils.copyProperties(e, email);
                        e.setId(id);
                    } catch (Exception ex) {
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
            } else {
                auditService.auditObject(email);
            }
        }
        return email;
    }

    public List<Email> readEmails(Long personId) {
        return emailDao.readEmails(personId);
    }

    @Override
    public List<Email> filterValidEmails(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidEmails: personId = " + personId);
        }
        List<Email> emails = this.readEmails(personId);
        List<Email> filteredEmails = new ArrayList<Email>();
        for (Email email : emails) {
            if (email.isValid()) {
                filteredEmails.add(email);
            }
        }
        return filteredEmails;
    }

    public Email readEmail(Long emailId) {
        return emailDao.readEmail(emailId);
    }

    public List<Email> readCurrentEmails(Long personId, Calendar calendar, boolean mailOnly) {
        return emailDao.readCurrentEmails(personId, calendar, mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivateEmails() {
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
    public Viewable clone(Viewable viewable) {
        Email email = (Email)viewable;
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
