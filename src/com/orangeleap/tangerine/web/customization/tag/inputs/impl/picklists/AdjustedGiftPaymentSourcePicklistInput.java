package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("adjustedGiftPaymentSourcePicklistInput")
public class AdjustedGiftPaymentSourcePicklistInput extends PaymentSourcePicklistInput {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createHiddenField(request, fieldVO, sb);
        AdjustedGift adjustedGift = (AdjustedGift) fieldVO.getModel();
        if (adjustedGift.getSelectedPaymentSource() != null && adjustedGift.getSelectedPaymentSource().getId() != null && adjustedGift.getSelectedPaymentSource().getId() > 0) {
            if (PaymentSource.ACH.equals(adjustedGift.getSelectedPaymentSource().getPaymentType())) {
                createAchSelectField(request, fieldVO, sb);
            }
            else if (PaymentSource.CREDIT_CARD.equals(adjustedGift.getSelectedPaymentSource().getPaymentType())) {
                createCreditCardSelectField(request, fieldVO, sb);
            }
        }
        return sb.toString();
    }

    @Override
    protected void createAchSelectField(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        PaymentSource selectedPaymentSource = ((PaymentSourceAware)fieldVO.getModel()).getSelectedPaymentSource();

        sb.append("<select name=\"ach-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"ach-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" class=\"" + checkForNull(fieldVO.getEntityAttributes()) + "\">");
        sb.append("<option value=\"" + selectedPaymentSource.getId() + "\" address=\"" + checkForNull(selectedPaymentSource.getSelectedAddress().getId()) + "\" phone=\"" + checkForNull(selectedPaymentSource.getSelectedPhone().getId()) + "\"");
        sb.append(" achholder=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getAchHolderName()) + "\" routing=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getAchRoutingNumberDisplay()) + "\" acct=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getAchAccountNumberDisplay()) + "\"");
        sb.append(" selected=\"selected\"");
        sb.append(">");
        sb.append(selectedPaymentSource.getProfile());
        if (selectedPaymentSource.isInactive()) {
            sb.append("&nbsp;").append(getMessage("inactive"));
        }
        sb.append("</option>");
        sb.append("</select>");
    }
    
    @Override
    protected void createCreditCardSelectField(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        PaymentSource selectedPaymentSource = ((PaymentSourceAware)fieldVO.getModel()).getSelectedPaymentSource();

        sb.append("<select name=\"creditCard-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldName()) + "\" id=\"creditCard-" + StringEscapeUtils.escapeHtml(fieldVO.getFieldId()) + "\" class=\"" + checkForNull(fieldVO.getEntityAttributes()) + "\">");

        SimpleDateFormat sdf = new SimpleDateFormat("MM / yyyy");
        sb.append("<option value=\"" + selectedPaymentSource.getId() + "\" address=\"" + checkForNull(selectedPaymentSource.getSelectedAddress().getId()) + "\" phone=\"" + checkForNull(selectedPaymentSource.getSelectedPhone().getId()) + "\"");
        sb.append(" cardholder=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getCreditCardHolderName()) + "\" cardType=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getCreditCardType()) + "\" number=\"" + StringEscapeUtils.escapeHtml(selectedPaymentSource.getCreditCardNumberDisplay()) + "\"");
        sb.append(" exp=\"" + sdf.format(selectedPaymentSource.getCreditCardExpiration()) + "\"");
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
