package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.picklists;

import com.orangeleap.tangerine.controller.TangerineForm;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class PaymentSourcePicklistHandler extends AbstractPicklistHandler {

	public PaymentSourcePicklistHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected void doHandler(HttpServletRequest request, HttpServletResponse response, PageContext pageContext,
	                      SectionDefinition sectionDefinition, List<SectionField> sectionFields, SectionField currentField,
	                      TangerineForm form, String formFieldName, Object fieldValue, StringBuilder sb) {
		createHiddenField(formFieldName, fieldValue, sb);
		createAchSelectField(request, currentField, formFieldName, fieldValue, sb);
		createCreditCardSelectField(request, currentField, formFieldName, fieldValue, sb);
	}

    protected void createHiddenField(String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<input type=\"hidden\" name=\"").append(formFieldName).append("\"");
        sb.append(" id=\"").append(formFieldName).append("\" value=\"").append(checkForNull(fieldValue)).append("\"/>");
    }

    protected List<PaymentSource> getSources(Map<String, List<PaymentSource>> paymentSources, String key) {
        List<PaymentSource> theseSources = null;
        if (paymentSources != null) {
            theseSources = paymentSources.get(key);
        }
        return theseSources;
    }

    @SuppressWarnings("unchecked")
    protected void createAchSelectField(HttpServletRequest request, SectionField currentField, String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<select name=\"ach-").append(formFieldName).append("\" id=\"ach-").append(formFieldName);
	    sb.append("\" class=\"picklist ").append(resolveEntityAttributes(currentField)).append("\" style=\"display:none\" references=\"li:has(.ea-newAch),li:has(.ea-existingAch)\">");

        createNoneOption(currentField, fieldValue, sb);
        createNewOption(fieldValue, "li:has(.ea-newAch)", sb);

        Map<String, List<PaymentSource>> paymentSources = (Map<String, List<PaymentSource>>) request.getAttribute(StringConstants.PAYMENT_SOURCES);
        List<PaymentSource> achSources = getSources(paymentSources, PaymentSource.ACH);
        createBeginOptGroup(achSources, sb);

        if (achSources != null) {
            for (PaymentSource thisAchSrc : achSources) {
                sb.append("<option value=\"").append(thisAchSrc.getId()).append("\" reference=\"li:has(.ea-existingAch)\" address=\"");
	            if (thisAchSrc.getAddress() != null) {
	                sb.append(checkForNull(thisAchSrc.getAddress().getId()));
	            }
	            sb.append("\" phone=\"");
	            if (thisAchSrc.getPhone() != null) {
		            sb.append(checkForNull(thisAchSrc.getPhone().getId()));
	            }
	            sb.append("\" achholder=\"").append(checkForNull(thisAchSrc.getAchHolderName()));
	            sb.append("\" routing=\"").append(checkForNull(thisAchSrc.getAchRoutingNumberDisplay()));
	            sb.append("\" acct=\"").append(checkForNull(thisAchSrc.getAchAccountNumberDisplay())).append("\" ");
                if (thisAchSrc.getId().equals(fieldValue)) {
                    sb.append("selected=\"selected\"");
                }
                sb.append(">");
                sb.append(thisAchSrc.getProfile());
                if (thisAchSrc.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }

        createEndOptGroup(achSources, sb);
        sb.append("</select>");
	    
	    StringBuilder selectedRef = new StringBuilder();
	    if (fieldValue != null && NumberUtils.isNumber(fieldValue.toString()) && Long.parseLong(fieldValue.toString()) > 0) {
		    selectedRef.append("li:has(.ea-existingAch)");
	    }
	    else {
		    selectedRef.append("li:has(.ea-newAch)");
	    }
	    createSelectedRef("ach-" + formFieldName, fieldValue, selectedRef.toString(), sb);
    }

    @SuppressWarnings("unchecked")
    protected void createCreditCardSelectField(HttpServletRequest request, SectionField currentField, String formFieldName, Object fieldValue, StringBuilder sb) {
        sb.append("<select name=\"creditCard-").append(formFieldName).append("\" id=\"creditCard-").append(formFieldName);
	    sb.append("\" class=\"picklist ").append(resolveEntityAttributes(currentField)).append("\" style=\"display:none\" references=\"li:has(.ea-newCredit),li:has(.ea-existingCredit)\">");

	    createNoneOption(currentField, fieldValue, sb);
	    createNewOption(fieldValue, "li:has(.ea-newCredit)", sb);

	    Map<String, List<PaymentSource>> paymentSources = (Map<String, List<PaymentSource>>) request.getAttribute(StringConstants.PAYMENT_SOURCES);
        List<PaymentSource> ccSources = getSources(paymentSources, PaymentSource.CREDIT_CARD);
        createBeginOptGroup(ccSources, sb);

        SimpleDateFormat sdf = new SimpleDateFormat("MM / yyyy");
        if (ccSources != null) {
            for (PaymentSource thisCcSrc : ccSources) {
                sb.append("<option value=\"").append(thisCcSrc.getId()).append("\" reference=\"li:has(.ea-existingCredit)\" address=\"");
	            if (thisCcSrc.getAddress() != null) {
	                sb.append(checkForNull(thisCcSrc.getAddress().getId()));
	            }
	            sb.append("\" phone=\"");
	            if (thisCcSrc.getPhone() != null) {
	                sb.append(checkForNull(thisCcSrc.getPhone().getId()));
	            }
                sb.append("\" cardholder=\"").append(checkForNull(thisCcSrc.getCreditCardHolderName()));
	            sb.append("\" cardType=\"").append(checkForNull(thisCcSrc.getCreditCardType()));
	            sb.append("\" number=\"").append(checkForNull(thisCcSrc.getCreditCardNumberDisplay()));
                sb.append("\" exp=\"").append(sdf.format(thisCcSrc.getCreditCardExpiration())).append("\"");
                if (thisCcSrc.getId().equals(fieldValue)) {
                    sb.append(" selected=\"selected\"");
                }
                sb.append(">");
                sb.append(thisCcSrc.getProfile());
                if (thisCcSrc.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }

        createEndOptGroup(ccSources, sb);
        sb.append("</select>");

	    StringBuilder selectedRef = new StringBuilder();
	    if (fieldValue != null && NumberUtils.isNumber(fieldValue.toString()) && Long.parseLong(fieldValue.toString()) > 0) {
		    selectedRef.append("li:has(.ea-existingCredit)");
	    }
	    else {
		    selectedRef.append("li:has(.ea-newCredit)");
	    }
	    createSelectedRef("creditCard-" + formFieldName, fieldValue, selectedRef.toString(), sb);
    }

	protected void createSelectedRef(String formFieldName, Object fieldValue, String reference, StringBuilder sb) {
	    sb.append("<div style=\"display:none\" id=\"selectedRef-").append(formFieldName).append("\">").append(checkForNull(reference)).append("</div>");
	}
}