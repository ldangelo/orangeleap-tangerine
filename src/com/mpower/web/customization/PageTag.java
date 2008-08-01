package com.mpower.web.customization;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.customization.SectionDefinition;
import com.mpower.service.SessionServiceImpl;
import com.mpower.service.customization.PageCustomizationService;
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
        GrantedAuthority[] authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        // List<Role> roles = user.get
        List<String> roleList = null;
        if (authorities != null) {
            roleList = new ArrayList<String>();
            for (GrantedAuthority authority : authorities) {
                roleList.add(authority.getAuthority());
            }
        }
        List<SectionDefinition> sectionDefinitions = pageCustomizationService.readSectionDefinitionsBySiteAndPageType(SessionServiceImpl.lookupUserSiteName(pageContext.getRequest()), PageType.valueOf(pageName), roleList);
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
