/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists.communication;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.communication.Phone;
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

public class PhonePicklistHandler extends AbstractPicklistHandler {

    private static final String NEW_PHONE_REF = "li:has(:input[name^=\"phone\"])";

	public PhonePicklistHandler(ApplicationContext applicationContext) {
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

		if (!FieldType.EXISTING_PHONE_PICKLIST.equals(currentField.getFieldType())) {
			createNewOption(fieldValue, NEW_PHONE_REF, sb);
		}

		List<Phone> phones = (List<Phone>) request.getAttribute(StringConstants.PHONES);
		createBeginOptGroup(phones, sb);
		createOptions(fieldValue, phones, sb);
		createEndOptGroup(phones, sb);

		createEndSelect(sb);

		if (!FieldType.EXISTING_PHONE_PICKLIST.equals(currentField.getFieldType())) {
			createSelectedRef(formFieldName, fieldValue, NEW_PHONE_REF, sb);
		}
	}

	protected void createOptions(Object fieldValue, List<Phone> phones, StringBuilder sb) {
        if (phones != null) {
            for (Phone phone : phones) {
                sb.append("<option value=\"").append(phone.getId()).append("\"");

                checkIfExistingOptionSelected(fieldValue, phone.getId(), sb);

                sb.append(">");
                sb.append(phone.getNumber());

                if (phone.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }
}