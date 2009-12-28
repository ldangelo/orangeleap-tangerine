package com.orangeleap.tangerine.service.impl;

import java.util.Map;
import java.util.TreeMap;

import javax.management.Notification;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.service.OrangeleapJmxNotificationBean;
import com.orangeleap.tangerine.util.OLLogger;

import edu.emory.mathcs.backport.java.util.Collections;

@SuppressWarnings("unchecked")
@Service("OrangeleapJmxNotificationBean")
public class OrangeleapJmxNotificationBeanImpl implements OrangeleapJmxNotificationBean, NotificationPublisherAware {
	
	private NotificationPublisher publisher;
	private Map<String, Map<String, Long>> counts = Collections.synchronizedMap(new TreeMap<String, Map<String, Long>>());

	@Override
	public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        publisher = notificationPublisher;
    }

	@Override
	public void publishNotification(String type, String message) {
		if (publisher != null) publisher.sendNotification(new Notification(type, this, 0, System.currentTimeMillis(), message));
	}
	
	@Override
	public synchronized void incrementStatCount(String sitename, String statname) {
		incrementStat(sitename, statname, 1L);
	}
	
	@Override
	public synchronized void incrementStat(String sitename, String statname, Long amount) {
		adjustStat(sitename, statname, amount, false);
	}
	
	@Override
	public void setStat(String sitename, String statname, Long amount) {
		adjustStat(sitename, statname, amount, true);
	}

	private synchronized void adjustStat(String sitename, String statname, Long amount, boolean set) {
		Map<String, Long> map = counts.get(sitename);
		if (map == null) {
			map = Collections.synchronizedMap(new TreeMap<String, Long>());
			counts.put(sitename, map);
		}
		Long count = map.get(statname);
		if (count == null) count = new Long(0);
		if (set) {
			map.put(statname, amount); 
		} else {
			map.put(statname, count + amount);
		}
		if (!TOTAL.equals(sitename)) adjustStat(TOTAL, statname, amount, set);
	}

	@Override
	public synchronized void resetStats() {
		counts = Collections.synchronizedMap(new TreeMap<String, Map<String, Long>>());
	}
	
	@Override
	public Map<String, Long> getSiteCounts(String sitename) {
		return counts.get(sitename);
	}

	@Override
	public Map<String, Map<String, Long>> getAllSiteCounts() {
 		return counts;
	}

	@Override
	public Long getTotalLoginCount() {
		return getTotalCount(LOGINS);
	}
	
	@Override
	public Long getTotalAuthorizeCount() {
		return getTotalCount(AUTHORIZE);
	}
	
	@Override
	public Long getTotalCaptureCount() {
		return getTotalCount(CAPTURE);
	}
	
	@Override
	public Long getTotalAuthorizeAndCaptureCount() {
		return getTotalCount(AUTHORIZE_AND_CAPTURE);
	}
	
	@Override
	public Long getTotalOrbitalStatus() {
		return getTotalCount(ORBITAL_PAYMENT_STATUS);
	}
	
	@Override
	public Long getTotalEchexStatus() {
		return getTotalCount(ECHEX_PAYMENT_STATUS);
	}
	
	@Override
	public Long getMemoryUsedPercent() {
		double pct = (OLLogger.getFreeMemory() * 1.0f) / Runtime.getRuntime().maxMemory();
		return (Long)Math.round((100d - pct) * 100);
	}
	
	private Long getTotalCount(String stat) {
		Map<String, Long> map = counts.get(TOTAL);
		if (map == null) return 0L;
		Long value = map.get(stat);
		if (value == null) value = 0L;
		return value;
	}

}
