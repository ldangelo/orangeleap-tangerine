package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.PaymentSourceDao;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.InactivateService;
import com.orangeleap.tangerine.service.PaymentSourceService;

@Service("paymentSourceService")
@Transactional(propagation = Propagation.REQUIRED)
public class PaymentSourceServiceImpl extends AbstractPaymentService implements PaymentSourceService, InactivateService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "paymentSourceDAO")
    private PaymentSourceDao paymentSourceDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainPaymentSource: paymentSource = " + paymentSource);
        }
        
        maintainEntityChildren(paymentSource, paymentSource.getPerson());
        
        paymentSource.createDefaultProfileName();

        paymentSource = paymentSourceDao.maintainPaymentSource(paymentSource);

        if (paymentSource.isInactive()) {
            auditService.auditObjectInactive(paymentSource, paymentSource.getPerson());
        }
        else {
            auditService.auditObject(paymentSource, paymentSource.getPerson());
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
        List<PaymentSource> paymentSources = paymentSourceDao.readAllPaymentSources(constituentId);
        List<PaymentSource> filteredPaymentSources = new ArrayList<PaymentSource>();
        for (PaymentSource paymentSource : paymentSources) {
            if (paymentSource.isValid()) {
                filteredPaymentSources.add(paymentSource);
            }
        }
        return filteredPaymentSources;
    }

    @Override
    public List<PaymentSource> readAllPaymentSourcesACHCreditCard(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAllPaymentSourcesACHCreditCard: constituentId = " + constituentId);
        }
        List<PaymentSource> sources = paymentSourceDao.readAllPaymentSources(constituentId);
        List<PaymentSource> filteredSources = new ArrayList<PaymentSource>(); 
        for (PaymentSource src : sources) {
            if (PaymentSource.ACH.equals(src.getPaymentType()) || PaymentSource.CREDIT_CARD.equals(src.getPaymentType())) {
                filteredSources.add(src);
            }
        }
        return filteredSources;
    }
    
    @Override
    public Map<String, List<PaymentSource>> groupPaymentSources(Long constituentId, PaymentSource selectedPaymentSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("groupPaymentSources: constituentId = " + constituentId);
        }
        Map<String, List<PaymentSource>> groupedSources = new HashMap<String, List<PaymentSource>>();
        List<PaymentSource> sources = filterValidPaymentSources(constituentId);
        if (sources != null) { 
            for (PaymentSource src : sources) {
                if (src.isInactive() == false || (selectedPaymentSource != null && selectedPaymentSource.getId().equals(src.getId()))) {
                    List<PaymentSource> list = groupedSources.get(src.getPaymentType());
                    if (list == null) {
                        list = new ArrayList<PaymentSource>();
                        groupedSources.put(src.getPaymentType(), list);
                    }
                    list.add(src);
                }
            }
        }
        return groupedSources;
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
