package com.mpower.web.customization;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mpower.domain.entity.customization.FieldDefinition;
import com.mpower.domain.entity.customization.SectionField;
import com.mpower.domain.type.FieldType;
import com.mpower.web.common.SessionUtils;

public class FieldTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    protected final Log logger = LogFactory.getLog(getClass());

    private SectionField sectionField;
    private List<SectionField> sectionFieldList;
    private Object model;

    public int doStartTag() throws JspException {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(this.pageContext.getServletContext());
        FieldDefinition fieldDefinition = sectionField.getFieldDefinition();

        FieldHandler fieldHandler = FieldHandlerHelper.lookupFieldHandler(appContext, sectionField);
        FieldVO fieldVO = fieldHandler.handleField(sectionFieldList, sectionField, pageContext.getRequest().getLocale(), SessionUtils.lookupUser(pageContext.getRequest()));

        if (model == null) {
            model = pageContext.getRequest().getAttribute(fieldDefinition.getEntityType().toString());
        }

        if (!FieldType.SPACER.equals(fieldVO.getFieldType())) {
            try {
                String fieldProperty = fieldVO.getFieldName();
                if (fieldProperty.contains("[")) {
                    fieldProperty = fieldProperty.replaceAll("\\[", ".");
                    fieldProperty = fieldProperty.replaceAll("\\]", "");
                }
                fieldVO.setFieldValue(BeanUtils.getNestedProperty(model, fieldProperty));
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
            } catch (NoSuchMethodException e) {
                logger.error(e);
            } finally {
                model = null;
            }
        }
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
