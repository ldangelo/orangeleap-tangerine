package com.mpower.web.customization;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.domain.customization.SectionField;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.customization.PageCustomizationService;

public class SectionTag extends TagSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;
    private SectionDefinition sectionDefinition;
    private PageCustomizationService pageCustomizationService;

    @Override
    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySiteAndSectionName(SessionServiceImpl.lookupUserSiteName(pageContext.getRequest()), sectionDefinition);
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
