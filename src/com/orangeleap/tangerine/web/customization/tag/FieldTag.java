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
