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
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdjustedGiftPaymentSourcePicklistHandler extends PaymentSourcePicklistHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public AdjustedGiftPaymentSourcePicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, SectionDefinition sectionDefinition,
	                      List<SectionField> sectionFields, SectionField currentField, TangerineForm form, String formFieldName,
	                      Object fieldValue, StringBuilder sb) {
		createHiddenField(formFieldName, fieldValue, sb);
		if (form.getDomainObject() instanceof AdjustedGift) {
			PaymentSource selectedPaymentSource = ((AdjustedGift) form.getDomainObject()).getPaymentSource();
			if (selectedPaymentSource != null) {
				if (PaymentSource.ACH.equals(selectedPaymentSource.getPaymentType())) {
					createAchSelectField(currentField, selectedPaymentSource, formFieldName, sb);
				}
				else if (PaymentSource.CREDIT_CARD.equals(selectedPaymentSource.getPaymentType())) {
					createCreditCardSelectField(currentField, selectedPaymentSource, formFieldName, sb);
				}
			}
		}
	}

    protected void createAchSelectField(SectionField currentField, PaymentSource selectedPaymentSource,
                                        String formFieldName, StringBuilder sb) {
	    sb.append("<select name=\"ach-").append(formFieldName).append("\" id=\"ach-").append(formFieldName);
		sb.append("\" class=\"picklist ").append(resolveEntityAttributes(currentField)).append("\">");
	    sb.append("<option value=\"").append(selectedPaymentSource.getId()).append("\" address=\"");
		if (selectedPaymentSource.getAddress() != null) {
		    sb.append(checkForNull(selectedPaymentSource.getAddress().getId()));
		}
		sb.append("\" phone=\"");
		if (selectedPaymentSource.getPhone() != null) {
			sb.append(checkForNull(selectedPaymentSource.getPhone().getId()));
		}
		sb.append("\" achholder=\"").append(checkForNull(selectedPaymentSource.getAchHolderName()));
		sb.append("\" routing=\"").append(checkForNull(selectedPaymentSource.getAchRoutingNumberDisplay()));
		sb.append("\" acct=\"").append(checkForNull(selectedPaymentSource.getAchAccountNumberDisplay())).append("\" ");
		sb.append("selected=\"selected\"");
	    sb.append(">");
	    sb.append(selectedPaymentSource.getProfile());
	    if (selectedPaymentSource.isInactive()) {
	        sb.append("&nbsp;").append(getMessage("inactive"));
	    }
	    sb.append("</option>");
        sb.append("</select>");
    }

    protected void createCreditCardSelectField(SectionField currentField, PaymentSource selectedPaymentSource,
                                        String formFieldName, StringBuilder sb) {
	    SimpleDateFormat sdf = new SimpleDateFormat("MM / yyyy");

	    sb.append("<select name=\"creditCard-").append(formFieldName).append("\" id=\"creditCard-").append(formFieldName);
		sb.append("\" class=\"picklist ").append(resolveEntityAttributes(currentField)).append("\">");
	    sb.append("<option value=\"").append(selectedPaymentSource.getId()).append("\" address=\"");
		if (selectedPaymentSource.getAddress() != null) {
		    sb.append(checkForNull(selectedPaymentSource.getAddress().getId()));
		}
		sb.append("\" phone=\"");
		if (selectedPaymentSource.getPhone() != null) {
		    sb.append(checkForNull(selectedPaymentSource.getPhone().getId()));
		}
	    sb.append("\" cardholder=\"").append(checkForNull(selectedPaymentSource.getCreditCardHolderName()));
		sb.append("\" cardType=\"").append(checkForNull(selectedPaymentSource.getCreditCardType()));
		sb.append("\" number=\"").append(checkForNull(selectedPaymentSource.getCreditCardNumberDisplay()));
	    sb.append("\" exp=\"").append(sdf.format(selectedPaymentSource.getCreditCardExpiration())).append("\"");
		sb.append(" selected=\"selected\"");
	    sb.append(">");
	    sb.append(selectedPaymentSource.getProfile());
	    if (selectedPaymentSource.isInactive()) {
	        sb.append("&nbsp;").append(getMessage("inactive"));
	    }
	    sb.append("</option>");
        sb.append("</select>");
    }
}