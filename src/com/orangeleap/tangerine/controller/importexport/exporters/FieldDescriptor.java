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

package com.orangeleap.tangerine.controller.importexport.exporters;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;

public class FieldDescriptor {
	
	// Type determines order for sorting
	public static final int NATIVE = 0;
	public static final int CUSTOM = 1;
	// Sub-entities
	public static final int ADDRESS = 2;
	public static final int PHONE = 3;
	public static final int EMAIL = 4;
	public static final int PAYMENTTYPE = 5;
  
	
	private String name;
    private int type;
    private FieldDefinition fieldDefinition;
    private boolean disabled = false; 
    
	public static String toInitialUpperCase(String s) {
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}

	public static String toInitialLowerCase(String s) {
		return s.substring(0,1).toLowerCase()+s.substring(1);
	}

	public FieldDescriptor(String name, int type, FieldDefinition fieldDefinition) {
    	this.name = name;
    	this.type = type;
    	this.fieldDefinition = fieldDefinition;
    }
     
	public String getName() {
		return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public int getType() {
		return type;
	}
	
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
	
	// Create an export field name for internal name.
	public String getExportFieldNameForInternalName() {
		if (name.startsWith("constituent.")) return name.substring(name.indexOf('.')+1);
		return getExportFieldNameForMap(name);
	}
	
	// Create internal name for import field name.
	public static String getInternalNameForImportFieldName(String importFieldName) {
		return importFieldName.replace("customField[", "customFieldMap[");
	}
	
	// Create an export field name for a mapped field.
	private String getExportFieldNameForMap(String name) {
		return name.replace("customFieldMap[", "customField[");
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}
	
	public String toString() {
		return getName();
	}
		
	
}
