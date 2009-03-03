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
import com.mpower.service.impl.SessionServiceImpl;

@Repository("communicationHistoryDao")
@Deprecated
public class JPACommunicationHistoryDao implements CommunicationHistoryDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;


	@Override
	public CommunicationHistory maintainCommunicationHistory(CommunicationHistory communicationHistory) {
         if (!communicationHistory.getPerson().getSite().getName().equals(SessionServiceImpl.lookupUserSiteName())) {
            throw new RuntimeException("Person object does not belong to current site.");
        }
		 em.persist(communicationHistory);
		 return communicationHistory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommunicationHistory> readCommunicationHistoryByPerson(Long personId) {
        Query q =  em.createNamedQuery("COMMUNICATION_HISTORY_BY_PERSON");
        q.setParameter("siteId", SessionServiceImpl.lookupUserSiteName());
        q.setParameter("personId", personId);
        List<CommunicationHistory> payments = q.getResultList();
        return payments;
	}
	

	@Override
	public CommunicationHistory readCommunicationHistoryById(Long communicationHistoryId) {
		CommunicationHistory communicationHistory = em.find(CommunicationHistory.class, communicationHistoryId);
        // Sanity check
        if (!communicationHistory.getPerson().getSite().getName().equals(SessionServiceImpl.lookupUserSiteName())) {
            throw new RuntimeException("Person object does not belong to current site.");
        }
        return communicationHistory;
	}
}
