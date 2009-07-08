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

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.DashboardDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    @SuppressWarnings("unchecked")
    @Override
    public List<DashboardItem> getDashboard() {
        if (logger.isTraceEnabled()) {
            logger.trace("getDashboard");
        }
        Map<String, Object> params = setupParams();

        List<DashboardItem> rows = getSqlMapClientTemplate().queryForList("SELECT_DASHBOARD_ITEM", params);

        filterForSiteOverride(rows);

        return rows;
    }

    private void filterForSiteOverride(List<DashboardItem> rows) {
        Iterator<DashboardItem> it = rows.iterator();
        while (it.hasNext()) {
            DashboardItem di = it.next();
            Site site = di.getSite();
            if (site == null) {
                Iterator<DashboardItem> it2 = rows.iterator();
                while (it2.hasNext()) {
                    DashboardItem di2 = it2.next();
                    if (di2.getSite() != null && di2.getOrder().equals(di.getOrder())) {
                        it.remove();
                        break;
                    }
                }
            } else {
                if ("None".equalsIgnoreCase(di.getType())) {
                    it.remove();
                }
            }
        }
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
