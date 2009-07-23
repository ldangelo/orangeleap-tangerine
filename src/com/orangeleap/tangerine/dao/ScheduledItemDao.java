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

import com.orangeleap.tangerine.domain.ScheduledItem;

public interface ScheduledItemDao {

    public ScheduledItem maintainScheduledItem(ScheduledItem scheduledItem);

    public void deleteScheduledItemById(Long scheduledItemId);

    public void deleteSchedule(String sourceEntity, Long sourceEntityId);

    public ScheduledItem readScheduledItemById(Long scheduledItemId);

    public List<ScheduledItem> readScheduledItemsBySourceEntityId(String sourceEntity, Long sourceEntityId);

}