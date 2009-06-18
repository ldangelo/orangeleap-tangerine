package com.orangeleap.tangerine.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.MessageDao;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    /** Logger for this class and subclasses */
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

        if(ele == null) {
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
        site = (site == null ? "DEFAULT": site);                     
        StringBuilder builder = new StringBuilder(site);
        builder.append(".").append(type.name()).append(".").append(key).append(".").append(locale.getDisplayLanguage());

        return builder.toString();
    }
}
