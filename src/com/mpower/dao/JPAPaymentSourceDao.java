package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.Gift;
import com.mpower.domain.PaymentSource;

@Repository("paymentSourceDao")
public class JPAPaymentSourceDao implements PaymentSourceDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public PaymentSource maintainPaymentSource(PaymentSource paymentSource) {
        if (paymentSource.getId() == null) {
            em.persist(paymentSource);
            return paymentSource;
        } else {
            return em.merge(paymentSource);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readPaymentSources(Long personId) {
        Query query = em.createNamedQuery("READ_PAYMENT_SOURCES_BY_PERSON_ID");
        query.setParameter("personId", personId);
        List<PaymentSource> paymentSourceList = query.getResultList();
        return paymentSourceList;
    }

    @Override
    public void deletePaymentSource(PaymentSource paymentSource) {
        em.remove(paymentSource);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaymentSource> readActivePaymentSources(Long personId) {
        Query query = em.createNamedQuery("READ_ACTIVE_PAYMENT_SOURCES_BY_PERSON_ID");
        query.setParameter("personId", personId);
        List<PaymentSource> paymentSourceList = query.getResultList();
        return paymentSourceList;
    }

    @Override
    public void inactivatePaymentSource(Long paymentSourceId) {
        Query query = em.createNamedQuery("UPDATE_PAYMENT_SOURCE_TO_INACTIVE");
        query.setParameter("paymentSourceId", paymentSourceId);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void removePaymentSource(Long paymentSourceId) {
        // Logic to determine whether or not we should delete or simply
        // inactivate a payment source
        Query query = em.createNamedQuery("READ_GIFTS_BY_PAYMENT_SOURCE_ID");
        query.setParameter("paymentSourceId", paymentSourceId);
        List<Gift> gifts = query.getResultList();
        if (gifts.size() > 0) {
            inactivatePaymentSource(paymentSourceId);
        } else {
            PaymentSource paymentSource = readPaymentSource(paymentSourceId);
            deletePaymentSource(paymentSource);
        }
    }

    @Override
    public PaymentSource readPaymentSource(Long paymentSourceId) {
        return em.find(PaymentSource.class, paymentSourceId);
    }
}
