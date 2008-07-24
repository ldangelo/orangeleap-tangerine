package com.mpower.service.customization;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mpower.dao.customization.MessageDao;
import com.mpower.type.MessageResourceType;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

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
