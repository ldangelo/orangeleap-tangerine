package com.orangeleap.tangerine.test.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.customization.DashboardData;
import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.service.customization.DashboardService;
import com.orangeleap.tangerine.test.BaseTest;

public class DashboardServiceImplTest extends BaseTest {

    @Autowired
    private DashboardService dashboardService;
    
//    @Test
//    public void testDashboardExecution() throws Exception {
//    	List<DashboardItem> items = dashboardService.getDashboard();
//		assert items != null && items.size() > 0;
//    	for (DashboardItem item : items) {
//    		DashboardData data = dashboardService.getDashboardQueryContent(item);
//    		assert data.getTitle() != null && data.getTitle().length() > 0;
//    	}
//    }

}
