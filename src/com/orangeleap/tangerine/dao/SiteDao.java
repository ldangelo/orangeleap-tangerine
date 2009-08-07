/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.EntityDefault;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.type.EntityType;

import java.util.List;

public interface SiteDao {

    public Site createSite(Site site);

    public Site readSite(String siteName);
	
	public List<Site> readSites();

	public int updateSite(Site site);

	EntityDefault createEntityDefault(EntityDefault entityDefault);

	List<EntityDefault> readEntityDefaults(EntityType entityType);

	int updateEntityDefault(EntityDefault entityDefault);

	EntityDefault readEntityDefaultByTypeName(EntityType entityType, String entityFieldName);
}
