package com.orangeleap.tangerine.service;

// internal interface (non-JMX)
public interface OrangeleapJmxNotificationBean extends OrangeleapJmxNotification {
	
	public final static String GLOBAL = "global";
	public final static String TOTAL = "total";
	public final static String LOGINS = "logins";
	public final static String AUTHORIZE = "Authorize";
	public final static String CAPTURE = "Capture";
	public final static String AUTHORIZE_AND_CAPTURE = "AuthorizeAndCapture";

	public final static String ORBITAL_PAYMENT_ERROR = "orbitalpaymenterror";
	public final static String ORBITAL_PAYMENT_STATUS = "orbitalpaymentstatus";


	
	public void publishNotification(String type, String message);

	public void incrementStatCount(String sitename, String statname);
	public void incrementStat(String sitename, String statname, Long amount);
	public void setStat(String sitename, String statname, Long amount);

 
}
