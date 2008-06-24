package com.mpower.web.customization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.entity.User;
import com.mpower.service.customization.MessageService;
import com.mpower.type.MessageResourceType;
import com.mpower.web.common.SessionUtils;

public class ContentTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String messageKey;
	private MessageService messageService;

    public int doStartTag() throws JspException {
    	messageService = (MessageService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("messageService");

    	Locale locale = pageContext.getRequest().getLocale();
        User user = SessionUtils.lookupUser(pageContext.getRequest());
		String messageValue = messageService.lookupMessage(user.getSite(), MessageResourceType.CONTENT, messageKey, locale);
		try {
			pageContext.getOut().write(messageValue);
		} catch (IOException e) {
			throw new JspException(e);
		}
        return Tag.SKIP_BODY;
    }

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
}
