package com.mpower.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PaymentSourceDao;
import com.mpower.domain.PaymentSource;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;

@Service("paymentSourceService")
public class PaymentSourceServiceImpl implements PaymentSourceService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressService")
    private AddressService addressService;

    @Resource(name = "phoneService")
    private PhoneService phoneService;

    @Resource(name = "paymentSourceDao")
    private PaymentSourceDao paymentSourceDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (paymentSource.isInactive()) {
            paymentSource = paymentSourceDao.maintainPaymentSource(paymentSource);
            auditService.auditObjectInactive(paymentSource);
        }
        else {
            if (paymentSource.getAddress() != null && paymentSource.getAddress().getId() == null) {
                paymentSource.setAddress(addressService.saveAddress(paymentSource.getAddress()));
            }
            if (paymentSource.getPhone() != null && paymentSource.getPhone().getId() == null) {
                paymentSource.setPhone(phoneService.savePhone(paymentSource.getPhone()));
            }
            paymentSource = paymentSourceDao.maintainPaymentSource(paymentSource);
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
