package com.mpower.web.customization;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.customization.PageCustomizationService;
import com.mpower.type.PageType;

public class PageTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String pageName;
	private PageCustomizationService pageCustomizationService;

    @Override
    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(SessionServiceImpl.lookupUserSiteName(pageContext.getRequest()), PageType.valueOf(pageName));
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
