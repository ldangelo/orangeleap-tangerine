package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.AbstractPicklistHandler;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class EmailPicklistHandler extends AbstractPicklistHandler {

    private static final String NEW_EMAIL_REF = "li:has(.ea-newEmail)";

	public EmailPicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		Picklist picklist = resolvePicklist(currentField);
		createBeginSelect(pageContext, currentField, formFieldName, picklist, sb);
		createNoneOption(currentField, fieldValue, sb);

		if (!FieldType.EXISTING_EMAIL_PICKLIST.equals(currentField.getFieldType())) {
			createNewOption(fieldValue, NEW_EMAIL_REF, sb);
		}

		List<Email> emails = (List<Email>) request.getAttribute(StringConstants.EMAILS);
		createBeginOptGroup(emails, sb);
		createOptions(fieldValue, emails, sb);
		createEndOptGroup(emails, sb);

		createEndSelect(sb);

		if (!FieldType.EXISTING_EMAIL_PICKLIST.equals(currentField.getFieldType())) {
			createSelectedRef(formFieldName, fieldValue, NEW_EMAIL_REF, sb);
		}
	}

	@Override
	protected String resolveReferenceValues(SectionField currentField, Picklist picklist) {
		StringBuilder refValues = new StringBuilder(super.resolveReferenceValues(currentField, picklist));
		if (!FieldType.EXISTING_EMAIL_PICKLIST.equals(currentField.getFieldType())) {
			if (StringUtils.hasText(refValues.toString())) {
				refValues.append(",");
			}
			refValues.append(NEW_EMAIL_REF);
		}
		return refValues.toString();
	}

	protected void createOptions(Object fieldValue, List<Email> emails, StringBuilder sb) {
        if (emails != null) {
            for (Email email : emails) {
                sb.append("<option value=\"").append(email.getId()).append("\"");

                checkIfExistingOptionSelected(fieldValue, email.getId(), sb);

                sb.append(">");
                sb.append(email.getEmailAddress());

                if (email.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        Email email = null;
        Object domainObject = beanWrapper.getWrappedInstance();
        if (domainObject instanceof Email) {
            email = (Email) domainObject;
        }
        else if (domainObject instanceof EmailAware) {
            email = ((EmailAware) domainObject).getEmail();
        }
        return email == null ? StringConstants.EMPTY : email.getEmailAddress();
    }
}