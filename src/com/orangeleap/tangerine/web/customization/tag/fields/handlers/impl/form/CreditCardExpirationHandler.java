package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.form;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
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
		PaymentSource paymentSource = (PaymentSource) request.getAttribute("paymentSource");  // TODO: fix for tangerineForm
		createMonthSelect(currentField, formFieldName, sb, paymentSource);
		createYearSelect(currentField, formFieldName, sb, paymentSource);
	}

    protected void createMonthSelect(SectionField currentField, String formFieldName, StringBuilder sb, PaymentSource paymentSource) {
        sb.append("<select name=\"").append(formFieldName).append("Month\" id=\"").append(formFieldName);
	    sb.append("Month\" class=\"expMonth ").append(resolveEntityAttributes(currentField)).append("\">");
        if (paymentSource != null) {
            String expirationMonth = paymentSource.getCreditCardExpirationMonthText();
            if ( ! StringUtils.hasText(expirationMonth)) {
                expirationMonth = new SimpleDateFormat("MM").format(Calendar.getInstance(Locale.getDefault()).getTime());
            }
            List<String> months = paymentSource.getExpirationMonthList();
            if (months != null) {
                for (String thisMonth : months) {
                    sb.append("<option value=\"").append(thisMonth).append("\"");
                    if (thisMonth.equals(expirationMonth)) {
                        sb.append(" selected=\"selected\"");
                    }
                    sb.append(">").append(thisMonth).append("</option>");
                }
            }
        }
        sb.append("</select>");
    }

    protected void createYearSelect(SectionField currentField, String formFieldName, StringBuilder sb, PaymentSource paymentSource) {
        sb.append("<select name=\"").append(formFieldName).append("Year\" id=\"").append(formFieldName);
	    sb.append("Year\" class=\"expYear ").append(resolveEntityAttributes(currentField)).append("\">");
        if (paymentSource != null) {
            Integer expirationYear = paymentSource.getCreditCardExpirationYear();
            if (expirationYear == null || expirationYear <= 0) {
                expirationYear = Calendar.getInstance(Locale.getDefault()).get(Calendar.YEAR);
            }
            List<String> years = paymentSource.getExpirationYearList();
            if (years != null) {
                for (String thisYear : years) {
                    sb.append("<option value=\"").append(thisYear).append("\"");
                    if (thisYear.equals(expirationYear.toString())) {
                        sb.append(" selected=\"selected\"");
                    }
                    sb.append(">").append(thisYear).append("</option>");
                }
            }
        }
        sb.append("</select>");
    }
}