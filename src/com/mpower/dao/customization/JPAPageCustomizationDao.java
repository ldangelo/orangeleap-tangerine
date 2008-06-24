package com.mpower.dao.customization;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.type.PageType;
import com.mpower.entity.customization.SectionDefinition;
import com.mpower.entity.customization.SectionField;

@Repository("pageCustomizationDao")
public class JPAPageCustomizationDao implements PageCustomizationDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<SectionDefinition> readOutOfBoxSectionDefinitions(PageType pageType) {
		Query query = em.createNamedQuery("READ_OUT_OF_BOX_SECTION_DEFINITIONS");
		query.setParameter("pageType", pageType);
		return query.getResultList();
	}

    @SuppressWarnings("unchecked")
	public List<SectionDefinition> readCustomizedSectionDefinitions(Long siteId, PageType pageType) {
		Query query = em.createNamedQuery("READ_CUSTOMIZED_SECTION_DEFINITIONS");
		query.setParameter("siteId", siteId);
		query.setParameter("pageType", pageType);
		return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SectionField> readOutOfBoxSectionFields(String sectionName) {
		Query query = em.createNamedQuery("READ_OUT_OF_BOX_SECTION_FIELDS");
		query.setParameter("sectionName", sectionName);
		return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SectionField> readCustomizedSectionFields(Long siteId, String sectionName) {
		Query query = em.createNamedQuery("READ_CUSTOMIZED_SECTION_FIELDS");
		query.setParameter("siteId", siteId);
		query.setParameter("sectionName", sectionName);
		return query.getResultList();
    }
}
