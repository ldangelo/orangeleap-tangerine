package com.orangeleap.tangerine.web.customization.tag.fields.handlers;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public interface FieldHandler {

    void handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
                     SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
                     TangerineForm form, StringBuilder sb);
	
}