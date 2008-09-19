package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.Audit;
import com.mpower.domain.Site;
import com.mpower.type.EntityType;

@Repository("auditDao")
public class JPAAuditDao implements AuditDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	private EntityManager em;

	@Override
	public Audit auditObject(Audit audit) {
		if (audit.getId() == null) {
			em.persist(audit);
			return audit;
		}
		return em.merge(audit);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> allAuditHistoryForSite(Site site) {
		Query q =  em.createQuery("SELECT audit FROM com.mpower.domain.Audit audit WHERE audit.site = :site");
		q.setParameter("site", site);
		List<Audit> audits = q.getResultList();
		return audits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> auditHistoryForEntity(Site site, EntityType entityType, Long objectId) {
		Query q =  em.createQuery("SELECT audit FROM com.mpower.domain.Audit audit WHERE audit.site = :site AND audit.entityType = :entityType AND audit.objectId = :objectId");
		q.setParameter("site", site);
		q.setParameter("entityType", entityType);
		q.setParameter("objectId", objectId);
		List<Audit> audits = q.getResultList();
		return audits;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> auditHistoryForPerson(Long personId) {
		Query q =  em.createQuery("SELECT audit FROM com.mpower.domain.Audit audit WHERE audit.person.id = :personId");
		q.setParameter("personId", personId);
		List<Audit> audits = q.getResultList();
		return audits;
	}
}
