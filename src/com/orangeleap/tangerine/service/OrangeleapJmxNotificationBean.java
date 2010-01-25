package com.orangeleap.tangerine.service;

// internal interface (non-JMX)
public interface OrangeleapJmxNotificationBean extends OrangeleapJmxNotification {

	public final static Long OK = 0L;
	public final static Long ERROR = 1L;

	public final static String INIT = "initialization";
	public final static String TOTAL = "total";
	public final static String LOGINS = "logins";
	public final static String AUTHORIZE = "Authorize";
	public final static String CAPTURE = "Capture";
	public final static String AUTHORIZE_AND_CAPTURE = "AuthorizeAndCapture";
	public final static String ACH = "ACH";

	public final static String ORBITAL_PAYMENT_ERROR = "orbitalpaymenterror";
	public final static String ORBITAL_PAYMENT_STATUS = "orbitalpaymentstatus";

	public final static String ECHEX_PAYMENT_ERROR = "echexpaymenterror";
	public final static String ECHEX_PAYMENT_STATUS = "echexpaymentstatus";

	public final static String RULE_CACHE_ATTEMPTS = "ruleCacheAttempts";
	public final static String RULE_CACHE_HITS = "ruleCacheHits";

	public final static String DATABASE_CONNECTIONS_IN_USE = "databaseConnectionsInUse";
	
	
	public void publishNotification(String type, String message);

	public void incrementStatCount(String sitename, String statname);
	public void incrementStat(String sitename, String statname, Long amount);
	public void setStat(String sitename, String statname, Long amount);

 
}
