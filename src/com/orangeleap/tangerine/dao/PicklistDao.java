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

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.type.EntityType;

public interface PicklistDao {

    public Picklist readPicklistById(Long picklistId);
    
    public Picklist readPicklistByNameId(String picklistNameId);

	public Picklist maintainPicklist(Picklist picklist);

	public List<Picklist> listPicklists();

    public Picklist readPicklistByFieldName(String fieldName, EntityType entityType);

    public PicklistItem readPicklistItemById(Long picklistItemId);

	public PicklistItem maintainPicklistItem(PicklistItem picklistItem);
	
	public PicklistItem readPicklistItemByName(String picklistNameId, String picklistItemName);
	
	public PicklistItem readPicklistItemByDefaultValue(String picklistNameId, String defaultDisplayValue);
}
