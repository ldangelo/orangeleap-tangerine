package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.NewPhoneAware;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("phonePicklistInput")
public class PhonePicklistInput extends AbstractSingleValuedPicklistInput {
    
    private static final String NEW_PHONE_REF = "li:has(:input[name^=\"phone\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, NEW_PHONE_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((NewPhoneAware)fieldVO.getModel()).getPhone(), NEW_PHONE_REF);
        
        List<Phone> phones = (List<Phone>)request.getAttribute(StringConstants.PHONES);
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
                sb.append("<option value='" + phone.getId() + "'");
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
            sb.append(" selected='selected'");
        }
    }
    
    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof NewPhoneAware) {
            NewPhoneAware aware = (NewPhoneAware)fieldVO.getModel();
            if (aware.getPhone().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_PHONE_REF;
            }
        }
        sb.append("<div style='display:none' id='selectedRef-" + fieldVO.getFieldId() + "'>" + checkForNull(selectedRef) + "</div>");
    }
}
