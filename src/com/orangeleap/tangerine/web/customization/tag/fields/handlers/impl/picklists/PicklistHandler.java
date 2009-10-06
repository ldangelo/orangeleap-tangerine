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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

public class PicklistHandler extends AbstractPicklistHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public PicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		Picklist picklist = resolvePicklist(currentField);
        fieldValue = resolveFieldValueIfRequired(currentField, picklist, fieldValue);
		createBeginSelect(pageContext, currentField, formFieldName, picklist, sb);
		createNoneOption(currentField, fieldValue, sb);
		String selectedRef = createOptions(pageContext, fieldValue, picklist, sb);
		createEndSelect(sb);
		createSelectedRef(currentField, picklist, formFieldName, selectedRef, sb);
	}

	@Override
	protected void getBeginSelectCssClass(Picklist picklist, StringBuilder sb) {
		if (resolvePicklistIsCascading(picklist)) {
			super.getBeginSelectCssClass(picklist, sb);
		}
	}

    protected String createOptions(PageContext pageContext, Object fieldValue, Picklist picklist, StringBuilder sb) {
	    String selectedRef = null;

        if (picklist != null) {
	        for (PicklistItem item : picklist.getPicklistItems()) {
		        if (StringUtils.hasText(item.getItemName())) {

					String displayValue = resolvePicklistItemDisplayValue(item, pageContext.getRequest());
                    boolean isSelected = fieldValue != null && fieldValue.toString().equals(item.getItemName());

			        if (StringUtils.hasText(displayValue) && ( ! item.isInactive() || (item.isInactive() && isSelected))) {
				        sb.append("<option value=\"").append(StringEscapeUtils.escapeHtml(item.getItemName())).append("\" ");
				        if (StringUtils.hasText(item.getReferenceValue())) {
					        sb.append("reference=\"").append(item.getReferenceValue()).append("\" ");
				        }
				        if (isSelected) {
							sb.append("selected=\"selected\" ");
							selectedRef = item.getReferenceValue();
				        }
				        sb.append(">");
				        sb.append(displayValue);
                        if (item.isInactive()) {
                            sb.append(" ").append(getMessage("inactive"));
                        }
				        sb.append("</option>");
			        }
		        }
	        }
        }
        return selectedRef;
    }

    protected void createSelectedRef(SectionField currentField, Picklist picklist, String formFieldName, String selectedRef, StringBuilder sb) {
        if (isFieldRequired(currentField) && !StringUtils.hasText(selectedRef)) {
            selectedRef = StringEscapeUtils.escapeHtml(resolveFirstReferenceValue(picklist));
        }
	    if (selectedRef == null) {
		    selectedRef = StringConstants.EMPTY;
	    }
        selectedRef = selectedRef.trim();
        sb.append("<div style=\"display:none\" id=\"selectedRef-").append(formFieldName).append("\">");
	    sb.append(selectedRef);
	    sb.append("</div>");
    }
}