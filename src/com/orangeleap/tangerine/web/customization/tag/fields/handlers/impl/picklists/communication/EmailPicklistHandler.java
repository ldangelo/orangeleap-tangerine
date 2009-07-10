package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.AbstractPicklistHandler;
import com.orangeleap.tangerine.type.FieldType;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class EmailPicklistHandler extends AbstractPicklistHandler {

    private static final String NEW_EMAIL_REF = "li:has(:input[name^=\"email\"])";

	public EmailPicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		Picklist picklist = resolvePicklist(currentField, pageContext);
		createBeginSelect(currentField, formFieldName, picklist, sb);
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
}