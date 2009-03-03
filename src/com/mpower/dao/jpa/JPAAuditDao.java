package com.mpower.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.dao.AuditDao;
import com.mpower.domain.model.Audit;

@Repository("auditDao")
@Deprecated
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
    public List<Audit> allAuditHistoryForSite() {
        Query q =  em.createNamedQuery("AUDIT_HISTORY_FOR_SITE");
        q.setParameter("site", null);
        List<Audit> audits = q.getResultList();
        return audits;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId) {
        Query q =  em.createNamedQuery("AUDIT_HISTORY_FOR_ENTITY");
        q.setParameter("site", null);
        q.setParameter("entityType", entityTypeDisplay);
        q.setParameter("objectId", objectId);
        List<Audit> audits = q.getResultList();
        return audits;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Audit> auditHistoryForPerson(Long personId) {
        Query q =  em.createNamedQuery("AUDIT_HISTORY_FOR_PERSON");
        q.setParameter("personId", personId);
        List<Audit> audits = q.getResultList();
        return audits;
    }
}
