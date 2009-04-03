package com.orangeleap.tangerine.dao.ibatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.type.EntityType;

@Repository("recurringGiftDAO")
public class IBatisRecurringGiftDao extends AbstractPaymentInfoEntityDao<RecurringGift> implements RecurringGiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisRecurringGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGifts: date = " + date + " statuses = " + statuses);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);

        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_ON_OR_AFTER_DATE", params);
    }

    @Override
    public RecurringGift maintainRecurringGift(RecurringGift rg) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainRecurringGift: recurringGift = " + rg);
        }
		RecurringGift aRecurringGift = (RecurringGift)insertOrUpdate(rg, "RECURRING_GIFT");
		
		/* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_RECURRING_GIFT_ID", aRecurringGift.getId()); 
        /* Then Insert DistributionLines */
        insertDistributionLines(aRecurringGift, "recurringGiftId");
        return aRecurringGift;
    }

    @Override
    public void removeRecurringGift(RecurringGift rg) {
        if (logger.isDebugEnabled()) {
            logger.debug("removeRecurringGift: id = " + rg.getId());
        }
        Map<String, Object> params = setupParams();
        params.put("id", rg.getId());
        int rows = getSqlMapClientTemplate().delete("DELETE_RECURRING_GIFT", params);
        if (logger.isInfoEnabled()) {
            logger.info("removeRecurringGift: number of rows deleted = " + rows);
        }
    }
    
    @Override
    public RecurringGift readRecurringGiftById(Long recurringGiftId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGiftById: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", recurringGiftId);
        RecurringGift recurringGift = (RecurringGift) getSqlMapClientTemplate().queryForObject("SELECT_RECURRING_GIFT_BY_ID", params);
        loadDistributionLinesCustomFields(recurringGift);

        return recurringGift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readRecurringGiftsByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGiftsByConstituentIdType: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> searchParams) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchRecurringGifts: searchParams = " + searchParams);
        }
        Map<String, Object> params = setupParams();
        QueryUtil.translateSearchParamsToIBatisParams(searchParams, params, new SearchFieldMapperFactory().getMapper(EntityType.recurringGift).getMap());

        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFT_BY_SEARCH_TERMS", params);
    }
}
