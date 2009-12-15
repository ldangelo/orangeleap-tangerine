package com.orangeleap.tangerine.type;

public enum RuleEventNameType {
	
	CONSTITUENT_SAVE("constituent-save"),
	GIFT_SAVE("gift-save"),
	TOUCHPOINT_SAVE("touchpoint-save"),
	EMAIL("email"),
	PAYMENT_PROCESSING("payment-processing"),
	SCHEDULED_DAILY("scheduled-daily"),
	SCHEDULED_WEEKLY("scheduled-weekly"),
	SCHEDULED_MONTHLY("scheduled-monthly"),
	SCHEDULED_ONE_TIME("scheduled-one-time")
	;
	
	private String type;

	private RuleEventNameType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
