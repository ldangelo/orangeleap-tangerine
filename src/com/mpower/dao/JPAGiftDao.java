package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.entity.Gift;

@Repository("giftDao")
public class JPAGiftDao implements GiftDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Gift maintainGift(Gift gift) {
        if (gift.getId() == null) {
            em.persist(gift);
        }
        gift = em.merge(gift);
        return gift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGifts(Long personId) {
        Query query = em.createNamedQuery("READ_GIFT_BY_PERSON");
        query.setParameter("personId", personId);
        return query.getResultList();
    }
}
