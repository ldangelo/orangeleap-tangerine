package com.orangeleap.tangerine.service.customization;

import java.util.Locale;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.MessageDao;
import com.orangeleap.tangerine.type.MessageResourceType;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "messageDAO")
    private MessageDao messageDao;

    private final ConcurrentMap<String,String> CACHE = new ConcurrentHashMap<String,String>();


    @Override
    public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale language) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupMessage: messageResourceType = " + messageResourceType + " messageKey = " + messageKey + " language = " + language);
        }


        String key = buildKey(messageResourceType, messageKey, language);
        String msg = CACHE.get(key);

        if(msg == null) {
            msg = messageDao.readMessage(messageResourceType, messageKey, language);
            // use the String "<NULL>" to represent null
            msg = (msg == null ? "<NULL>" : msg);
            CACHE.put(key, msg);

        }

        // if we substituted a real null with the <NULL> token, change it back to a real null
        return (msg.equals("<NULL>") ? null : msg);
    }

    private String buildKey(MessageResourceType type, String key, Locale locale) {
        StringBuilder builder = new StringBuilder(type.name());
        builder.append("_").append(key).append("_").append(locale.getDisplayLanguage());

        return builder.toString();
    }
}
