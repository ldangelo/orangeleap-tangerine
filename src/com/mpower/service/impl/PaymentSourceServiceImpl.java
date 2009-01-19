package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.PaymentSourceDao;
import com.mpower.domain.PaymentSource;
import com.mpower.domain.Person;
import com.mpower.domain.Phone;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.InactivateService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;

@Service("paymentSourceService")
public class PaymentSourceServiceImpl implements PaymentSourceService, InactivateService {

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
        boolean found = false;
        if (paymentSource.getId() == null) {
            List<PaymentSource> paymentSourceList = readPaymentSources(paymentSource.getPerson().getId());
            for (PaymentSource a : paymentSourceList) {
                if (paymentSource.equals(a)) {
                    found = true;
                    Long id = a.getId();
                    try {
                        BeanUtils.copyProperties(a, paymentSource);
                        a.setId(id);
                    } catch (Exception e) {
                        logger.debug(e.getMessage(), e);
                    }
                    paymentSource = a;
                }
            }
        }

        // Payment source is editable in place in the database, unlike address, email and phone, so always save to avoid a transient error.
        if (paymentSource.getAddress() != null && paymentSource.getAddress().getId() == null) {
            paymentSource.setAddress(addressService.saveAddress(paymentSource.getAddress()));
        }
        if (paymentSource.getPhone() != null && paymentSource.getPhone().getId() == null) {
            paymentSource.setPhone(phoneService.savePhone(paymentSource.getPhone()));
        }
        paymentSource.createDefaultProfileName();

        paymentSource = paymentSourceDao.maintainPaymentSource(paymentSource);

        if (paymentSource.isInactive()) {
            auditService.auditObjectInactive(paymentSource);
        }
        else {
            auditService.auditObject(paymentSource);
        }
        
        

        return paymentSource;
    }

    @Override
    public List<PaymentSource> readPaymentSources(Long personId) {
        return paymentSourceDao.readActivePaymentSources(personId);
    }

    @Override
    public List<PaymentSource> filterValidPaymentSources(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidPaymentSources: personId = " + personId);
        }
        List<PaymentSource> paymentSources = this.readPaymentSources(personId);
        List<PaymentSource> filteredPaymentSources = new ArrayList<PaymentSource>();
        for (PaymentSource paymentSource : paymentSources) {
            if (paymentSource.isValid()) {
                filteredPaymentSources.add(paymentSource);
            }
        }
        return filteredPaymentSources;
    }

    @Override
    public List<PaymentSource> readActivePaymentSourcesACHCreditCard(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSourcesACHCreditCard: personId = " + personId);
        }
        List<String> paymentTypes = new ArrayList<String>(2);
        paymentTypes.add(PaymentSource.ACH);
        paymentTypes.add(PaymentSource.CREDIT_CARD);

        return paymentSourceDao.readActivePaymentSourcesByTypes(personId, paymentTypes);
    }

    @Override
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long personId, List<String> paymentTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSourcesByTypes: personId = " + personId + " paymentTypes = " + paymentTypes);
        }
        return paymentSourceDao.readActivePaymentSourcesByTypes(personId, paymentTypes);
    }

    @Override
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public PaymentSource readPaymentSource(Long paymentSourceId) {
        return paymentSourceDao.readPaymentSource(paymentSourceId);
    }

    @Override
    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Person person) {
        PaymentSource paymentSource = null;
        if (paymentSourceId == null) {
            paymentSource = new PaymentSource(person);
        }
        else {
            paymentSource = this.readPaymentSource(Long.valueOf(paymentSourceId));
        }
        return paymentSource;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivate(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate: id = " + id);
        }
        PaymentSource ps = this.readPaymentSource(id);
        ps.setInactive(true);
        this.maintainPaymentSource(ps);
    }

    @Override
    public PaymentSource findPaymentSourceProfile(Long personId, String profile) {
        if (logger.isDebugEnabled()) {
            logger.debug("findPaymentSourceProfile: personId = " + personId + " profile = " + profile);
        }
        return paymentSourceDao.findPaymentSourceProfile(personId, profile);
    }
}
