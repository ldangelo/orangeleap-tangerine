package com.orangeleap.tangerine.dao;

import java.util.Locale;

import com.orangeleap.tangerine.type.MessageResourceType;

public interface MessageDao {
	public String readMessage(MessageResourceType messageResourceType, String messageKey, Locale locale);
}
