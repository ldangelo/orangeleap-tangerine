package com.orangeleap.tangerine.type;

public enum RuleObjectType {
	
	SITE("site"),
	CONSTITUENT("constituent"),
	GIFT("gift"),
	TOUCHPOINT("touchpoint"),
	AS_OF_DATE("asOfDate");
	
	private String type;

	private RuleObjectType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
