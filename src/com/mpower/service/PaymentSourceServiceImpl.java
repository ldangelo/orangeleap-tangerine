package com.mpower.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PaymentSourceDao;
import com.mpower.domain.PaymentSource;

@Service("paymentSourceService")
public class PaymentSourceServiceImpl implements PaymentSourceService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "paymentSourceDao")
    private PaymentSourceDao paymentSourceDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentSource savePaymentSource(PaymentSource paymentSource) {
        paymentSource = paymentSourceDao.maintainPaymentSource(paymentSource);
        if (paymentSource.isInactive()) {
            auditService.auditObjectInactive(paymentSource);
        } else {
            auditService.auditObject(paymentSource);
        }
        return paymentSource;
    }

    public List<PaymentSource> readPaymentSources(Long personId) {
        return paymentSourceDao.readActivePaymentSources(personId);
    }

    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public PaymentSource readPaymentSource(Long paymentSourceId) {
        return paymentSourceDao.readPaymentSource(paymentSourceId);
    }

    @Override
    public PaymentSource findPaymentSourceProfile(Long personId, String profile) {
        if (logger.isDebugEnabled()) {
            logger.debug("findPaymentSourceProfile: personId = " + personId + " profile = " + profile);
        }
        return paymentSourceDao.findPaymentSourceProfile(personId, profile);
    }
}
