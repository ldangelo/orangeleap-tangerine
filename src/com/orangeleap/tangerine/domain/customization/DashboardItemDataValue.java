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

import java.math.BigDecimal;
import java.math.BigInteger;

// Result of dashboard item query
public class DashboardItemDataValue {
	
	private String label;
	private BigDecimal labelValue;
	private BigDecimal dataValue;
	
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setLabelValue(BigDecimal labelValue) {
		this.labelValue = labelValue;
	}
	public BigDecimal getLabelValue() {
		return labelValue;
	}
	public void setDataValue(BigDecimal dataValue) {
		this.dataValue = dataValue;
	}
	public BigDecimal getDataValue() {
		return dataValue;
	}
	
}
