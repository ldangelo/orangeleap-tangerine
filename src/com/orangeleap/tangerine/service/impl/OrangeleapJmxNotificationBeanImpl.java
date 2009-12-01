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
	private Map<String, Long> loginCounts = Collections.synchronizedMap(new TreeMap<String, Long>());
	
	@Override
	public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }

	@Override
	public void publishNotification(String type, String message) {
		this.publisher.sendNotification(new Notification(type, this, 0, System.currentTimeMillis(), message));
	}

	@Override
	public Map<String, Long> getLoginCounts() {
		return loginCounts;
	}
	
}
