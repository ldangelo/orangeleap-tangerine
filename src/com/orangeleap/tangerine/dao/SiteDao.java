package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.type.EntityType;

public interface SiteDao {

    public Site createSite(Site site);

    public Site readSite(String siteName);
	
	public List<Site> readSites();

	public int updateSite(Site site);
	
    public EntityDefault createEntityDefault(EntityDefault entityDefault);

    public List<EntityDefault> readEntityDefaults(List<EntityType> entityTypes);
    
    public int updateEntityDefault(EntityDefault entityDefault);
}
