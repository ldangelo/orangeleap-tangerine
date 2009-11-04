package com.orangeleap.tangerine.dao.ibatis;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RollupSeriesDao;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RollupSeries table
 */
@Repository("rollupSeriesDAO")
public class IBatisRollupSeriesDao extends AbstractIBatisDao implements RollupSeriesDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRollupSeriesDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRollupSeries: rollupSeriesId = " + rollupSeries.getId());
        }
        return (RollupSeries)insertOrUpdate(rollupSeries, "ROLLUP_SERIES");
    }

    @Override
    public RollupSeries readRollupSeriesById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRollupSeriesById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RollupSeries)getSqlMapClientTemplate().queryForObject("SELECT_ROLLUP_SERIES_BY_ID", params);
    }
    
}