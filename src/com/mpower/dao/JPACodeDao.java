package com.mpower.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.domain.customization.Code;

@Repository("codeDao")
public class JPACodeDao implements CodeDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String siteName, String codeType) {
		Query query = em.createNamedQuery("READ_CODES_BY_SITE_AND_CODE_TYPE");
		query.setParameter("siteName", siteName);
		query.setParameter("codeType", codeType);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String siteName, String codeType, String startsWith) {
		Query query = em.createNamedQuery("READ_CODES_BY_SITE_AND_CODE_TYPE_AND_CODE_VALUE");
		query.setParameter("siteName", siteName);
		query.setParameter("codeType", codeType);
		query.setParameter("value", startsWith + "%");
		// query.setMaxResults(100);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Code> readCodes(String siteName, String codeType, String startsWith, String partialDescription,
			Boolean inactive) {
		Query query;
		if (inactive!=null) {
			query = em.createNamedQuery("READ_ACTIVE_CODES_BY_SITE_AND_CODE_TYPE_AND_CODE_VALUE_AND_CODE_DESCRIPTION");
			query.setParameter("inactive", inactive);
		} else {
			query = em.createNamedQuery("READ_CODES_BY_SITE_AND_CODE_TYPE_AND_CODE_VALUE_AND_CODE_DESCRIPTION");
		}
		query.setParameter("siteName", siteName);
		query.setParameter("codeType", codeType);
		query.setParameter("value", startsWith + "%");
		query.setParameter("description", "%" + partialDescription + "%");
		return query.getResultList();
	}

	public Code maintainCode(Code code) {
		return em.merge(code);
	}

	@Override
	public Code readCode(Long id) {
		return em.find(Code.class, id);
	}

}
