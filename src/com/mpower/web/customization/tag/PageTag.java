package com.mpower.web.customization.tag;

import java.util.ArrayList;
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
import com.mpower.type.LayoutType;
import com.mpower.type.PageType;

public class PageTag extends TagSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final long serialVersionUID = 1L;
    private String pageName;
    private PageCustomizationService pageCustomizationService;

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(SessionServiceImpl.lookupUserSiteName(), PageType.valueOf(pageName), SessionServiceImpl.lookupUserRoles());
        
        pageContext.getRequest().setAttribute("sectionDefinitions", sectionDefinitions);
        seperateSectionsByGridColumns(sectionDefinitions);
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    /** TODO: This is a temporarily method used by the gift page and will be changed/removed when Tiles is refactored */
    private void seperateSectionsByGridColumns(List<SectionDefinition> sectionDefinitions) {
        List<SectionDefinition> gridSections = new ArrayList<SectionDefinition>();
        List<SectionDefinition> columnSections = new ArrayList<SectionDefinition>();
        
        for (SectionDefinition sectionDefinition : sectionDefinitions) {
            if (LayoutType.GRID.equals(sectionDefinition.getLayoutType())) {
                gridSections.add(sectionDefinition);
            }
            else {
                columnSections.add(sectionDefinition);
            }
        }
        
        pageContext.getRequest().setAttribute("gridSections", gridSections);
        pageContext.getRequest().setAttribute("columnSections", columnSections);
    }
}
