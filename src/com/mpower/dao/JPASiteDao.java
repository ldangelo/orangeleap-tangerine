package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.entity.Site;
import com.mpower.domain.entity.customization.EntityDefault;
import com.mpower.domain.type.EntityType;

@Repository("siteDao")
public class JPASiteDao implements SiteDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public Site readSite(Long id) {
		return (Site) em.find(Site.class, id);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDefault> readEntityDefaults(Long siteId, List<EntityType> entityTypes) {
        Query query = em.createNamedQuery("READ_ENTITY_DEFAULTS_BY_SITE_AND_ENTITY_FIELD_NAME");
        query.setParameter("siteId", siteId);
        query.setParameter("entityTypes", entityTypes);
        return query.getResultList();
    }
}
