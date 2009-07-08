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

import java.util.Map;
import java.util.TreeMap;


public class RelationshipCustomField {
	
	private CustomField customField;
	private int index;
	private Map<String, Object> relationshipCustomizations;
	
	public CustomField getCustomField() {
		return customField;
	}
	
	public void setCustomField(CustomField customField) {
		this.customField = customField;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Map<String, Object> getRelationshipCustomizations() {
		return relationshipCustomizations;
	}
	
	public void setRelationshipCustomizations(Map<String, Object> relationshipCustomizations) {
		this.relationshipCustomizations = relationshipCustomizations;
	}
	
	public void addRelationshipCustomization(String key, Object value) {
		if (this.relationshipCustomizations == null) {
			relationshipCustomizations = new TreeMap<String, Object>();
		}
		relationshipCustomizations.put(key, value);
	}
}
