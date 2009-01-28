package com.mpower.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.dao.PaymentHistoryDao;
import com.mpower.domain.PaymentHistory;

@Repository("paymentHistoryDao")
public class JPAPaymentHistoryDao implements PaymentHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;


	@Override
	public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory) {
		 em.persist(paymentHistory);
		 return paymentHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistory(Long personId) {
        Query q =  em.createNamedQuery("PAYMENT_HISTORY_FOR_PERSON");
        q.setParameter("personId", personId);
        List<PaymentHistory> payments = q.getResultList();
        return payments;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentHistory> readPaymentHistoryBySite(String siteId) {
        Query q =  em.createNamedQuery("PAYMENT_HISTORY_FOR_SITE");
        q.setParameter("siteId", siteId);
        List<PaymentHistory> payments = q.getResultList();
        return payments;
	}
}
