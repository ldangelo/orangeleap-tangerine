package com.orangeleap.tangerine.web.customization.tag;

import java.util.*;

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
    private List<String> skipList = new ArrayList<String>();
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getSkip() {
        // poor-mans join() operation
        StringBuilder builder = new StringBuilder();
        for(String item : skipList) {
            builder.append(item).append(',');
        }

        if(builder.charAt( builder.length()-1 ) == ',') {
            builder.deleteCharAt( builder.length() - 1);
        }

        return builder.toString();
    }

    /**
     * A comma-separated list of sections that should not be rendered. Each token
     * in the comma delimited String is in the format pagename:section.name
     * (pagename, colon, section name). For example, to exclude the gift.distribution
     * section of the giftView page, you would use a token like giftView:gift.distribution
     * @param skipSections comma-delimited String of sections to skip
     */
    public void setSkip(String skipSections) {

        String[] sides = skipSections.split(",");
        for(String side:sides) {
            skipList.add(side);
        }

    }

    @Override
    public int doStartTag() throws JspException {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        pageCustomizationService = (PageCustomizationService) appContext.getBean("pageCustomizationService");
        TangerineUserHelper tangerineUserHelper = (TangerineUserHelper)appContext.getBean("tangerineUserHelper");

        // handle multiple page names separated by a comma
        String[] pages = pageName.split(",");
        List<SectionDefinition> sectionDefinitions = new ArrayList<SectionDefinition>();

        for(String page : pages) {
            List<SectionDefinition> defintions = pageCustomizationService.readSectionDefinitionsByPageTypeRoles(PageType.valueOf(page), tangerineUserHelper.lookupUserRoles());

            for(SectionDefinition def : defintions) {
                String key = page + ":" + def.getSectionName();
                if(!skipList.contains(key)) {
                    sectionDefinitions.add(def);
                }
            }
        }

        pageContext.getRequest().setAttribute("sectionDefinitions", sectionDefinitions);
        seperateSectionsByGridColumns(sectionDefinitions);
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    /** TODO: This is a temporarily method used by the gift page and will be changed/removed when Tiles is refactored */
    private void seperateSectionsByGridColumns(List<SectionDefinition> sectionDefinitions) {
        List<SectionDefinition> gridSections = new ArrayList<SectionDefinition>();
        List<SectionDefinition> hiddenGridRows = new ArrayList<SectionDefinition>();
        List<SectionDefinition> columnSections = new ArrayList<SectionDefinition>();
        
        for (SectionDefinition sectionDefinition : sectionDefinitions) {
            if (LayoutType.GRID.equals(sectionDefinition.getLayoutType())) {
                gridSections.add(sectionDefinition);
            }
            else if (LayoutType.GRID_HIDDEN_ROW.equals(sectionDefinition.getLayoutType())) {
                hiddenGridRows.add(sectionDefinition);
            }
            else {
                columnSections.add(sectionDefinition);
            }
        }
        
        pageContext.getRequest().setAttribute("gridSections", gridSections);
        pageContext.getRequest().setAttribute("hiddenGridRows", hiddenGridRows);
        pageContext.getRequest().setAttribute("columnSections", columnSections);
    }
}
