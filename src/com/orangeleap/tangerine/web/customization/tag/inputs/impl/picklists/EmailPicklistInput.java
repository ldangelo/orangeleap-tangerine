package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.NewEmailAware;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.type.FormBeanType;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("emailPicklistInput")
public class EmailPicklistInput extends AbstractSingleValuedPicklistInput {
    
    private static final String NEW_EMAIL_REF = "li:has(:input[name^=\"email\"])";

    @SuppressWarnings("unchecked")
    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, NEW_EMAIL_REF);
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, ((NewEmailAware)fieldVO.getModel()).getEmail(), NEW_EMAIL_REF);
        
        List<Email> emails = (List<Email>)request.getAttribute(StringConstants.EMAILS);
        createBeginOptGroup(request, fieldVO, sb, emails);
        createOptions(request, fieldVO, sb, emails);
        createEndOptGroup(request, fieldVO, sb, emails);
        
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb);
        return sb.toString();
    }
    
    protected void createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List<Email> emails) {
        if (emails != null) {
            for (Email email : emails) {
                sb.append("<option value=\"" + email.getId() + "\"");
                checkIfExistingOptionSelected(fieldVO.getModel(), email, sb);
                sb.append(">");
                sb.append(email.getEmailAddress());
                
                if (email.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
    }
    
    protected void checkIfExistingOptionSelected(Object model, Email emailToCheck, StringBuilder sb) {
        NewEmailAware aware = (NewEmailAware) model;
        if (FormBeanType.EXISTING.equals(aware.getEmailType()) && aware.getEmail() != null && 
                aware.getEmail().isUserCreated() == false && aware.getSelectedEmail() != null && 
                emailToCheck.getId().equals(aware.getSelectedEmail().getId())) {
            sb.append(" selected=\"selected\"");
        }
    }
    
    protected void createSelectedRef(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        String selectedRef = null;
        if (fieldVO.getModel() instanceof NewEmailAware) {
            NewEmailAware aware = (NewEmailAware)fieldVO.getModel();
            if (aware.getEmail().isUserCreated() || (fieldVO.isRequired() && selectedRef == null)) {
                selectedRef = NEW_EMAIL_REF;
            }
        }
        sb.append("<div style=\"display:none\" id=\"selectedRef-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\">" + checkForNull(selectedRef) + "</div>");
    }
}
