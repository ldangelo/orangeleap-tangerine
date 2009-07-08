/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.web.customization.tag;

import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.customization.PageCustomizationService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class SectionTag extends TagSupport {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());


    private static final long serialVersionUID = 1L;
    private SectionDefinition sectionDefinition;
    private PageCustomizationService pageCustomizationService;

    @Override
    public int doStartTag() throws JspException {
        pageCustomizationService = (PageCustomizationService) WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext()).getBean("pageCustomizationService");
        List<SectionField> sectionFields = pageCustomizationService.readSectionFieldsBySection(sectionDefinition);
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
