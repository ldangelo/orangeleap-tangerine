package com.mpower.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.SiteDao;
import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;

@Repository("siteDao")
public class JPASiteDao implements SiteDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
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

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public List<Site> readSites() {
		Query query = em.createNamedQuery("READ_SITES");
		return query.getResultList();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public Site createSite(String siteName, String merchantNumber, Site parentSite) {
		Site site = new Site();
		site.setMerchantNumber(merchantNumber);
		site.setName(siteName);
		em.persist(site);
		return site;
	}

	
	
}
