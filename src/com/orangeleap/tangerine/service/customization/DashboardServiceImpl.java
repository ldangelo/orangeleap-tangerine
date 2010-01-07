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

package com.orangeleap.tangerine.service.customization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.DashboardDao;
import com.orangeleap.tangerine.domain.customization.DashboardData;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("dashboardService")
public class DashboardServiceImpl extends AbstractTangerineService implements DashboardService {

    public static final int DATA_POINT_LIMIT = 12;

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "dashboardDAO")
    private DashboardDao dashboardDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    
	@Override
	public List<DashboardItem> getAllDashboardItems() {
		return dashboardDao.getDashboard();
	}

	@Override
	public DashboardItem maintainDashboardItem(DashboardItem item) {
		return dashboardDao.maintainDashboardItem(item);
	}

	@Override
	public void deleteDashboardItemById(Long id) {
		dashboardDao.deleteDashboardItemById(id);
	}
    
    @Override
    public List<DashboardItem> getDashboard() {
        if (logger.isTraceEnabled()) {
            logger.trace("getDashboard");
        }

        List<DashboardItem> list = dashboardDao.getDashboard();

        List<String> roles = tangerineUserHelper.lookupUserRoles();

        filterForRole(list, roles);

        return list;
    }

    private void filterForRole(List<DashboardItem> rows, List<String> roles) {
        Iterator<DashboardItem> it = rows.iterator();
        while (it.hasNext()) {
            DashboardItem di = it.next();
            String itemroles = di.getRoles();
            if (itemroles != null) {
                boolean ok = false;
                for (String userrole : roles) {
                    if (itemroles.contains(userrole)) {
                        ok = true;
                        di.setUrl(userrole+"/"+di.getUrl()); // This allows having a different directory permission on the role-specific reports in the guru.
                        break;
                    }
                }
                if (!ok) it.remove();
            }
        }
    }


    @Override
    public DashboardData getDashboardQueryContent(DashboardItem item) {

        if (logger.isTraceEnabled()) {
            logger.trace("getDashboardContent " + item.getTitle());
        }

        // Populate data structures for google js graphing library
        DashboardData data = new DashboardData();
        data.setGraphType(item.getType());
        data.setTitle(item.getTitle());
        data.setUrl(item.getUrl());
        data.getColumnTitles().add("");
        data.getColumnTypes().add("string");

        Map<Integer, List<DashboardItemDataValue>> labelValues = new TreeMap<Integer, List<DashboardItemDataValue>>();
        Map<Integer, List<DashboardItemDataValue>> labelData = new TreeMap<Integer, List<DashboardItemDataValue>>();
        for (int i = 0; i < item.getDatasets().size(); i++) {

            DashboardItemDataset ds = item.getDatasets().get(i);

            data.getColumnTitles().add(ds.getLabel());
            data.getColumnTypes().add("number");

            // Some data points may be missing from some datasets
            List<DashboardItemDataValue> values = dashboardDao.getDashboardQueryResults(ds, tangerineUserHelper.lookupUserId());
            if (values.size() > DATA_POINT_LIMIT) {
                values.subList(DATA_POINT_LIMIT, values.size()).clear();
            }

            // Sort data points by label value
            for (DashboardItemDataValue value : values) {
                Integer key = (int) value.getLabelValue().doubleValue();
                List<DashboardItemDataValue> labellist = labelValues.get(key);
                if (labellist == null) {
                    labellist = new ArrayList<DashboardItemDataValue>();
                    labelValues.put(key, labellist);
                }
                labellist.add(value);

                List<DashboardItemDataValue> datalist = labelData.get(i);
                if (datalist == null) {
                    datalist = new ArrayList<DashboardItemDataValue>();
                    labelData.put(i, datalist);
                }
                datalist.add(value);
            }

        }

        // Set labels
        for (Map.Entry<Integer, List<DashboardItemDataValue>> me : labelValues.entrySet()) {
            List<DashboardItemDataValue> list = me.getValue();
            DashboardItemDataValue dv = list.get(0);
            data.getRowLabels().add(dv.getLabel());
        }

        // Set datapoints
        for (int i = 0; i < item.getDatasets().size(); i++) {
            List<BigDecimal> datapoints = new ArrayList<BigDecimal>();
            data.getRowData().add(datapoints);
            for (int j = 0; j < data.getRowLabels().size(); j++) {
                BigDecimal value = null;
                String label = data.getRowLabels().get(j);
                if (label != null && labelData.get(i) != null) {
                    for (DashboardItemDataValue dv : labelData.get(i)) {
                        if (label.equals(dv.getLabel())) {
                            value = dv.getDataValue();
                            break;
                        }
                    }
                }
                if (value == null) {
                    value = new BigDecimal(0);
                }
                datapoints.add(value);
            }
        }

        return data;
    }


}