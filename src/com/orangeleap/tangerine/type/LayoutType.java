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

package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum LayoutType {
	ONE_COLUMN,
	ONE_COLUMN_HIDDEN,
	TWO_COLUMN,
	GRID,
	GRID_HIDDEN_ROW,
	DISTRIBUTION_LINE_GRID,
	ADJUSTED_DISTRIBUTION_LINE_GRID,
	GIFT_IN_KIND_GRID;

	public static boolean isMultiColumnType(LayoutType layoutType) {
		return TWO_COLUMN.equals(layoutType);
	}

	public static boolean isSingleColumnType(LayoutType layoutType) {
		return ONE_COLUMN.equals(layoutType) || ONE_COLUMN_HIDDEN.equals(layoutType);
	}

	public static boolean isColumnType(LayoutType layoutType) {
		return ONE_COLUMN.equals(layoutType) || ONE_COLUMN_HIDDEN.equals(layoutType) ||
				TWO_COLUMN.equals(layoutType);
	}

	public static boolean isGridType(LayoutType layoutType) {
		return GRID.equals(layoutType) || GRID_HIDDEN_ROW.equals(layoutType) ||
				DISTRIBUTION_LINE_GRID.equals(layoutType) || GIFT_IN_KIND_GRID.equals(layoutType) ||
				ADJUSTED_DISTRIBUTION_LINE_GRID.equals(layoutType);
	}
}
