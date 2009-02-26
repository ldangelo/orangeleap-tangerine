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
import com.mpower.domain.model.RecurringGift;

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
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses) {
        if (logger.isDebugEnabled()) {
            logger.debug("readRecurringGifts: date = " + date);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);

        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_ON_OR_AFTER_DATE", params);
    }

    @Override
    public RecurringGift maintain(RecurringGift rg) {
		return (RecurringGift)insertOrUpdate(rg, "RECURRING_GIFT");
    }

    @Override
    public void remove(RecurringGift rg) {
        if (logger.isDebugEnabled()) {
            logger.debug("RecurringGiftDao.remove: id = " + rg.getId());
        }
        Map<String, Object> params = setupParams();
        params.put("id", rg.getId());
        getSqlMapClientTemplate().queryForObject("DELETE_RECURRING_GIFT", params);
    }
    
}
