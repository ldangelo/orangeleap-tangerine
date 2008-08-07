package com.mpower.web.customization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.service.SessionServiceImpl;
import com.mpower.service.customization.MessageService;
import com.mpower.type.MessageResourceType;

public class PageTitleTag extends TagSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    private static final long serialVersionUID = 1L;
    private String pageName;
    private MessageService messageService;

    @Override
    public int doStartTag() throws JspException {
        messageService = (MessageService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("messageService");

        Locale locale = pageContext.getRequest().getLocale();
        String messageValue = messageService.lookupMessage(SessionServiceImpl.lookupUserSiteName(), MessageResourceType.TITLE, pageName, locale);
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
