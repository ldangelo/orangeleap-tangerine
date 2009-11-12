package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.RollupSeriesXAttributeDao;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.util.OLLogger;

/** 
 * Corresponds to the RollupSeriesXAttribute table
 */
@Repository("rollupSeriesXAttributeDAO")
public class IBatisRollupSeriesXAttributeDao extends AbstractIBatisDao implements RollupSeriesXAttributeDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRollupSeriesXAttributeDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
  
	@SuppressWarnings("unchecked")
	@Override
    public List<RollupSeriesXAttribute> selectRollupSeriesForAttribute(Long attributeId) {
        Map<String, Object> params = setupParams();
        params.put("rollupAttributeId", attributeId);
		return getSqlMapClientTemplate().queryForList("SELECT_ROLLUP_SERIES_X_ATTRIBUTE_BY_ATTRIBUTE_ID", params);
	}
    
	@Override
    public void maintainRollupSeriesXAttribute(Long attributeId, List<RollupSeriesXAttribute> rollupSeriesXAttributes) {
		
        Map<String, Object> params = setupParams();
        params.put("rollupAttributeId", attributeId);
        
        getSqlMapClientTemplate().delete("DELETE_ROLLUP_SERIES_X_ATTRIBUTE_BY_ATTRIBUTE_ID", params);
        
        for (RollupSeriesXAttribute rollupSeriesXAttribute : rollupSeriesXAttributes)
        	getSqlMapClientTemplate().insert("INSERT_ROLLUP_SERIES_X_ATTRIBUTE", rollupSeriesXAttribute);
		
    }
    
}