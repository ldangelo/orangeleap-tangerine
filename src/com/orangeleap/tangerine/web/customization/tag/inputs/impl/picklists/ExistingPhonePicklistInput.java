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

import com.orangeleap.tangerine.domain.OldPhoneAware;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

@Component("existingPhonePicklistInput")
public class ExistingPhonePicklistInput extends PhonePicklistInput {

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, null);
        createNoneOption(request, fieldVO, sb);

        List<Phone> phones = (List<Phone>) request.getAttribute(StringConstants.PHONES);
        createBeginOptGroup(request, fieldVO, sb, phones);
        createOptions(request, fieldVO, sb, phones);
        createEndOptGroup(request, fieldVO, sb, phones);

        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    @Override
    protected void checkIfExistingOptionSelected(Object model, Phone phoneToCheck, StringBuilder sb) {
        OldPhoneAware aware = (OldPhoneAware) model;
        if (FormBeanType.EXISTING.equals(aware.getPhoneType()) && aware.getSelectedPhone() != null &&
                phoneToCheck.getId().equals(aware.getSelectedPhone().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }
}
