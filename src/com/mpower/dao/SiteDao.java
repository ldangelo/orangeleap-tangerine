package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Site;
import com.mpower.domain.customization.EntityDefault;
import com.mpower.type.EntityType;

public interface SiteDao {

	public Site readSite(Long siteId);

	public List<EntityDefault> readEntityDefaults(Long siteId, List<EntityType> entityTypes);
}
