package com.orangeleap.tangerine.web.customization.tag.inputs.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.Creatable;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

public abstract class AbstractSingleValuedPicklistInput extends AbstractInput {

    protected void createNoneOption(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        if (fieldVO.isRequired() == false) {
            sb.append("<option value='none'>").append(getMessage("none")).append("</option>");
        }
    }

    protected void createNewOption(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, Creatable creatable, String reference) {
        sb.append("<option value='new'");
        if (StringUtils.hasText(reference)) {
            sb.append(" reference='").append(reference).append("'");
        }
        if (creatable.isUserCreated()) {
            sb.append(" selected='selected'");
        }
        sb.append(">").append(getMessage("createNew")).append("</option>");
    }
    
    @SuppressWarnings("unchecked")
    protected void createBeginOptGroup(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List list) {
        if (list != null && list.isEmpty() == false) {
            sb.append("<optgroup label='").append(getMessage("orChoose")).append("'>");
        }
    }

    @SuppressWarnings("unchecked")
    protected void createEndOptGroup(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List list) {
        if (list != null && list.isEmpty() == false) {
            sb.append("</optgroup>");
        }
    }
}
