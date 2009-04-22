package com.orangeleap.tangerine.dao.ibatis;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("giftDAO")
public class IBatisGiftDao extends AbstractPaymentInfoEntityDao<Gift> implements GiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Gift maintainGift(final Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainGift: giftId = " + gift.getId());
        }
        Gift aGift = (Gift)insertOrUpdate(gift, "GIFT");
        
        /* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_GIFT_ID", aGift.getId());
        /* Then insert DistributionLines */
        insertDistributionLines(aGift, "giftId");
        
        deleteInsertAssociatedPledges(aGift);
        deleteInsertAssociatedRecurringGifts(aGift);
        
        return aGift;
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftById: giftId = " + giftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftId);
        Gift gift = (Gift)getSqlMapClientTemplate().queryForObject("SELECT_GIFT_BY_ID", params);
        
        loadDistributionLinesCustomFields(gift);
        if (gift != null) {
            gift.setAssociatedPledgeIds(readAssociatedPledgeIdsForGift(giftId));
            if (gift.getAssociatedPledgeIds() == null || gift.getAssociatedPledgeIds().isEmpty()) {
                gift.setAssociatedRecurringGiftIds(readAssociatedRecurringGiftIdsForGift(giftId));
            }
            else {
                gift.setAssociatedRecurringGiftIds(new ArrayList<Long>(0)); // default set
            }
            loadCustomFields(gift.getPerson());
        }
        return gift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMonetaryGiftsByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_CONSTITUENT_ID", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public PaginatedResult readPaginatedMonetaryGiftsByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedMonetaryGiftsByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("GIFTS_BY_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> searchGifts(Map<String, Object> searchparams) {
    	Map<String, Object> params = setupParams();
    	QueryUtil.translateSearchParamsToIBatisParams(searchparams, params, new SearchFieldMapperFactory().getMapper(EntityType.gift).getMap());
    	
    	List<Gift> gifts = getSqlMapClientTemplate().queryForList("SELECT_GIFT_BY_SEARCH_TERMS", params);
    	return gifts;
    }
    

    @Override
    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeMajorDonor: constituentId = " + constituentId + " beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        BigDecimal bd = (BigDecimal)getSqlMapClientTemplate().queryForObject("ANALYZE_FOR_MAJOR_DONOR", params);
        if (bd == null) {
            return 0.00d;
        }
        return bd.doubleValue();
    }
    
	@SuppressWarnings("unchecked")
    @Override
    public List<Person> analyzeLapsedDonor(Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeLapsedDonor:  beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        return getSqlMapClientTemplate().queryForList("ANALYZE_FOR_LAPSED_DONOR", params);
    }

    @SuppressWarnings("unchecked")
	@Override
	public List<Gift> readAllGiftsBySite() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsBySite:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_SITE", params);
	}
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByDateRange:");
        }
        Map<String, Object> params = setupParams();
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        List list = getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_DATE_RANGE", params);
        if (list.size() > 5000) {
            throw new RuntimeException("Selection too large, reduce selection range."); // Note this needs to be one less than the 5001 in gift.xml
        }
        return list;
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsByRecurringGiftId(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsByRecurringGiftId: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> params = setupParams();
        params.put("recurringGiftId", recurringGiftId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_RECURRING_GIFT_ID", params);
    }

    @Override
    public BigDecimal readGiftsReceivedSumByRecurringGiftId(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsReceivedSumByRecurringGiftId: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> params = setupParams();
        params.put("recurringGiftId", recurringGiftId);
        BigDecimal result = (BigDecimal) getSqlMapClientTemplate().queryForObject("READ_GIFTS_RECEIVED_SUM_BY_RECURRING_GIFT_ID", params);
        if (result == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsByPledgeId(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsByPledgeId: pledgeId = " + pledgeId);
        }
        Map<String, Object> params = setupParams();
        params.put("pledgeId", pledgeId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_PLEDGE_ID", params);
    }

    @Override
    public BigDecimal readGiftsReceivedSumByPledgeId(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsReceivedSumByPledgeId: pledgeId = " + pledgeId);
        }
        Map<String, Object> params = setupParams();
        params.put("pledgeId", pledgeId);
        BigDecimal result = (BigDecimal) getSqlMapClientTemplate().queryForObject("READ_GIFTS_RECEIVED_SUM_BY_PLEDGE_ID", params);
        if (result == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedPledgeIdsForGift(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedPledgeIdsForGift: giftId = " + giftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("giftId", giftId);
        return getSqlMapClientTemplate().queryForList("SELECT_PLEDGE_GIFT_BY_GIFT_ID", paramMap);
    }
    
    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedRecurringGiftIdsForGift(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedRecurringGiftIdsForGift: giftId = " + giftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("giftId", giftId);
        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFT_GIFT_BY_GIFT_ID", paramMap);
    }
    
    protected void deleteInsertAssociatedPledges(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteInsertAssociatedPledges: giftId = " + gift.getId());
        }
        getSqlMapClientTemplate().delete("DELETE_PLEDGE_GIFT_BY_GIFT_ID", gift.getId());
        if (gift.getAssociatedPledgeIds() != null) {
            for (Long associatedPledgeId : gift.getAssociatedPledgeIds()) {
                Map<String, Long> paramMap = new HashMap<String, Long>(2);
                paramMap.put("pledgeId", associatedPledgeId);
                paramMap.put("giftId", gift.getId());
                getSqlMapClientTemplate().insert("INSERT_PLEDGE_GIFT", paramMap);
            }
        }
    }
    
    protected void deleteInsertAssociatedRecurringGifts(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteInsertAssociatedRecurringGifts: giftId = " + gift.getId());
        }
        getSqlMapClientTemplate().delete("DELETE_RECURRING_GIFT_GIFT_BY_GIFT_ID", gift.getId());
        if (gift.getAssociatedRecurringGiftIds() != null) {
            for (Long associatedRecurringGiftId : gift.getAssociatedRecurringGiftIds()) {
                Map<String, Long> paramMap = new HashMap<String, Long>(2);
                paramMap.put("recurringGiftId", associatedRecurringGiftId);
                paramMap.put("giftId", gift.getId());
                getSqlMapClientTemplate().insert("INSERT_RECURRING_GIFT_GIFT", paramMap);
            }
        }
    }
}
