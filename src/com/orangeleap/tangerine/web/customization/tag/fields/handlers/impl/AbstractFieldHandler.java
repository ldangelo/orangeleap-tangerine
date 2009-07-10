package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.service.customization.FieldService;
import com.orangeleap.tangerine.service.customization.MessageService;
import com.orangeleap.tangerine.type.MessageResourceType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.FieldHandler;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

/**
 * User: alex lo
 * Date: Jul 2, 2009
 * Time: 6:04:17 PM
 */
public abstract class AbstractFieldHandler implements FieldHandler {
	
	protected final Log logger = OLLogger.getLog(getClass());
	protected FieldService fieldService;
	protected MessageService messageService;
	protected ConstituentService constituentService;
	protected RelationshipService relationshipService;

	protected AbstractFieldHandler(ApplicationContext applicationContext) {
		constituentService = (ConstituentService) applicationContext.getBean("constituentService");
		messageService = (MessageService) applicationContext.getBean("messageService");
		fieldService = (FieldService) applicationContext.getBean("fieldService");
		relationshipService = (RelationshipService) applicationContext.getBean("relationshipService");
	}

	@Override
	public void handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                     SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, StringBuilder sb) {
		String unescapedFieldName = currentField.getFieldPropertyName();
		Object fieldValue = form.getFieldValue(unescapedFieldName);
		String formFieldName = TangerineForm.escapeFieldName(unescapedFieldName);

		writeSideLiElementStart(sectionDefinition, formFieldName, fieldValue, sb);
		writeLabel(currentField, pageContext, sb);

		doHandler(request, response, pageContext, sectionDefinition, sectionFields, currentField, form, formFieldName, fieldValue, sb);
		writeSideLiElementEnd(sb);
	}

	protected abstract void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                                  SectionDefinition sectionDefinition, List<SectionField> sectionFields,
	                     SectionField currentField, TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb);

	protected String getMessage(String code) {
	    return getMessage(code, null);
	}

	protected String getMessage(String code, String[] args) {
	    return TangerineMessageAccessor.getMessage(code, args);
	}

	protected String checkForNull(Object obj) {
	    return obj == null ? StringConstants.EMPTY : StringEscapeUtils.escapeHtml(obj.toString());
	}
	
	public String resolveFieldPropertyName(SectionField currentField) {
	    StringBuilder fieldPropertyName = new StringBuilder(currentField.getFieldPropertyName());

	    if ((currentField.isCompoundField() && currentField.getSecondaryFieldDefinition().isCustom()) ||
	            ( ! currentField.isCompoundField() && currentField.getFieldDefinition().isCustom())) {
	        fieldPropertyName.append(StringConstants.DOT_VALUE);
	    }
	    return fieldPropertyName.toString();
	}
	
	public String resolvedPrefixedFieldName(String prefix, String aFieldName) {
	    String prefixedFieldName = null;

	    boolean endsInValue = false;
	    if (aFieldName.endsWith(StringConstants.DOT_VALUE)) {
	        aFieldName = aFieldName.substring(0, aFieldName.length() - StringConstants.DOT_VALUE.length());
	        endsInValue = true;
	    }

	    int startBracketIndex = aFieldName.lastIndexOf('[');
	    if (startBracketIndex > -1) {
	        int periodIndex = aFieldName.indexOf('.', startBracketIndex);
	        if (periodIndex > -1) {
	            prefixedFieldName = new StringBuilder(aFieldName.substring(0, periodIndex + 1)).append(prefix).append(aFieldName.substring(periodIndex + 1, aFieldName.length())).toString();
	        }
	        else {
	            prefixedFieldName = new StringBuilder(aFieldName.substring(0, startBracketIndex + 1)).append(prefix).append(aFieldName.substring(startBracketIndex + 1, aFieldName.length())).toString();
	        }
	    }
	    if (prefixedFieldName == null) {
	        prefixedFieldName = new StringBuilder(prefix).append(aFieldName).toString();
	    }
	    if (endsInValue) {
	        prefixedFieldName = prefixedFieldName.concat(StringConstants.DOT_VALUE);
	    }
	    return prefixedFieldName;
	}

	protected String resolveUnescapedOtherFieldPropertyName(String fieldPropertyName) {
		return resolvedPrefixedFieldName(StringConstants.OTHER_PREFIX, fieldPropertyName);
	}

	protected String resolveOtherFieldPropertyName(String fieldPropertyName) {
		return TangerineForm.escapeFieldName(resolveUnescapedOtherFieldPropertyName(fieldPropertyName));
	}

	protected String resolveUnescapedAdditionalFieldPropertyName(String fieldPropertyName) {
		return resolvedPrefixedFieldName(StringConstants.ADDITIONAL_PREFIX, fieldPropertyName);
	}

	protected String resolveAdditionalFieldPropertyName(String fieldPropertyName) {
		return TangerineForm.escapeFieldName(resolveUnescapedAdditionalFieldPropertyName(fieldPropertyName));
	}

	protected String resolveEntityAttributes(SectionField currentField) {
		String entityAttributes = currentField.getFieldDefinition().getEntityAttributes();
		StringBuilder entityAttributesStyle = new StringBuilder();
		if (entityAttributes != null) {
		    String[] entityAttributesArray = entityAttributes.split(",");
		    for (String ea : entityAttributesArray) {
		        entityAttributesStyle.append(" ea-").append(ea);
		    }
		}
		return StringEscapeUtils.escapeHtml(entityAttributesStyle.toString());
	}

	protected void writeErrorClass(HttpServletRequest request, PageContext pageContext, StringBuilder sb) {
	    if (pageContext.getAttribute(StringConstants.ERROR_CLASS) != null) {     // TODO
	        sb.append(" ").append(pageContext.getAttribute(StringConstants.ERROR_CLASS));
	    }
	}

	protected void writeDisabled(SectionField currentField, TangerineForm form, StringBuilder sb) {
		if (fieldService.isFieldDisabled(currentField, form.getDomainObject())) {
		    sb.append("disabled=\"true\" ");
		}
	}

	protected void writeSideLiElementStart(SectionDefinition sectionDefinition, String fieldName, Object fieldValue, StringBuilder sb) {
		sb.append("<li class=\"side ");
		sb.append(getSideCssClass(fieldValue));
		sb.append("\" id=\"").append(sectionDefinition.getSectionHtmlName()).append("-").append(fieldName).append("\">");
	}

	protected void writeSideLiElementEnd(StringBuilder sb) {
		sb.append("</li>");
	}

	protected void writeLabel(SectionField sectionField, PageContext pageContext, StringBuilder sb) {
		sb.append("<label for=\"").append(TangerineForm.escapeFieldName(sectionField.getFieldPropertyName())).append("\" class=\"desc\">");
		String helpText = messageService.lookupMessage(MessageResourceType.FIELD_HELP, sectionField.getFieldLabelName(), pageContext.getRequest().getLocale());
		if (StringUtils.hasText(helpText)) {
			sb.append("<a class=\"helpLink\"><img src=\"images/icons/questionGreyTransparent.gif\" /></a><span class=\"helpText\">");
			sb.append(helpText);
			sb.append("</span>");
		}
		if (isFieldRequired(sectionField)) {
			sb.append("<span class=\"required\">*</span>&nbsp;");
		}

		String labelText = messageService.lookupMessage(MessageResourceType.FIELD_LABEL, sectionField.getFieldLabelName(), pageContext.getRequest().getLocale());
		if ( ! StringUtils.hasText(labelText)) {
			if (!sectionField.isCompoundField()) {
				labelText = sectionField.getFieldDefinition().getDefaultLabel();
			}
			else {
				labelText = sectionField.getSecondaryFieldDefinition().getDefaultLabel();
			}
		}
		sb.append(labelText);
		sb.append("</label>");
	}

	protected void writeDeleteLink(StringBuilder sb, String deleteHandler) {
		String removeOptionMsg = getMessage("removeThisOption");
		sb.append("<a href=\"javascript:void(0)\" onclick=\"").append(deleteHandler).append("\" class=\"deleteOption\">");
		sb.append("<img src=\"images/icons/deleteRow.png\" alt=\"").append(removeOptionMsg).append("\" title=\"").append(removeOptionMsg).append("\"/></a>");
	}

	protected String getSideCssClass(Object fieldValue) {
		return StringConstants.EMPTY;
	}

	protected boolean isFieldRequired(SectionField currentField) {
		FieldRequired fr = fieldService.lookupFieldRequired(currentField);
		return (fr != null && fr.isRequired());
	}

	protected Object[] splitValuesByCustomFieldSeparator(Object fieldValue) {
		Object[] fieldVals;
		if (fieldValue == null) {
			fieldVals = new Object[0];
		}
		else if (fieldValue instanceof String) {
			fieldVals = ((String) fieldValue).split(StringConstants.CUSTOM_FIELD_SEPARATOR);
		}
		else {
			fieldVals = new Object[] { fieldValue };
		}
		return fieldVals;
	}
}
