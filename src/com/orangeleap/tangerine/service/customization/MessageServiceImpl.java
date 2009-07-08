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
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

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

    @Override
    public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale language) {
        if (logger.isTraceEnabled()) {
            logger.trace("lookupMessage: messageResourceType = " + messageResourceType + " messageKey = " + messageKey + " language = " + language);
        }

        String key = buildKey(messageResourceType, messageKey, language);
        Element ele = messageResourceCache.get(key);
        String msg = null;

        if (ele == null) {
            msg = messageDao.readMessage(messageResourceType, messageKey, language);
            // use the String "<NULL>" to represent null
            msg = (msg == null ? "<NULL>" : msg);
            ele = new Element(key, msg);
            messageResourceCache.put(ele);
        } else {
            msg = (String) ele.getValue();
        }

        // if we substituted a real null with the <NULL> token, change it back to a real null
        return (msg.equals("<NULL>") ? null : msg);
    }

    private String buildKey(MessageResourceType type, String key, Locale locale) {

        String site = tangerineUserHelper.lookupUserSiteName();
        site = (site == null ? "DEFAULT" : site);
        StringBuilder builder = new StringBuilder(site);
        builder.append(".").append(type.name()).append(".").append(key).append(".").append(locale.getDisplayLanguage());

        return builder.toString();
    }
}
