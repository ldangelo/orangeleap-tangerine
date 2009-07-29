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

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.customization.Picklist;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.PageContext;

public class AdjustedGiftPaymentTypePicklistHandler extends PicklistHandler {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

	public AdjustedGiftPaymentTypePicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String createOptions(PageContext pageContext, Object fieldValue, Picklist picklist, StringBuilder sb) {
		String selectedRef = null;

	    if (picklist != null) {
		    for (PicklistItem item : picklist.getActivePicklistItems()) {
			    if (StringUtils.hasText(item.getItemName())) {

					String displayValue = resolvePicklistItemDisplayValue(item, pageContext);

				    // Only allow cash, check, or either ACH or Credit Card, depending on which is selected
				    if (StringUtils.hasText(displayValue) &&
						    (PaymentSource.CASH.equals(item.getItemName()) || PaymentSource.CHECK.equals(item.getItemName()) ||
						     (fieldValue != null && item.getItemName().equals(fieldValue.toString())) )) {
					    sb.append("<option value=\"").append(StringEscapeUtils.escapeHtml(item.getItemName())).append("\" ");
					    if (StringUtils.hasText(item.getReferenceValue())) {
						    sb.append("reference=\"").append(item.getReferenceValue()).append("\" ");
					    }
					    if (fieldValue != null && fieldValue.toString().equals(item.getItemName())) {
							sb.append("selected=\"selected\" ");
							selectedRef = item.getReferenceValue();
					    }
					    sb.append(">");
					    sb.append(displayValue);
					    sb.append("</option>");
				    }
			    }
		    }
	    }
	    return selectedRef;
	}
}