package com.mpower.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.customization.MessageDao;
import com.mpower.type.MessageResourceType;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
	// injected
	private MessageDao messageDao;
	@Resource(name="messageDao")
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public String lookupMessage(String siteName, MessageResourceType messageResourceType, String messageKey, Locale language) {
		return messageDao.readMessage(siteName, messageResourceType, messageKey, language);
	}
}
