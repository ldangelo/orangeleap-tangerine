package com.mpower.service.customization;

import java.util.Locale;

import com.mpower.type.MessageResourceType;

public interface MessageService {
	public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale locale);
}
