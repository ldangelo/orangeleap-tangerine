package com.mpower.service.customization;

import java.util.Locale;

import com.mpower.domain.type.MessageResourceType;
import com.mpower.entity.Site;

public interface MessageService {
	public String lookupMessage(Site site, MessageResourceType messageResourceType, String messageKey, Locale locale);
}
