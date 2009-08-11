package com.orangeleap.tangerine.web.customization.tag.fields.handlers;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;

import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.BeanWrapper;

public interface FieldHandler {

    void handleField(PageContext pageContext,
                     SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
                     TangerineForm form, StringBuilder sb);

	void handleField(PageContext pageContext,
	                     SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, boolean showSideAndLabel,
	                     boolean isDummy, int rowCounter, StringBuilder sb);

	String resolveLabelText(PageContext pageContext, SectionField sectionField);

    Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField);
}