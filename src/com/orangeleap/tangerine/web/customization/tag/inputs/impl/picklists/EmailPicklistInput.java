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

import com.orangeleap.tangerine.domain.NewEmailAware;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

@Component("emailPicklistInput")
public class EmailPicklistInput extends AbstractSingleValuedPicklistInput {

    private static final String NEW_EMAIL_REF = "li:has(:input[name^=\"email\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, NEW_EMAIL_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((NewEmailAware) fieldVO.getModel()).getEmail(), NEW_EMAIL_REF);

        List<Email> emails = (List<Email>) request.getAttribute(StringConstants.EMAILS);
        createBeginOptGroup(request, fieldVO, sb, emails);
        createOptions(request, fieldVO, sb, emails);
        createEndOptGroup(request, fieldVO, sb, emails);

        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    protected void createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List<Email> emails) {
        if (emails != null) {
            for (Email email : emails) {
                sb.append("<option value=\"" + email.getId() + "\"");
                checkIfExistingOptionSelected(fieldVO.getModel(), email, sb);
                sb.append(">");
                sb.append(email.getEmailAddress());

                if (email.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }

    protected void checkIfExistingOptionSelected(Object model, Email emailToCheck, StringBuilder sb) {
        NewEmailAware aware = (NewEmailAware) model;
        if (FormBeanType.EXISTING.equals(aware.getEmailType()) && aware.getEmail() != null &&
                aware.getEmail().isUserCreated() == false && aware.getSelectedEmail() != null &&
                emailToCheck.getId().equals(aware.getSelectedEmail().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }

    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof NewEmailAware) {
            NewEmailAware aware = (NewEmailAware) fieldVO.getModel();
            if (aware.getEmail().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_EMAIL_REF;
            }
        }
        sb.append("<div style=\"display:none\" id=\"selectedRef-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">" + checkForNull(selectedRef) + "</div>");
    }
}
