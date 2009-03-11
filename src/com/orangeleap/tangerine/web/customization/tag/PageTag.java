package com.orangeleap.tangerine.web.customization.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.type.LayoutType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

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
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        pageCustomizationService = (PageCustomizationService) appContext.getBean("pageCustomizationService");
        TangerineUserHelper tangerineUserHelper = (TangerineUserHelper)appContext.getBean("tangerineUserHelper");
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(pageName), tangerineUserHelper.lookupUserRoles());
        
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
