package com.orangeleap.tangerine.service.customization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

@Service("dashboardService")
public class DashboardServiceImpl extends AbstractTangerineService implements DashboardService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "dashboardDAO")
    private DashboardDao dashboardDao;
    
    @Override
    public List<DashboardItem> getDashboard() {
        if (logger.isDebugEnabled()) {
            logger.debug("getDashboard");
        }
        return dashboardDao.getDashboard();
    }

    @Override
    public DashboardData getDashboardQueryContent(DashboardItem item) {
       
    	if (logger.isDebugEnabled()) {
            logger.debug("getDashboardContent "+item.getTitle());
        }
        
    	// Populate data structures for google js graphing library
        DashboardData data = new DashboardData();
        data.setTitle(item.getTitle());
    	data.getColumnTitles().add("");
    	data.getColumnTypes().add("string");
    	data.setGraphType(item.getType());
        for (DashboardItemDataset ds : item.getDatasets()) {
        	
        	data.getColumnTitles().add(ds.getLabel());
        	data.getColumnTypes().add("number");
        	
        	List<DashboardItemDataValue> values = dashboardDao.getDashboardQueryResults(ds);
        	
        	List<BigDecimal> datapointlist = new ArrayList<BigDecimal>();
        	List<String> labellist = new ArrayList<String>();
        	for (DashboardItemDataValue value : values) {
        		datapointlist.add(value.getDataValue());
        		labellist.add(value.getLabel());
        	}
        	if (data.getRowData().size() == 0) data.getRowLabels().addAll(labellist);
        	data.getRowData().add(datapointlist);
        	
        }
        
        return data;
    }

    
}