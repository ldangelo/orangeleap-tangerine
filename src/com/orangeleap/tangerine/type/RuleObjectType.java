package com.orangeleap.tangerine.type;

public enum RuleObjectType {
	
	SITE("site"),
	CONSTITUENT("constituent"),
	GIFT("gift"),
	TOUCHPOINT("touchpoint");
	
	private String type;

	private RuleObjectType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
