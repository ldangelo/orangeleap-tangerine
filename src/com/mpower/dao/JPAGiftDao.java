package com.mpower.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.Gift;

@Repository("giftDao")
public class JPAGiftDao implements GiftDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Gift maintainGift(Gift gift) {
        if (gift.getId() == null) {
            em.persist(gift);
            return gift;
        }
        return em.merge(gift);
    }

    @Override
    public Gift readGift(Long giftId) {
        return em.find(Gift.class, giftId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGifts(Long personId) {
        Query query = em.createNamedQuery("READ_GIFT_BY_PERSON");
        query.setParameter("personId", personId);
        return query.getResultList();
    }

    @Override
    public double analyzeMajorDonor(Long personId, String beginDate, String currentDate) {
    	try {
			Query query = em.createNamedQuery("ANALYZE_FOR_MAJOR_DONOR");
			query.setParameter("personId", personId);
			query.setParameter("beginDate", new SimpleDateFormat("yyyy-MM-dd").parse(beginDate));
			query.setParameter("currentDate", new SimpleDateFormat("yyyy-MM-dd").parse(currentDate));
			if (query.getSingleResult() != null) {
				return ((BigDecimal)query.getSingleResult()).doubleValue();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.00;
    }
}
