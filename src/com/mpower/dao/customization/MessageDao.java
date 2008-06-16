package com.mpower.dao.customization;

import java.util.Locale;

import com.mpower.domain.type.MessageResourceType;

public interface MessageDao {
	public String readMessage(Long siteId, MessageResourceType messageResourceType, String messageKey, Locale locale);
}
