package com.orangeleap.tangerine.web.customization.tag.inputs.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.communication.Address;
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
        createBeginSelect(request, fieldVO, sb);
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
    protected void checkIfSelected(Object model, Address address, StringBuilder sb) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        Address selectedAddress = (Address)bw.getPropertyValue(StringConstants.SELECTED_ADDRESS);
        if (selectedAddress != null && address.getId().equals(selectedAddress.getId())) {
            sb.append(" selected='selected'");
        }
    }

    @Override
    protected void createBeginSelect(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<select name='" + fieldVO.getFieldName() + "' id='" + fieldVO.getFieldId() + "' class='picklist " + fieldVO.getEntityAttributes() + "'>");
    }
    
    @Override
    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<div style='display:none' id='selectedRef-" + fieldVO.getFieldId() + "'></div>");
    }
}
