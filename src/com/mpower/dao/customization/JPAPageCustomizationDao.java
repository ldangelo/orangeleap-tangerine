package com.mpower.dao.customization;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.type.PageType;

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
	public List<SectionDefinition> readCustomizedSectionDefinitions(String siteName, PageType pageType) {
		Query query = em.createNamedQuery("READ_CUSTOMIZED_SECTION_DEFINITIONS");
		query.setParameter("siteName", siteName);
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
    public List<SectionField> readCustomizedSectionFields(String siteName, String sectionName) {
		Query query = em.createNamedQuery("READ_CUSTOMIZED_SECTION_FIELDS");
		query.setParameter("siteName", siteName);
		query.setParameter("sectionName", sectionName);
		return query.getResultList();
    }
}
