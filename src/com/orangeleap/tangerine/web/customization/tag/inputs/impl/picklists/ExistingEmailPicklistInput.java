package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("existingEmailPicklistInput")
public class ExistingEmailPicklistInput extends EmailPicklistInput {
    
    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, null);
        createNoneOption(request, fieldVO, sb);
        
        List<Email> emails = (List<Email>)request.getAttribute(StringConstants.EMAILS);
        createBeginOptGroup(request, fieldVO, sb, emails);
        createOptions(request, fieldVO, sb, emails);
        createEndOptGroup(request, fieldVO, sb, emails);
        
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }

    @Override
    protected void checkIfExistingOptionSelected(Object model, Email emailToCheck, StringBuilder sb) {
        EmailAware aware = (EmailAware) model;
        if (FormBeanType.EXISTING.equals(aware.getEmailType()) && aware.getSelectedEmail() != null && 
                emailToCheck.getId().equals(aware.getSelectedEmail().getId())) {
            sb.append(" selected='selected'");
        }
    }
}
