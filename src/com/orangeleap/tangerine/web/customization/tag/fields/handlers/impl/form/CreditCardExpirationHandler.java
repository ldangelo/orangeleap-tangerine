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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreditCardExpirationHandler extends AbstractFieldHandler {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

	public CreditCardExpirationHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		Object domainObject = form.getDomainObject();
		PaymentSource paymentSource = null;
		if (domainObject instanceof PaymentSource) {
			paymentSource = (PaymentSource) domainObject;
		}
		else if (domainObject instanceof PaymentSourceAware) {
			paymentSource = ((PaymentSourceAware) domainObject).getPaymentSource();
		}
		createMonthSelect(pageContext, currentField, paymentSource, formFieldName, fieldValue, sb);
		createYearSelect(pageContext, currentField, paymentSource, formFieldName, fieldValue, sb);
	}

    protected void createMonthSelect(PageContext pageContext, SectionField currentField, PaymentSource paymentSource, String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<select name=\"").append(formFieldName).append("Month\" id=\"").append(formFieldName);
	    sb.append("Month\" class=\"expMonth ").append(resolveEntityAttributes(currentField));
	    writeErrorClass(pageContext, formFieldName, sb); 
	    sb.append("\">");

	    String expirationMonth = null;
        if (paymentSource != null) {
            expirationMonth = paymentSource.getCreditCardExpirationMonthText();
        }
		if ( ! StringUtils.hasText(expirationMonth)) {
			expirationMonth = new SimpleDateFormat("MM").format(Calendar.getInstance(Locale.getDefault()).getTime());
		}
		List<String> months = PaymentSource.getExpirationMonthList();
		if (months != null) {
			for (String thisMonth : months) {
				sb.append("<option value=\"").append(thisMonth).append("\"");
				if (thisMonth.equals(expirationMonth)) {
					sb.append(" selected=\"selected\"");
				}
				sb.append(">").append(thisMonth).append("</option>");
			}
		}
        sb.append("</select>");
    }

    protected void createYearSelect(PageContext pageContext, SectionField currentField, PaymentSource paymentSource, String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<select name=\"").append(formFieldName).append("Year\" id=\"").append(formFieldName);
	    sb.append("Year\" class=\"expYear ").append(resolveEntityAttributes(currentField));
	    writeErrorClass(pageContext, formFieldName, sb); 
	    sb.append("\">");
	    Integer expirationYear = null;
        if (paymentSource != null) {
            expirationYear = paymentSource.getCreditCardExpirationYear();
        }
		if (expirationYear == null || expirationYear <= 0) {
			expirationYear = Calendar.getInstance(Locale.getDefault()).get(Calendar.YEAR);
		}
		List<String> years = PaymentSource.getExpirationYearList();
		if (years != null) {
			for (String thisYear : years) {
				sb.append("<option value=\"").append(thisYear).append("\"");
				if (thisYear.equals(expirationYear.toString())) {
					sb.append(" selected=\"selected\"");
				}
				sb.append(">").append(thisYear).append("</option>");
			}
		}
        sb.append("</select>");
    }

    @Override
    public Object resolveDisplayValue(HttpServletRequest request, BeanWrapper beanWrapper, SectionField currentField, Object fieldValue) {
        Object domainObject = beanWrapper.getWrappedInstance();
        PaymentSource paymentSource = null;
        if (domainObject instanceof PaymentSource) {
            paymentSource = (PaymentSource) domainObject;
        }
        else if (domainObject instanceof PaymentSourceAware) {
            paymentSource = ((PaymentSourceAware) domainObject).getPaymentSource();
        }
        return paymentSource != null ? new SimpleDateFormat(StringConstants.CREDIT_CARD_EXP_DISPLAY_FORMAT).format(paymentSource.getCreditCardExpiration()) :
                StringConstants.EMPTY;
    }
}