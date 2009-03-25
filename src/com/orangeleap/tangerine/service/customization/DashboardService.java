package com.orangeleap.tangerine.service.customization;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.DashboardData;
import com.orangeleap.tangerine.domain.customization.DashboardItem;

public interface DashboardService {

    public List<DashboardItem> getDashboard();
    
    public DashboardData getDashboardQueryContent(DashboardItem item);
	
}
