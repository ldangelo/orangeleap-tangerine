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

package com.orangeleap.tangerine.domain.customization;

public class DashboardItemDataset {

	private Long id;
	private Long itemId;
	private Integer num;
	private String label;
	private String sqltext;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getNum() {
		return num;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}
	public String getSqltext() {
		return sqltext;
	}
	
}
