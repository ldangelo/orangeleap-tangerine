package com.mpower.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.EmailDao;
import com.mpower.domain.Email;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "emailDao")
    private EmailDao emailDao;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Email saveEmail(Email email) {
        boolean found = false;
        if (email.getId() == null) {
            List<Email> emailList = readEmails(email.getPerson().getId());
            for (Email e : emailList) {
                if (email.equals(e)) {
                    email = e;
                    found = true;
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

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
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
}
