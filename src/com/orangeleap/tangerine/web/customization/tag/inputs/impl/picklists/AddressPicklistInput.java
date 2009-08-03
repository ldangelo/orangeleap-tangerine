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

import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.OldAddressAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;
                                                                                                     
@Component("addressPicklistInput")
public class AddressPicklistInput extends AbstractSingleValuedPicklistInput {

    private static final String NEW_ADDRESS_REF = "li:has(:input[name^=\"address\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, NEW_ADDRESS_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((AddressAware) fieldVO.getModel()).getAddress(), NEW_ADDRESS_REF);

        List<Address> addresses = (List<Address>) request.getAttribute(StringConstants.ADDRESSES);
        createBeginOptGroup(request, fieldVO, sb, addresses);
        createOptions(request, fieldVO, sb, addresses);
        createEndOptGroup(request, fieldVO, sb, addresses);

        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    protected void createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List<Address> addresses) {
        if (addresses != null) {
            for (Address address : addresses) {
                sb.append("<option value=\"" + address.getId() + "\"");
                checkIfExistingOptionSelected(fieldVO.getModel(), address, sb);
                sb.append(">");
                sb.append(address.getShortDisplay());

                if (address.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }

    protected void checkIfExistingOptionSelected(Object model, Address addressToCheck, StringBuilder sb) {
        OldAddressAware aware = (OldAddressAware) model;
        if (FormBeanType.EXISTING.equals(aware.getAddressType()) && aware.getAddress() != null &&
                aware.getAddress().isUserCreated() == false && aware.getSelectedAddress() != null &&
                addressToCheck.getId().equals(aware.getSelectedAddress().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }

    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof AddressAware) {
            AddressAware aware = (AddressAware) fieldVO.getModel();
            if (aware.getAddress().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_ADDRESS_REF;
            }
        }
        sb.append("<div style=\"display:none\" id=\"selectedRef-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">" + checkForNull(selectedRef) + "</div>");
    }
}
