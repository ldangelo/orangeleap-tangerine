package com.orangeleap.tangerine.type;

public enum RuleEventNameType {
	
	GIFT_SAVE("gift-save"),
	CONSTITUENT_SAVE("constituent-save"),
	TOUCHPOINT_SAVE("touchpoint-save"),
	SCHEDULED("scheduled")
	;
	
	private String type;

	private RuleEventNameType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
