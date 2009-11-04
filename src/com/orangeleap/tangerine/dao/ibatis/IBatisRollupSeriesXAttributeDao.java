package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;

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
  
	@Override
    public void maintainRollupSeriesXAttribute(List<RollupSeriesXAttribute> rollupSeriesXAttributes) {
		// TODO replace all based on rollupAttribute
    }
    
}