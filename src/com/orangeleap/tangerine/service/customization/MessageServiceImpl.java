package com.orangeleap.tangerine.service.customization;

import java.util.Locale;

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

    @Override
    public String lookupMessage(MessageResourceType messageResourceType, String messageKey, Locale language) {
        if (logger.isDebugEnabled()) {
            logger.debug("lookupMessage: messageResourceType = " + messageResourceType + " messageKey = " + messageKey + " language = " + language);
        }
        return messageDao.readMessage(messageResourceType, messageKey, language);
    }
}
