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

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.handler.FieldHandler;
import com.orangeleap.tangerine.web.customization.handler.FieldHandlerHelper;

public class FieldTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    protected final Log logger = OLLogger.getLog(getClass());

    private SectionField sectionField;
    private List<SectionField> sectionFieldList;
    private Object model;

    @Override
    public int doStartTag() throws JspException {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        FieldDefinition fieldDefinition = sectionField.getFieldDefinition();
        FieldHandler fieldHandler = FieldHandlerHelper.lookupFieldHandler(appContext, sectionField);
        if (fieldHandler == null) {
        	logger.error("No field handler found for "+sectionField.getFieldPropertyName());
        }

        Object modelParam = model != null ? model : pageContext.getRequest().getAttribute(fieldDefinition.getEntityType().toString());
        FieldVO fieldVO = fieldHandler.handleField(sectionFieldList, sectionField, pageContext.getRequest().getLocale(), modelParam);

        pageContext.getRequest().setAttribute("fieldVO", fieldVO);
        return Tag.SKIP_BODY;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public SectionField getSectionField() {
        return sectionField;
    }

    public void setSectionField(SectionField sectionField) {
        this.sectionField = sectionField;
    }

    public List<SectionField> getSectionFieldList() {
        return sectionFieldList;
    }

    public void setSectionFieldList(List<SectionField> sectionFieldList) {
        this.sectionFieldList = sectionFieldList;
    }
}
