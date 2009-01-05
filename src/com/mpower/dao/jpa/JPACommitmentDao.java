package com.mpower.dao.jpa;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.stereotype.Repository;

import com.mpower.dao.CommitmentDao;
import com.mpower.dao.util.QueryUtil;
import com.mpower.domain.Commitment;
import com.mpower.type.CommitmentType;
import com.mpower.util.EntityUtility;

@Repository("commitmentDao")
public class JPACommitmentDao implements CommitmentDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Commitment maintainCommitment(Commitment commitment) {
        if (commitment.getId() == null) {
            em.persist(commitment);
            return commitment;
        }
        return em.merge(commitment);
    }

    @Override
    public Commitment readCommitment(Long commitmentId) {
        return em.find(Commitment.class, commitmentId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Commitment> readCommitments(Long personId, CommitmentType commitmentType) {
        Query query = em.createNamedQuery("READ_COMMITMENTS_BY_PERSON_ID_AND_TYPE");
        query.setParameter("personId", personId);
        query.setParameter("commitmentType", commitmentType);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Commitment> readCommitments(String siteName, CommitmentType commitmentType, Map<String, Object> params) {
        boolean whereUsed = true;
        StringBuilder queryString = new StringBuilder("SELECT commitment FROM com.mpower.domain.Commitment commitment WHERE commitment.person.site.name = :siteName AND commitment.commitmentType = :commitmentType");
        Map<String, Object> addressParams = new HashMap<String, Object>();
        Map<String, String> customParams = new HashMap<String, String>();
        LinkedHashMap<String, Object> parameterMap = new LinkedHashMap<String, Object>();
        if (params != null) {
            String key;
            Object value;
            for (Map.Entry<String, Object> pair : params.entrySet()) {
                key = pair.getKey();
                value = pair.getValue();
                boolean isString = true;
                if (value instanceof String) {
                    if (GenericValidator.isBlankOrNull((String) value)) {
                        continue;
                    }
                } else {
                    if (value == null) {
                        continue;
                    }
                    isString = false;
                }
                if (key.startsWith("person.addressMap[")) {
                    addressParams.put(key.substring(key.lastIndexOf('.') + 1), value);
                } else if (key.startsWith("customFieldMap[")) {
                    customParams.put(key.substring(key.indexOf('[') + 1, key.indexOf(']')), (String) value);
                } else {
                    whereUsed = EntityUtility.addWhereOrAnd(whereUsed, queryString);
                    queryString.append(" commitment.");
                    queryString.append(key);
                    String paramName = key.replace(".", "_");
                    if (isString) {
                        queryString.append(" LIKE :");
                        queryString.append(paramName);
                        parameterMap.put(paramName, "%" + value + "%");
                    } else {
                        queryString.append(" = :");
                        queryString.append(paramName);
                        parameterMap.put(paramName, value);
                    }
                }
            }
        }
        queryString.append(getAddressString(addressParams, parameterMap));
        queryString.append(QueryUtil.getCustomString(customParams, parameterMap));

        Query query = em.createQuery(queryString.toString());
        query.setParameter("siteName", siteName);
        for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List commitmentList = query.getResultList();
        return commitmentList;
    }

    private StringBuilder getAddressString(Map<String, Object> addressParams, LinkedHashMap<String, Object> parameterMap) {
        StringBuilder addressString = new StringBuilder();
        if (addressParams != null && !addressParams.isEmpty()) {
            addressString.append(" AND EXISTS ( SELECT address FROM com.mpower.domain.Address address WHERE address.person.id = commitment.person.id ");
            for (Map.Entry<String, Object> pair : addressParams.entrySet()) {
                String key = pair.getKey();
                Object value = pair.getValue();
                addressString.append("AND address.");
                addressString.append(key);
                addressString.append(" LIKE :");
                String paramName = key.replace(".", "_");
                addressString.append(paramName);
                if (value instanceof String) {
                    parameterMap.put(paramName, "%" + value + "%");
                } else {
                    parameterMap.put(paramName, value);
                }
            }
            addressString.append(")");
        }
        return addressString;
    }
}
