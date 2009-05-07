package com.orangeleap.tangerine.web.customization.tag.inputs.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("addressPicklistInput")
public class AddressPicklistInput extends AbstractSingleValuedPicklistInput {
    
    private static final String NEW_ADDRESS_REF = "li:has(:input[name^=\"address\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((AddressAware)fieldVO.getModel()).getAddress(), NEW_ADDRESS_REF);
        
        List<Address> addresses = (List<Address>)request.getAttribute(StringConstants.ADDRESSES);
        createBeginOptGroup(request, fieldVO, sb, addresses);
        createOptions(request, fieldVO, sb, addresses);
        createEndOptGroup(request, fieldVO, sb, addresses);
        
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }
    
    protected void createBeginSelect(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<select name='" + fieldVO.getFieldName() + "' id='" + fieldVO.getFieldId() + "' class='picklist " + fieldVO.getEntityAttributes() + "' references='" + NEW_ADDRESS_REF + "'>");
    }
    
    protected void createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List<Address> addresses) {
        if (addresses != null) {
            for (Address address : addresses) {
                sb.append("<option value='" + address.getId() + "'");
                checkIfSelected(fieldVO.getModel(), address, sb);
                sb.append(">");
                sb.append(address.getShortDisplay());
                
                if (address.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }
    
    protected void checkIfSelected(Object model, Address address, StringBuilder sb) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(model);
        Address thisAddress = (Address)bw.getPropertyValue(StringConstants.ADDRESS);
        Address selectedAddress = (Address)bw.getPropertyValue(StringConstants.SELECTED_ADDRESS);
        if (thisAddress != null && thisAddress.isUserCreated() == false && selectedAddress != null && address.getId().equals(selectedAddress.getId())) {
            sb.append(" selected='selected'");
        }
    }
    
    protected void createEndSelect(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</select>");
    }
    
    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        AddressAware aware = (AddressAware)fieldVO.getModel();
        String selectedRef = null;
        if (aware.getAddress().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
            selectedRef = NEW_ADDRESS_REF;
        }
        sb.append("<div style='display:none' id='selectedRef-" + fieldVO.getFieldId() + "'>" + (selectedRef == null ? StringConstants.EMPTY : selectedRef) + "</div>");
    }
}
