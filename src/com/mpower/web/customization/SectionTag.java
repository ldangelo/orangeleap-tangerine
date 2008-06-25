package com.mpower.web.customization;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.User;
import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.customization.PageCustomizationService;
import com.mpower.web.common.SessionUtils;

public class SectionTag extends TagSupport {

	private static final long serialVersionUID = 1L;
	private SectionDefinition sectionDefinition;
	private PageCustomizationService pageCustomizationService;

    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        User user = SessionUtils.lookupUser(pageContext.getRequest());
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySiteAndSectionName(user.getSite(), sectionDefinition.getSectionName());
		pageContext.getRequest().setAttribute("sectionFieldList", sectionFields);
		pageContext.getRequest().setAttribute("sectionFieldCount", sectionFields.size());
        return Tag.EVAL_BODY_INCLUDE;
    }

	public SectionDefinition getSectionDefinition() {
		return sectionDefinition;
	}

	public void setSectionDefinition(SectionDefinition sectionDefinition) {
		this.sectionDefinition = sectionDefinition;
	}
}
