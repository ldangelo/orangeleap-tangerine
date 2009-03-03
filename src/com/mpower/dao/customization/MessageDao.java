package com.mpower.dao.customization;

import java.util.Locale;

import com.mpower.type.MessageResourceType;

@Deprecated
public interface MessageDao {
	public String readMessage(String siteName, MessageResourceType messageResourceType, String messageKey, Locale locale);
}
