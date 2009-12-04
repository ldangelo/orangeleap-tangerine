package com.orangeleap.tangerine.service.impl;

import java.util.Map;
import java.util.TreeMap;

import javax.management.Notification;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.service.OrangeleapJmxNotificationBean;

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
		Map<String, Long> map = counts.get(sitename);
		if (map == null) {
			map = Collections.synchronizedMap(new TreeMap<String, Long>());
			counts.put(sitename, map);
		}
		Long count = map.get(statname);
		if (count == null) count = new Long(0);
		map.put(statname, count + amount);
		if (!TOTAL.equals(sitename)) incrementStatCount(TOTAL, statname);
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
	
	private Long getTotalCount(String stat) {
		Map<String, Long> map = counts.get(TOTAL);
		if (map == null) return 0L;
		return map.get(stat);
	}

}
