package com.mpower.controller.importexport.exporters;

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
    
    public FieldDescriptor(String name, int type) {
    	this.name = name;
    	this.setType(type);
    }
     
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
}
