package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.DashboardDao;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;

@Repository("dashboardDAO")
public class IBatisDashboardDao extends AbstractIBatisDao implements DashboardDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
  
    @Autowired
    public IBatisDashboardDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<DashboardItem> getDashboard() {
        if (logger.isDebugEnabled()) {
            logger.debug("getDashboard");
        }
        Map<String, Object> params = setupParams();
        // TODO could add filter for logged-in user/roles if desired

        List<DashboardItem> rows = (List<DashboardItem>)getSqlMapClientTemplate().queryForList("SELECT_DASHBOARD_ITEM", params);
        return rows;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DashboardItemDataValue> getDashboardQueryResults(DashboardItemDataset ds) {
        if (logger.isDebugEnabled()) {
            logger.debug("getDashboardQueryResults");
        }
        Map<String, Object> params = setupParams();
        params.put("sql", ds.getSqltext());
        List<DashboardItemDataValue> rows = (List<DashboardItemDataValue>)getSqlMapClientTemplate().queryForList("SELECT_DASHBOARD_ITEM_DATA", params);
        return rows;
	}

	
}
