package com.orangeleap.tangerine.web.customization.tag.inputs.impl.lookups;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields.TextInput;

@Component("lookupInput")
public class LookupInput extends TextInput {

    @Override
    protected void createInput(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb) {
        super.createInput(request, response, pageContext, fieldVO, sb);
        sb.append("<a class='lookupLink jqModal' href='javascript:void(0)'>");
        sb.append(getMessage("lookup"));
        sb.append("</a>");
    }

    @Override
    protected String getCssClass() {
        return new StringBuilder(super.getCssClass()).append(" lookup ").toString();
    }
}
