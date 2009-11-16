package com.orangeleap.tangerine.dao.ibatis;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RollupValueDao;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RollupValue table
 */
@Repository("rollupValueDAO")
public class IBatisRollupValueDao extends AbstractIBatisDao implements RollupValueDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRollupValueDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public RollupValue maintainRollupValue(RollupValue rollupValue) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRollupValue: rollupValueId = " + rollupValue.getId());
        }
        rollupValue.setSiteName(getSiteName());
        return (RollupValue)insertOrUpdate(rollupValue, "ROLLUP_VALUE");
    }

    @Override
    public RollupValue readRollupValueById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRollupValueById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (RollupValue)getSqlMapClientTemplate().queryForObject("SELECT_ROLLUP_VALUE_BY_ID", params);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<RollupValue> readRollupValuesByAttributeSeriesAndConstituentId(Long attributeId, Long seriesId, Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRollupValuesByAttributeSeriesAndConstituentId: attributeId = " + attributeId + " constituentId="+constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("attributeId", attributeId);
        params.put("seriesId", seriesId);
        params.put("groupByValue", ""+constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_ROLLUP_VALUES_BY_ATTRIBUTE_SERIES_AND_GROUPBYVALUE", params);
    }
    
	@Override
    public void deleteRollupValuesForAttributeSeries(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date startDate, Date endDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteRollupValuesForAttributeSeries: attribute id = " + ra.getId() + " series id = "+rs.getId());
        }
        Map<String, Object> params = setupParams();
        params.put("groupByValue", groupByValue);
        params.put("rollupSeriesId", rs.getId());
        params.put("rollupAttributeId", ra.getId());
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        getSqlMapClientTemplate().delete("ROLLUP_DELETE_ROLLUP_VALUES_FOR_ATTRIBUTE_SERIES", params);
    }

	@Override
    public void insertRollupDimensionValues(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date startDate, Date endDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("updateRollupValue: attribute id = " + ra.getId() + " series id = "+rs.getId());
        }
        Map<String, Object> params = setupParams();
        params.put("groupByValue", groupByValue);
        params.put("rollupSeriesId", rs.getId());
        params.put("rollupAttributeId", ra.getId());
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("fieldName", ra.getFieldName());
        params.put("customFieldName", ra.getCustomFieldName());
        String stat = ra.getRollupStatType();
        if (!stat.startsWith("ROLLUP_")) {
        	logger.error("Invalid stat type "+stat);
        	return;
        }
        if (ra.getFieldName() != null) {
        	oneWord(ra.getFieldName());
        }
        if (ra.getFieldName() != null) {
        	oneWord(ra.getCustomFieldName());
        }
        getSqlMapClientTemplate().insert(stat, params);
    }
    
}