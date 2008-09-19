package com.mpower.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.RecurringGift;

@Repository("recurringGiftDao")
public class JPARecurringGiftDao implements RecurringGiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readRecurringGifts(Date date) {
        Query query = em.createNamedQuery("READ_RECURRING_GIFTS_ON_OR_AFTER_DATE");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public RecurringGift maintain(RecurringGift rg) {
        if (rg.getId() == null) {
            em.persist(rg);
            return rg;
        }
        return em.merge(rg);
    }

    @Override
    public void remove(RecurringGift rg) {
        em.remove(rg);
    }
}
