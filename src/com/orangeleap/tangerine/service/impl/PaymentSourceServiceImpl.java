package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.PaymentSourceDao;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.InactivateService;
import com.orangeleap.tangerine.service.PaymentSourceService;
import com.orangeleap.tangerine.type.FormBeanType;

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
    
    @Override
    public Map<String, Object> checkForSameConflictingPaymentSources(PaymentSourceAware paymentSourceAware) {
        if (logger.isDebugEnabled()) {
            logger.debug("checkForConflictingPaymentSources: paymentSource type = " + paymentSourceAware.getPaymentType());
        }
        Map<String, Object> conflictingPaymentSources = new HashMap<String, Object>();
        if (PaymentSource.CREDIT_CARD.equals(paymentSourceAware.getPaymentType())) {
            List<PaymentSource> sources = paymentSourceDao.readExistingCreditCards(paymentSourceAware.getPaymentSource().getCreditCardNumber());
            if (isSameCreditCard(paymentSourceAware, sources) == false) {
                conflictingPaymentSources.put("names", checkAccountNamesPaymentSources(paymentSourceAware.getPaymentSource(), sources));
                conflictingPaymentSources.put("dates", checkCreditCardDatesPaymentSource(paymentSourceAware.getPaymentSource(), sources));
            }
        }
        else if (PaymentSource.ACH.equals(paymentSourceAware.getPaymentType())) {
            List<PaymentSource> sources = paymentSourceDao.readExistingAchAccounts(paymentSourceAware.getPaymentSource().getAchAccountNumber(), paymentSourceAware.getPaymentSource().getAchRoutingNumber());
            if (isSameACH(paymentSourceAware, sources) == false) {
                conflictingPaymentSources.put("names", checkAccountNamesPaymentSources(paymentSourceAware.getPaymentSource(), sources));
            }
        }
        return conflictingPaymentSources;
    }
    
    private boolean isSameCreditCard(PaymentSourceAware aware, List<PaymentSource> sources) {
        boolean foundSame = false;
        if (sources != null) {
            PaymentSource newSource = aware.getPaymentSource();
            for (PaymentSource src : sources) {
                if (src.getCreditCardNumberEncrypted().equals(newSource.getCreditCardNumberEncrypted()) &&
                        src.getCreditCardType().equals(newSource.getCreditCardType()) &&
                        src.getCreditCardHolderName().equals(newSource.getCreditCardHolderName()) && 
                        src.getCreditCardExpirationMonth().equals(newSource.getCreditCardExpirationMonth()) && 
                        src.getCreditCardExpirationYear().equals(newSource.getCreditCardExpirationYear())) {
                    foundSame = true;
                    aware.setSelectedPaymentSource(src);
                    aware.setPaymentSourceType(FormBeanType.EXISTING);
                    aware.setPaymentSource(null);
                    break;
                }
            }
        }
        return foundSame;
    }
    
    private boolean isSameACH(PaymentSourceAware aware, List<PaymentSource> sources) {
        boolean foundSame = false;
        if (sources != null) {
            PaymentSource newSource = aware.getPaymentSource();
            for (PaymentSource src : sources) {
                if (src.getAchAccountNumberEncrypted().equals(newSource.getAchAccountNumberEncrypted()) &&
                        src.getAchHolderName().equals(newSource.getAchHolderName()) && 
                        src.getAchRoutingNumber().equals(newSource.getAchRoutingNumber())) {
                    foundSame = true;
                    aware.setSelectedPaymentSource(src);
                    aware.setPaymentSourceType(FormBeanType.EXISTING);
                    aware.setPaymentSource(null);
                    break;
                }
            }
        }
        return foundSame;
    }
    
    private Set<String> checkAccountNamesPaymentSources(PaymentSource paymentSource, List<PaymentSource> sources) {
        if (logger.isDebugEnabled()) {
            logger.debug("checkAccountNamesPaymentSources: paymentSource type = " + paymentSource.getPaymentType());
        }
        Set<String> names = new TreeSet<String>();
        boolean hasName = false;
        if (sources != null) {
            for (PaymentSource thisSource : sources) {
                if ((PaymentSource.ACH.equals(paymentSource.getPaymentType()) && thisSource.getAchHolderName().equals(paymentSource.getAchHolderName())) || 
                     (PaymentSource.CREDIT_CARD.equals(paymentSource.getPaymentType())) && thisSource.getCreditCardHolderName().equals(paymentSource.getCreditCardHolderName())) {
                    hasName = true;
                    break;
                }
                else {
                    names.add(PaymentSource.ACH.equals(paymentSource.getPaymentType()) ? thisSource.getAchHolderName() : thisSource.getCreditCardHolderName());
                }
            }
        }
        if (hasName) {
            names.clear(); // no conflicting names, clear the name list
        }
        return names;
    }
    
    private List<PaymentSource> checkCreditCardDatesPaymentSource(PaymentSource paymentSource, List<PaymentSource> sources) {
        if (logger.isDebugEnabled()) {
            logger.debug("checkCreditCardDatesPaymentSource:");
        }
        List<PaymentSource> dates = new ArrayList<PaymentSource>();
        if (sources != null) {
            for (PaymentSource thisSource : sources) {
                if ((thisSource.getCreditCardExpirationMonth().equals(paymentSource.getCreditCardExpirationMonth()) == false || 
                    thisSource.getCreditCardExpirationYear().equals(paymentSource.getCreditCardExpirationYear()) == false) && 
                    thisSource.getCreditCardHolderName().equals(paymentSource.getCreditCardHolderName())) {
                    dates.add(thisSource);
                }
            }
        }
        return dates;
    }
}
