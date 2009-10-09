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

package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;

import java.util.List;

public interface PicklistItemService {

    List<Picklist> listPicklists();

    Picklist getPicklist(String picklistNameId);

    Picklist getPicklistById(Long picklistId);

    Picklist maintainPicklist(Picklist picklist);

    PicklistItem maintainPicklistItem(PicklistItem picklistItem);

    void maintainPicklistItems(Picklist picklist, List<PicklistItem> items);

    PicklistItem getPicklistItem(String picklistId, String picklistItemName);

    PicklistItem getPicklistItem(Long picklistItemId);

    List<PicklistItem> getPicklistItems(String picklistNameId, String picklistItemName, String description, Boolean showInactive);

    public PicklistItem getPicklistItemByDefaultDisplayValue(String picklistNameId, String defaultDisplayValue);

    public List<PicklistItem> findCodeByDescription(String picklistNameId, String description, Boolean showInactive);

    public List<PicklistItem> findCodeByValue(String picklistNameId, String value, Boolean showInactive);

    void removeInvalidItems(List<PicklistItem> items);
}
