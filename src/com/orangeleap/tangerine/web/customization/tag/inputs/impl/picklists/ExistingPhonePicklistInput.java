package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

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
        
        List<Phone> phones = (List<Phone>)request.getAttribute(StringConstants.PHONES);
        createBeginOptGroup(request, fieldVO, sb, phones);
        createOptions(request, fieldVO, sb, phones);
        createEndOptGroup(request, fieldVO, sb, phones);
        
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    @Override
    protected void checkIfExistingOptionSelected(Object model, Phone phoneToCheck, StringBuilder sb) {
        PhoneAware aware = (PhoneAware) model;
        if (FormBeanType.EXISTING.equals(aware.getPhoneType()) && aware.getSelectedPhone() != null && 
                phoneToCheck.getId().equals(aware.getSelectedPhone().getId())) {
            sb.append(" selected='selected'");
        }
    }
}
