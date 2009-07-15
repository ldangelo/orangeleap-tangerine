package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: alexlo
 * Date: Jul 7, 2009
 * Time: 5:09:39 PM
 */
public class DateHandler extends AbstractFieldHandler {

	public DateHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<div class=\"lookupWrapper\">");

		sb.append("<input id=\"").append(formFieldName).append("\" ");
		sb.append("class=\"text ").append(resolveEntityAttributes(currentField)).append("\" ");

		sb.append("type=\"text\" maxlength=\"10\" size=\"16\" value=\"");

		if (fieldValue != null && fieldValue instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			sb.append(sdf.format(fieldValue));
		}
		sb.append("\" name=\"").append(formFieldName).append("\"/>");

		createScript(formFieldName, sb);
		
		sb.append("</div>");
	}

	protected void createScript(String formFieldName, StringBuilder sb) {
		sb.append("<script type=\"text/javascript\">");
		sb.append("//<![CDATA[\n");
		sb.append("var name = '").append(formFieldName).append("';\n");
		sb.append("var seasonal = (name.indexOf('seasonal') > -1);\n");
		sb.append("name = name.replace('[','').replace(']','');\n");
		sb.append("new Ext.form.DateField({\n");
		sb.append("applyTo: name,\n");
		sb.append("id: name + \"-wrapper\",\n");
		sb.append("format: (seasonal ? 'F-j' : 'm/d/Y'),\n");
		sb.append("width: 250\n");
		sb.append("});\n");
		sb.append("//]]>\n");
		sb.append("</script>");
	}
}