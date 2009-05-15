package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.DashboardItem;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataValue;
import com.orangeleap.tangerine.domain.customization.DashboardItemDataset;

public interface DashboardDao {
	
	public List<DashboardItem> getDashboard();
	public List<DashboardItemDataValue> getDashboardQueryResults(DashboardItemDataset ds, Long userid);
	
}
