package com.mpower.dao;

import java.util.List;

import com.mpower.domain.type.EntityType;
import com.mpower.entity.Site;
import com.mpower.entity.customization.EntityDefault;

public interface SiteDao {

	public Site readSite(Long siteId);

	public List<EntityDefault> readEntityDefaults(Long siteId, List<EntityType> entityTypes);
}
