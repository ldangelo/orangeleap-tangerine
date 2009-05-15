package com.orangeleap.tangerine.service.customization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.DashboardDao;
import com.orangeleap.tangerine.domain.customization.DashboardData;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("dashboardService")
public class DashboardServiceImpl extends AbstractTangerineService implements DashboardService {

	public static final int DATA_POINT_LIMIT = 12;
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "dashboardDAO")
    private DashboardDao dashboardDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
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
            logger.trace("getDashboardContent "+item.getTitle());
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
        	List<DashboardItemDataValue> values = dashboardDao.getDashboardQueryResults(ds);
        	if (values.size() > DATA_POINT_LIMIT) {
                values.subList(DATA_POINT_LIMIT, values.size()).clear();
            }
        	
        	// Sort data points by label value
        	for (DashboardItemDataValue value : values) {
        		Integer key = (int)value.getLabelValue().doubleValue();
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
        for (Map.Entry<Integer, List<DashboardItemDataValue>> me: labelValues.entrySet())  {
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