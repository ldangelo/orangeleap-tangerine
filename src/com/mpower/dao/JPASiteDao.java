package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;

@Repository("siteDao")
public class JPASiteDao implements SiteDao {

	@PersistenceContext
    private EntityManager em;

	@Override
	public Site readSite(String siteName) {
		return em.find(Site.class, siteName);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<EntityDefault> readEntityDefaults(String siteName, List<EntityType> entityTypes) {
        Query query = em.createNamedQuery("READ_ENTITY_DEFAULTS_BY_SITE_AND_ENTITY_FIELD_NAME");
        query.setParameter("siteName", siteName);
        query.setParameter("entityTypes", entityTypes);
        return query.getResultList();
    }
}
