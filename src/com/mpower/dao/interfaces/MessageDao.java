package com.mpower.dao.interfaces;

import java.util.Locale;

import com.mpower.type.MessageResourceType;

public interface MessageDao {
	public String readMessage(MessageResourceType messageResourceType, String messageKey, Locale locale);
}
