package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

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
		}
		return em.merge(paymentSource);
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

}
