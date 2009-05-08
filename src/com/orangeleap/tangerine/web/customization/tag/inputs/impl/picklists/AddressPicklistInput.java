package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.NewAddressAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.type.FormBeanType;
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
        createBeginSelect(request, fieldVO, sb, NEW_ADDRESS_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((NewAddressAware)fieldVO.getModel()).getAddress(), NEW_ADDRESS_REF);
        
        List<Address> addresses = (List<Address>)request.getAttribute(StringConstants.ADDRESSES);
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
                sb.append("<option value='" + address.getId() + "'");
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
        NewAddressAware aware = (NewAddressAware) model;
        if (FormBeanType.EXISTING.equals(aware.getAddressType()) && aware.getAddress() != null && 
                aware.getAddress().isUserCreated() == false && aware.getSelectedAddress() != null && 
                addressToCheck.getId().equals(aware.getSelectedAddress().getId())) {
            sb.append(" selected='selected'");
        }
    }
    
    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof NewAddressAware) {
            NewAddressAware aware = (NewAddressAware)fieldVO.getModel();
            if (aware.getAddress().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_ADDRESS_REF;
            }
        }
        sb.append("<div style='display:none' id='selectedRef-" + fieldVO.getFieldId() + "'>" + checkForNull(selectedRef) + "</div>");
    }
}
