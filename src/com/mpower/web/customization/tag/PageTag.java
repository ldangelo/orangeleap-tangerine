package com.mpower.web.customization.tag;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.service.customization.PageCustomizationService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.PageType;

public class PageTag extends TagSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


    private static final long serialVersionUID = 1L;
    private String pageName;
    private PageCustomizationService pageCustomizationService;

    @Override
    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(pageName), SessionServiceImpl.lookupUserRoles());
        pageContext.getRequest().setAttribute("sectionDefinitions", sectionDefinitions);
        return Tag.EVAL_BODY_INCLUDE;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
