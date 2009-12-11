package com.orangeleap.tangerine.type;

public enum RuleEventType {
	
	GIFT_SAVE("gift-save"),
	CONSTITUENT_SAVE("constituent-save"),
	TOUCHPOINT_SAVE("touchpoint-save")
	;
	
	private String type;

	private RuleEventType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
