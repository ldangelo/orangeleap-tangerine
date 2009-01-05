package com.mpower.dao.jpa;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.mpower.dao.QueryLookupDao;
import com.mpower.domain.QueryLookup;

@Repository("queryLookupDao")
public class JPAQueryLookupDao implements QueryLookupDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public QueryLookup readQueryLookup(String siteName, String fieldDefinitionId) {
        Query query = em.createNamedQuery("READ_QUERY_LOOKUP_BY_SITE_AND_FIELD");
        query.setParameter("siteName", siteName);
        query.setParameter("fieldDefinitionId", fieldDefinitionId);
        List<QueryLookup> list = query.getResultList();
        QueryLookup lookup = null;
        if (list != null && list.size() > 0) {
            if ((list.size() == 1) || (list.get(0).getSite() != null)) {
                lookup = list.get(0);
            } else {
                lookup = list.get(1);
            }
        }
        return lookup;
    }

    @SuppressWarnings("unchecked")
    public List<Object> executeQueryLookup(String queryString, LinkedHashMap<String, String> parameters) {
        Query query = em.createQuery(queryString);
        for (String key : parameters.keySet()) {
            query.setParameter(key, parameters.get(key));
        }
        List<Object> list = query.getResultList();
        return list;
    }
}
