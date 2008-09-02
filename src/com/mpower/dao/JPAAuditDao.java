package com.mpower.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.Audit;

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
}
