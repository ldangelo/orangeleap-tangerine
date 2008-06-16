package com.mpower.web.customization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.entity.User;
import com.mpower.domain.type.MessageResourceType;
import com.mpower.service.customization.MessageService;
import com.mpower.web.common.SessionUtils;

public class PageTitleTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String pageName;
	private MessageService messageService;

    public int doStartTag() throws JspException {
    	messageService = (MessageService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("messageService");

    	Locale locale = pageContext.getRequest().getLocale();
        User user = SessionUtils.lookupUser(pageContext.getRequest());
		String messageValue = messageService.lookupMessage(user.getSite(), MessageResourceType.TITLE, pageName, locale);
		try {
			pageContext.getOut().write(messageValue);
		} catch (IOException e) {
			throw new JspException(e);
		}
        return Tag.SKIP_BODY;
    }

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String messageKey) {
		this.pageName = messageKey;
	}
}
