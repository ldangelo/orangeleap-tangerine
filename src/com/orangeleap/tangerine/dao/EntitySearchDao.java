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

import java.util.Locale;

import com.orangeleap.tangerine.web.common.PaginatedResult;


public interface EntitySearchDao {

    public void insertOrUpdateEntitySearchText(String entityType, Long entityId, String searchText);

	public PaginatedResult search(String entityType, String searchText, String sortColumn, String dir, int start, int limit, Locale locale);

}
