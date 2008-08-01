package com.mpower.web.customization;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.customization.MessageService;
import com.mpower.type.MessageResourceType;

public class SectionHeaderTag extends TagSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;
    private SectionDefinition sectionDefinition;
    private MessageService messageService;

    @Override
    public int doStartTag() throws JspException {
        messageService = (MessageService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("messageService");

        Locale locale = pageContext.getRequest().getLocale();
        String messageValue = messageService.lookupMessage(SessionServiceImpl.lookupUserSiteName(pageContext.getRequest()), MessageResourceType.SECTION_HEADER, sectionDefinition.getSectionName(), locale);
        if (GenericValidator.isBlankOrNull(messageValue)) {
            messageValue = sectionDefinition.getDefaultLabel();
        }
        try {
            pageContext.getOut().write(messageValue);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return Tag.SKIP_BODY;
    }

    public SectionDefinition getSectionDefinition() {
        return sectionDefinition;
    }

    public void setSectionDefinition(SectionDefinition sectionDefinition) {
        this.sectionDefinition = sectionDefinition;
    }
}
