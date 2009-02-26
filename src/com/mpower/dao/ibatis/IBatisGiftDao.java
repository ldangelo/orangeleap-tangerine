package com.mpower.dao.ibatis;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.GiftDao;
import com.mpower.domain.model.Gift;

@Repository("giftDAO")
public class IBatisGiftDao extends AbstractIBatisDao implements GiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisGiftDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @Override
    public Gift maintainGift(Gift gift) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainGift: gift = " + gift);
        }
    	if (gift.getId() == null) {
            Calendar transCal = Calendar.getInstance();
            gift.setTransactionDate(transCal.getTime());
            if (gift.getPostmarkDate() == null) {
                Calendar postCal = new GregorianCalendar(transCal.get(Calendar.YEAR), transCal.get(Calendar.MONTH), transCal.get(Calendar.DAY_OF_MONTH));
                gift.setPostmarkDate(postCal.getTime());
            }
        }
        return (Gift)insertOrUpdate(gift, "GIFT");
    }

    @Override
    public Gift readGift(Long giftId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftById: giftId = " + giftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftId);
        return (Gift)getSqlMapClientTemplate().queryForObject("SELECT_GIFT_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGifts(Long personId) {
        Map<String, Object> params = setupParams();
        params.put("personId", personId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_PERSON", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGifts(String siteName, Map<String, Object> params) {
    	return null;
    
/*        boolean whereUsed = true;
        StringBuilder queryString = new StringBuilder("SELECT gift FROM com.mpower.domain.Gift gift WHERE gift.person.site.name = :siteName");
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
                    queryString.append(" gift.");
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
        List giftList = query.getResultList();
        return giftList;
        */
    }

    @Override
    public double analyzeMajorDonor(Long personId, Date beginDate, Date currentDate) {
    	return 0; //TODO
    	/*
        Query query = em.createNamedQuery("ANALYZE_FOR_MAJOR_DONOR");
        query.setParameter("personId", personId);
        query.setParameter("beginDate", beginDate);
        query.setParameter("currentDate", currentDate);
        if (query.getSingleResult() != null) {
            return ((BigDecimal) query.getSingleResult()).doubleValue();
        }
        return 0.00;
        */
    }

//    private StringBuilder getAddressString(Map<String, Object> addressParams, LinkedHashMap<String, Object> parameterMap) {
//        StringBuilder addressString = new StringBuilder();
//        if (addressParams != null && !addressParams.isEmpty()) {
//            addressString.append(" AND EXISTS ( SELECT address FROM com.mpower.domain.Address address WHERE address.person.id = gift.person.id ");
//            for (Map.Entry<String, Object> pair : addressParams.entrySet()) {
//                String key = pair.getKey();
//                Object value = pair.getValue();
//                addressString.append("AND address.");
//                addressString.append(key);
//                addressString.append(" LIKE :");
//                String paramName = key.replace(".", "_");
//                addressString.append(paramName);
//                if (value instanceof String) {
//                    parameterMap.put(paramName, "%" + value + "%");
//                } else {
//                    parameterMap.put(paramName, value);
//                }
//            }
//            addressString.append(")");
//        }
//        return addressString;
//    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsByPersonId(Long personId) {
        Map<String, Object> params = setupParams();
        params.put("personId", personId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_PERSON", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Gift> readAllGiftsBySite() {
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_SITE", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readAllGifts() {
    	return null; // TODO Is this method used at all?  Does it need to be site-specific?
    }

    @SuppressWarnings("unchecked")
    public List<Gift> readGiftsByCommitmentId(Long commitmentId) {
        Map<String, Object> params = setupParams();
        params.put("commitmentId", commitmentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_COMMITMENT", params);
    }

    public BigDecimal readGiftsReceivedSumByCommitmentId(Long commitmentId) {
        Map<String, Object> params = setupParams();
        params.put("commitmentId", commitmentId);
        return (BigDecimal)getSqlMapClientTemplate().queryForObject("READ_GIFTS_RECEIVED_SUM_BY_COMMITMENT_ID", params);
    }

}
