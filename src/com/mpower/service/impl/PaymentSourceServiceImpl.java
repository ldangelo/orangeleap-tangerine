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

import com.mpower.dao.interfaces.PaymentSourceDao;
import com.mpower.domain.model.PaymentSource;
import com.mpower.domain.model.Person;
import com.mpower.service.AddressService;
import com.mpower.service.AuditService;
import com.mpower.service.InactivateService;
import com.mpower.service.PaymentSourceService;
import com.mpower.service.PhoneService;

@Service("paymentSourceService")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentSourceServiceImpl extends AbstractTangerineService implements PaymentSourceService, InactivateService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "addressService")
    private AddressService addressService;

    @Resource(name = "phoneService")
    private PhoneService phoneService;

    @Resource(name = "paymentSourceDAO")
    private PaymentSourceDao paymentSourceDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainPaymentSource: paymentSource = " + paymentSource);
        }
        if (paymentSource.getId() == null) {
            List<PaymentSource> paymentSourceList = readPaymentSources(paymentSource.getPerson().getId());
            for (PaymentSource a : paymentSourceList) {
                if (paymentSource.equals(a)) {
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
            paymentSource.setAddress(addressService.save(paymentSource.getAddress()));
        }
        if (paymentSource.getPhone() != null && paymentSource.getPhone().getId() == null) {
            paymentSource.setPhone(phoneService.save(paymentSource.getPhone()));
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
    public List<PaymentSource> readPaymentSources(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentSources: constituentId = " + constituentId);
        }
        return paymentSourceDao.readActivePaymentSources(constituentId);
    }

    @Override
    public List<PaymentSource> filterValidPaymentSources(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValidPaymentSources: constituentId = " + constituentId);
        }
        List<PaymentSource> paymentSources = this.readPaymentSources(constituentId);
        List<PaymentSource> filteredPaymentSources = new ArrayList<PaymentSource>();
        for (PaymentSource paymentSource : paymentSources) {
            if (paymentSource.isValid()) {
                filteredPaymentSources.add(paymentSource);
            }
        }
        return filteredPaymentSources;
    }

    @Override
    public List<PaymentSource> readActivePaymentSourcesACHCreditCard(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSourcesACHCreditCard: constituentId = " + constituentId);
        }
        List<String> paymentTypes = new ArrayList<String>(2);
        paymentTypes.add(PaymentSource.ACH);
        paymentTypes.add(PaymentSource.CREDIT_CARD);

        return paymentSourceDao.readActivePaymentSourcesByTypes(constituentId, paymentTypes);
    }

    @Override
    public List<PaymentSource> readActivePaymentSourcesByTypes(Long constituentId, List<String> paymentTypes) {
        if (logger.isDebugEnabled()) {
            logger.debug("readActivePaymentSourcesByTypes: constituentId = " + constituentId + " paymentTypes = " + paymentTypes);
        }
        return paymentSourceDao.readActivePaymentSourcesByTypes(constituentId, paymentTypes);
    }

    @Override
    public PaymentSource readPaymentSource(Long paymentSourceId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentSource: paymentSourceId = " + paymentSourceId);
        }
        return paymentSourceDao.readPaymentSourceById(paymentSourceId);
    }

    @Override
    public PaymentSource readPaymentSourceCreateIfNull(String paymentSourceId, Person constituent) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaymentSourceCreateIfNull: paymentSourceId = " + paymentSourceId + " constituent = " + constituent);
        }
        PaymentSource paymentSource = null;
        if (paymentSourceId == null) {
            paymentSource = new PaymentSource(constituent);
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
    public PaymentSource findPaymentSourceProfile(Long constituentId, String profile) {
        if (logger.isDebugEnabled()) {
            logger.debug("findPaymentSourceProfile: constituentId = " + constituentId + " profile = " + profile);
        }
        return paymentSourceDao.readPaymentSourceByProfile(constituentId, profile);
    }
}
