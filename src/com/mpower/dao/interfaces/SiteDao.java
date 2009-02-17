package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.Site;
import com.mpower.domain.model.customization.EntityDefault;
import com.mpower.type.EntityType;

public interface SiteDao {

	public Site readSite(String siteName);

	public List<EntityDefault> readEntityDefaults(String siteName, List<EntityType> entityTypes);
	
	public List<Site> readSites();

	public Site createSite(String siteName, String merchantNumber, Site parentSite);
	
    public EntityDefault createEntityDefault(EntityDefault entityDefault);
}
