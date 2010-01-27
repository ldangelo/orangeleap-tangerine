package com.orangeleap.tangerine.web.customization.tag.fields.handlers;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import org.springframework.beans.BeanWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.util.List;

public interface FieldHandler {

    void handleField(PageContext pageContext,
                     SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
                     TangerineForm form, StringBuilder sb);

	void handleField(PageContext pageContext,
	                     SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, boolean showSideAndLabel,
	                     boolean isDummy, int rowCounter, StringBuilder sb);

	String resolveLabelText(PageContext pageContext, SectionField sectionField);

    Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue);

	Object resolveDisplayValue(FieldDefinition fieldDef, Object fieldValue);

	Object resolveExtData(final SectionField currentField, Object fieldValue);
}