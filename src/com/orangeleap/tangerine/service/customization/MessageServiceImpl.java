/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.customization;

import com.orangeleap.tangerine.dao.MessageDao;
import com.orangeleap.tangerine.domain.customization.MessageResource;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "messageDAO")
    private MessageDao messageDao;

    @Resource(name = "messageResourceCache")
    private Cache messageResourceCache;

    @Autowired
    private TangerineUserHelper tangerineUserHelper;

	@SuppressWarnings("unchecked")
    @Override
    public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale language) {
        if (logger.isTraceEnabled()) {
            logger.trace("lookupMessage: messageResourceType = " + messageResourceType + " messageKey = " + messageKey + " language = " + language);
        }
	    final String key = tangerineUserHelper.lookupUserSiteName();
		Map<String, MessageResource> messageResourceMap;
		final Element element = messageResourceCache.get(key);

		if (element == null) {
		    messageResourceMap = loadAllMessageResources();
		    messageResourceCache.put(new Element(key, messageResourceMap));
	    }
	    else {
		    messageResourceMap = (Map<String, MessageResource>) element.getValue();
	    }
		final MessageResource messageResource = messageResourceMap.get(getResourceKey(messageResourceType, messageKey, language));

		String messageText = null;
		if (messageResource != null) {
			messageText = messageResource.getMessageValue();
		}
		return messageText;
    }

	public Map<String, MessageResource> loadAllMessageResources() {
		final Map<String, MessageResource> resourceMap = new HashMap<String, MessageResource>();
		List<MessageResource> resources = messageDao.readAllMessages();
		if (resources != null) {
			for (MessageResource thisResource : resources) {
				String key = getResourceKey(thisResource.getMessageResourceType(), thisResource.getMessageKey(), thisResource.getLocale());
				if (resourceMap.get(key) != null) {
					MessageResource existingResource = resourceMap.get(key);

					// Only replace the existingResource if there is no site name and thisResource has a site name
					if (thisResource.getSite() != null && StringUtils.hasText(thisResource.getSite().getName()) &&
							(existingResource.getSite() == null || ! StringUtils.hasText(existingResource.getSite().getName()))) {
						resourceMap.put(key, thisResource);
					}
				}
				else {
					resourceMap.put(key, thisResource);
				}
			}
		}
		return resourceMap;
	}

	private String getResourceKey(MessageResourceType type, String messageKey, Locale locale) {
		return new StringBuilder(type.name()).append(".").append(messageKey).append(".").append(locale.toString()).toString();
	}
}
