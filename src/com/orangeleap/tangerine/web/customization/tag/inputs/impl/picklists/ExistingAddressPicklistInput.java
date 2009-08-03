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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.OldAddressAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("existingAddressPicklistInput")
public class ExistingAddressPicklistInput extends AddressPicklistInput {
    
    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, null);
        createNoneOption(request, fieldVO, sb);
        
        List<Address> addresses = (List<Address>)request.getAttribute(StringConstants.ADDRESSES);
        createBeginOptGroup(request, fieldVO, sb, addresses);
        createOptions(request, fieldVO, sb, addresses);
        createEndOptGroup(request, fieldVO, sb, addresses);
        
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    @Override
    protected void checkIfExistingOptionSelected(Object model, Address addressToCheck, StringBuilder sb) {
        OldAddressAware aware = (OldAddressAware) model;
        if (FormBeanType.EXISTING.equals(aware.getAddressType()) && aware.getSelectedAddress() != null && 
                addressToCheck.getId().equals(aware.getSelectedAddress().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }
}
