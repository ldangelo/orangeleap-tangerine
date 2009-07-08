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

package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.web.customization.FieldVO;
import com.orangeleap.tangerine.web.customization.tag.inputs.AbstractInput;

@Component("creditCardExpirationInput")
public class CreditCardExpirationInput extends AbstractInput {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        StringBuilder sb = new StringBuilder();
        PaymentSource paymentSource = (PaymentSource) request.getAttribute("paymentSource");
        createMonthSelect(request, response, pageContext, fieldVO, sb, paymentSource);
        createYearSelect(request, response, pageContext, fieldVO, sb, paymentSource);
        return sb.toString();
    }

    protected void createMonthSelect(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb, PaymentSource paymentSource) {
        sb.append("<select name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "Month\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "Month\" class=\"expMonth " + checkForNull(fieldVO.getEntityAttributes()) + "\">");
        if (paymentSource != null) {
            String expirationMonth = paymentSource.getCreditCardExpirationMonthText();
            if (StringUtils.hasText(expirationMonth) == false) {
                expirationMonth = new SimpleDateFormat("MM").format(Calendar.getInstance(Locale.getDefault()).getTime());
            }
            List<String> months = paymentSource.getExpirationMonthList();
            if (months != null) {
                for (String thisMonth : months) {
                    sb.append("<option value=\"" + thisMonth + "\"");
                    if (thisMonth.equals(expirationMonth)) {
                        sb.append(" selected=\"selected\"");
                    }
                    sb.append(">" + thisMonth + "</option>");
                }
            }
        }
        sb.append("</select>");
    }

    protected void createYearSelect(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO, StringBuilder sb, PaymentSource paymentSource) {
        sb.append("<select name=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "Year\" id=\"" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "Year\" class=\"expYear " + checkForNull(fieldVO.getEntityAttributes()) + "\">");
        if (paymentSource != null) {
            Integer expirationYear = paymentSource.getCreditCardExpirationYear();
            if (expirationYear == null || expirationYear <= 0) {
                expirationYear = Calendar.getInstance(Locale.getDefault()).get(Calendar.YEAR);
            }
            List<String> years = paymentSource.getExpirationYearList();
            if (years != null) {
                for (String thisYear : years) {
                    sb.append("<option value=\"" + thisYear + "\"");
                    if (thisYear.equals(expirationYear.toString())) {
                        sb.append(" selected=\"selected\"");
                    }
                    sb.append(">" + thisYear + "</option>");
                }
            }
        }
        sb.append("</select>");
    }
}
