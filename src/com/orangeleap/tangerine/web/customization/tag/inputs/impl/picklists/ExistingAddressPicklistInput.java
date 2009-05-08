package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("existingAddressPicklistInput")
public class ExistingAddressPicklistInput extends AddressPicklistInput {
    
    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, FieldVO fieldVO) {
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
        AddressAware aware = (AddressAware) model;
        if (FormBeanType.EXISTING.equals(aware.getAddressType()) && aware.getSelectedAddress() != null && 
                addressToCheck.getId().equals(aware.getSelectedAddress().getId())) {
            sb.append(" selected='selected'");
        }
    }
}
