package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.communication.Address;
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

public class AddressPicklistHandler extends AbstractPicklistHandler {

    private static final String NEW_ADDRESS_REF = "li:has(:input[name^=\"address\"])";

	public AddressPicklistHandler(ApplicationContext applicationContext) {
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

		if (!FieldType.EXISTING_ADDRESS_PICKLIST.equals(currentField.getFieldType())) {
			createNewOption(fieldValue, NEW_ADDRESS_REF, sb);
		}

		List<Address> addresses = (List<Address>) request.getAttribute(StringConstants.ADDRESSES);
		createBeginOptGroup(addresses, sb);
		createOptions(fieldValue, addresses, sb);
		createEndOptGroup(addresses, sb);

		createEndSelect(sb);

		if (!FieldType.EXISTING_ADDRESS_PICKLIST.equals(currentField.getFieldType())) {
			createSelectedRef(formFieldName, fieldValue, NEW_ADDRESS_REF, sb);
		}
	}

	protected void createOptions(Object fieldValue, List<Address> addresses, StringBuilder sb) {
        if (addresses != null) {
            for (Address address : addresses) {
                sb.append("<option value=\"").append(address.getId()).append("\"");

                checkIfExistingOptionSelected(fieldValue, address.getId(), sb);

                sb.append(">");
                sb.append(address.getShortDisplay());

                if (address.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }
}