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

import com.orangeleap.tangerine.domain.Creatable;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class AbstractSingleValuedPicklistInput extends AbstractInput {

    protected void createNoneOption(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        if (fieldVO.isRequired() == false) {
            sb.append("<option value=\"none\">").append(getMessage("none")).append("</option>");
        }
    }

    protected void createNewOption(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, Creatable creatable, String reference) {
        sb.append("<option value=\"new\"");
        if (StringUtils.hasText(reference)) {
            sb.append(" reference=\"").append(StringEscapeUtils.escapeHtml(reference)).append("\"");
        }
        if (creatable.isUserCreated()) {
            sb.append(" selected=\"selected\"");
        }
        sb.append(">").append(getMessage("createNew")).append("</option>");
    }

    @SuppressWarnings("unchecked")
    protected void createBeginOptGroup(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List list) {
        if (list != null && list.isEmpty() == false) {
            sb.append("<optgroup label=\"").append(getMessage("orChoose")).append("\">");
        }
    }

    @SuppressWarnings("unchecked")
    protected void createEndOptGroup(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, List list) {
        if (list != null && list.isEmpty() == false) {
            sb.append("</optgroup>");
        }
    }

    protected void createBeginSelect(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, String references) {
        sb.append("<select name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" class=\"picklist " + checkForNull(fieldVO.getEntityAttributes()) + "\" references=\"" + checkForNull(references) + "\">");
    }

    protected void createEndSelect(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("</select>");
    }
}
