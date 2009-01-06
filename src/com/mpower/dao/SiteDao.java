package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;

public interface SiteDao {

	public Site readSite(String siteName);

	public List<EntityDefault> readEntityDefaults(String siteName, List<EntityType> entityTypes);
	
	public List<Site> readSites();

	public Site createSite(String siteName, String merchantNumber, Site parentSite);

}
