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

package com.orangeleap.tangerine.controller.importexport;

import java.util.Date;

public class ImportRequest {
	
	private String entity;
	private Date ncoaDate;
	private Date cassDate;
	private boolean convertToProperCase = true;
	
	
	
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getEntity() {
		return entity;
	}
	public void setNcoaDate(Date ncoaDate) {
		this.ncoaDate = ncoaDate;
	}
	public Date getNcoaDate() {
		return ncoaDate;
	}
	public void setCassDate(Date caasDate) {
		this.cassDate = caasDate;
	}
	public Date getCassDate() {
		return cassDate;
	}
	public void setConvertToProperCase(boolean convertToProperCase) {
		this.convertToProperCase = convertToProperCase;
	}
	public boolean isConvertToProperCase() {
		return convertToProperCase;
	}
	
}
