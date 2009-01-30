package com.mpower.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.dao.CommunicationHistoryDao;
import com.mpower.domain.CommunicationHistory;

@Repository("communicationHistoryDao")
public class JPACommunicationHistoryDao implements CommunicationHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;


	@Override
	public CommunicationHistory addCommunicationHistory(CommunicationHistory communicationHistory) {
		 em.persist(communicationHistory);
		 return communicationHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByClient(Long personId) {
        Query q =  em.createNamedQuery("PAYMENT_HISTORY_BY_CLIENT");
        q.setParameter("personId", personId);
        List<CommunicationHistory> payments = q.getResultList();
        return payments;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByRepresentative(Long personId) {
        Query q =  em.createNamedQuery("PAYMENT_HISTORY_BY_REPRESENTATIVE");
        q.setParameter("personId", personId);
        List<CommunicationHistory> payments = q.getResultList();
        return payments;
	}
}
