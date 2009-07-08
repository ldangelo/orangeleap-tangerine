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

package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import com.orangeleap.tangerine.domain.NewPhoneAware;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

@Component("phonePicklistInput")
public class PhonePicklistInput extends AbstractSingleValuedPicklistInput {

    private static final String NEW_PHONE_REF = "li:has(:input[name^=\"phone\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, NEW_PHONE_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((NewPhoneAware) fieldVO.getModel()).getPhone(), NEW_PHONE_REF);

        List<Phone> phones = (List<Phone>) request.getAttribute(StringConstants.PHONES);
        createBeginOptGroup(request, fieldVO, sb, phones);
        createOptions(request, fieldVO, sb, phones);
        createEndOptGroup(request, fieldVO, sb, phones);

        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    protected void createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List<Phone> phones) {
        if (phones != null) {
            for (Phone phone : phones) {
                sb.append("<option value=\"" + phone.getId() + "\"");
                checkIfExistingOptionSelected(fieldVO.getModel(), phone, sb);
                sb.append(">");
                sb.append(phone.getNumber());

                if (phone.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }

    protected void checkIfExistingOptionSelected(Object model, Phone phoneToCheck, StringBuilder sb) {
        NewPhoneAware aware = (NewPhoneAware) model;
        if (FormBeanType.EXISTING.equals(aware.getPhoneType()) && aware.getPhone() != null &&
                aware.getPhone().isUserCreated() == false && aware.getSelectedPhone() != null &&
                phoneToCheck.getId().equals(aware.getSelectedPhone().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }

    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof NewPhoneAware) {
            NewPhoneAware aware = (NewPhoneAware) fieldVO.getModel();
            if (aware.getPhone().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_PHONE_REF;
            }
        }
        sb.append("<div style=\"display:none\" id=\"selectedRef-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">" + checkForNull(selectedRef) + "</div>");
    }
}
