package com.orangeleap.tangerine.service.customization;

import java.util.Locale;

import com.orangeleap.tangerine.type.MessageResourceType;

public interface MessageService {
	public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale locale);
}
