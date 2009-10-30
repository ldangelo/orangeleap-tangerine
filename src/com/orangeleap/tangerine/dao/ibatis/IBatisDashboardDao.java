/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.DashboardDao;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;
import com.orangeleap.tangerine.util.OLLogger;

@Repository("dashboardDAO")
public class IBatisDashboardDao extends AbstractIBatisDao implements DashboardDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisDashboardDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public DashboardItem maintainDashboardItem(DashboardItem dashboardItem) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainDashboardItem: dashboardItemId = " + dashboardItem.getId());
        }
        return (DashboardItem)insertOrUpdate(dashboardItem, "DASHBOARD_ITEM");
    }
    
    @Override
    public void deleteDashboardItemById(Long dashboardItemId) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteDashboardItemById: dashboardItemId = " + dashboardItemId);
        }
        Map<String, Object> params = setupParams();
		params.put("id", dashboardItemId);
		getSqlMapClientTemplate().delete("DELETE_DASHBOARD_ITEM_BY_ID", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<DashboardItem> getDashboard() {
        if (logger.isTraceEnabled()) {
            logger.trace("getDashboard");
        }
        Map<String, Object> params = setupParams();

        // Either return default dashboard or guru-type customized one
        List<DashboardItem> rows = getSqlMapClientTemplate().queryForList("SELECT_SITE_DASHBOARD_ITEMS", params);
        if (rows.size() == 0) {
        	rows = getSqlMapClientTemplate().queryForList("SELECT_DEFAULT_DASHBOARD_ITEMS", params);
        }

        return rows;
    }


    @SuppressWarnings("unchecked")
    public List<DashboardItemDataValue> getDashboardQueryResults(DashboardItemDataset ds, Long userid) {
        if (logger.isTraceEnabled()) {
            logger.trace("getDashboardQueryResults");
        }

        String sql = ds.getSqltext();
        if (sql == null) {
            return new ArrayList<DashboardItemDataValue>();
        }
        sql = sql.replaceAll("#siteName#", "'" + this.getSiteName() + "'");
        sql = sql.replaceAll("#userId#", "" + userid);

        Map<String, Object> params = setupParams();
        params.put("sql", sql);
        List<DashboardItemDataValue> rows = getSqlMapClientTemplate().queryForList("SELECT_DASHBOARD_ITEM_DATA", params);
        return rows;
    }



}
