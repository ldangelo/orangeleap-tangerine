package com.mpower.dao.ibatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.RecurringGiftDao;
import com.mpower.domain.model.paymentInfo.RecurringGift;

@Repository("recurringGiftDAO")
public class IBatisRecurringGiftDao extends AbstractIBatisDao implements RecurringGiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisRecurringGiftDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    // TODO: determine if this query requires a join against site?
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
		return (RecurringGift)insertOrUpdate(rg, "RECURRING_GIFT");
    }

    @Override
    // TODO: determine if this query requires a join against site?
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
}
