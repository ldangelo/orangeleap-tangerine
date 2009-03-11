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

	public int getType() {
		return type;
	}
	
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}
	
	public boolean isMap() {
		return isMap(name);
	}

	public boolean isDependentField() {
		return isDependentField(name);
	}

	public String getMapType() {
		return getMapType(name);
	}
	
	public String getKey() {
		return getKey(name);
	}
	
	public String getSubField() {
		return getSubField(name);
	}
	
	public String getDependentObject() {
		return getDependentObject(name);
	}
	
	public String getDependentField() {
		return getDependentField(name);
	}
	
	// Create an export field name for internal name.
	public String getExportFieldNameForInternalName() {
		return getExportFieldNameForMap(name);
	}
	
	// Create internal name for import field name.
	public static String getInternalNameForImportFieldName(String importFieldName) {
		if (
				importFieldName.startsWith("address[")
				|| importFieldName.startsWith("phone[")
				|| importFieldName.startsWith("email[")
				) {
			int i = importFieldName.indexOf("[");
			int j = importFieldName.indexOf("]");
			if (i < 0 || j < 0 || i > j) {
                return "";
            }
		    return importFieldName.substring(0, i) + "Map[" + getKey(importFieldName) + "]." + importFieldName.substring(j + 1);
		} else {
			return importFieldName;
		}
	}

		
	
	
	private boolean isMap(String name) {
		return name.contains("Map[");
	}

	private boolean isDependentField(String name) {
		return name.contains(".");
	}

	private String getMapType(String name) {
		int i = name.indexOf("Map[");
		if (i < 0 ) {
            return "";
        }
		String type = name.substring(0,i);
		return toInitialUpperCase(type);
	}
	
	private static String getKey(String name) {
		int i = name.indexOf("[");
		int j = name.indexOf("]");
		if (i < 0 || j < 0 || i > j) {
            return "";
        }
		return name.substring(i + 1, j);
	}
	
	private String getSubField(String name) {
		int j = name.indexOf("]");
		if (j < 0) {
            return "";
        }
		return name.substring(j+2);
	}
	
	private String getDependentObject(String name) {
		int j = name.indexOf(".");
		if (j < 0) {
            return "";
        }
		return name.substring(0,j);
	}
	
	private String getDependentField(String name) {
		int j = name.indexOf(".");
		if (j < 0) {
            return "";
        }
		return name.substring(j+1);
	}
	
	// Create an export field name for a mapped field.
	private String getExportFieldNameForMap(String name) {
		if (!isMap(name)) {
            return name;
        }
		return toInitialLowerCase(getMapType(name)) + "[" + getKey(name) + "]" + getSubField(name);
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isDisabled() {
		return disabled;
	}
		
	
}
