package com.orangeleap.tangerine.web.customization.tag.inputs.impl.picklists;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;

@Component("paymentSourcePicklistInput")
public class PaymentSourcePicklistInput extends AbstractSingleValuedPicklistInput {

    @Override
    public String handleField(HttpServletRequest request, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createHiddenField(request, fieldVO, sb);
        createAchSelectField(request, fieldVO, sb);
        createCreditCardSelectField(request, fieldVO, sb);
        return sb.toString();
    }
    
    protected void createHiddenField(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<input type='hidden' name='" + fieldVO.getFieldName() + "'");
        sb.append(" id='" + fieldVO.getFieldId() + "' value='" + checkForNull(((PaymentSourceAware)fieldVO.getModel()).getSelectedPaymentSource().getId()) + "'/>");
    }
    
    protected List<PaymentSource> getSources(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb, Map<String, List<PaymentSource>> paymentSources, String key) {
        List<PaymentSource> theseSources = null;
        if (paymentSources != null) {
            theseSources = paymentSources.get(key);
        } 
        return theseSources;
    }

    @SuppressWarnings("unchecked")
    protected void createAchSelectField(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<select name='ach-" + fieldVO.getFieldName() + "' id='ach-" + fieldVO.getFieldId() + "' class='" + fieldVO.getEntityAttributes() + "' style='display:none'>");
        
        PaymentSource paymentSource = ((PaymentSourceAware)fieldVO.getModel()).getPaymentSource();
        PaymentSource selectedPaymentSource = ((PaymentSourceAware)fieldVO.getModel()).getSelectedPaymentSource();
        
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, paymentSource, null);
        
        Map<String, List<PaymentSource>> paymentSources = (Map<String, List<PaymentSource>>) request.getAttribute(StringConstants.PAYMENT_SOURCES);
        List<PaymentSource> achSources = getSources(request, fieldVO, sb, paymentSources, PaymentSource.ACH);
        createBeginOptGroup(request, fieldVO, sb, achSources);
        
        if (achSources != null) {
            for (PaymentSource thisAchSrc : achSources) {
                sb.append("<option value='" + thisAchSrc.getId() + "' address='" + checkForNull(thisAchSrc.getSelectedAddress().getId()) + "' phone='" + checkForNull(thisAchSrc.getSelectedPhone().getId()) + "'");
                sb.append(" achholder='" + thisAchSrc.getAchHolderName() + "' routing='" + thisAchSrc.getAchRoutingNumberDisplay() + "' acct='" + thisAchSrc.getAchAccountNumberDisplay() + "'");
                if (paymentSource.isUserCreated() == false && thisAchSrc.getId().equals(selectedPaymentSource.getId())) {
                    sb.append(" selected='selected'");
                }
                sb.append(">");
                sb.append(thisAchSrc.getProfile());
                if (thisAchSrc.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
        
        createEndOptGroup(request, fieldVO, sb, achSources);
        sb.append("</select>");
    }
    
    @SuppressWarnings("unchecked")
    protected void createCreditCardSelectField(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        sb.append("<select name='creditCard-" + fieldVO.getFieldName() + "' id='creditCard-" + fieldVO.getFieldId() + "' class='" + fieldVO.getEntityAttributes() + "' style='display:none'>");
        
        PaymentSource paymentSource = ((PaymentSourceAware)fieldVO.getModel()).getPaymentSource();
        PaymentSource selectedPaymentSource = ((PaymentSourceAware)fieldVO.getModel()).getSelectedPaymentSource();
        
        createNoneOption(request, fieldVO, sb);
        createNewOption(request, fieldVO, sb, paymentSource, null);
        
        Map<String, List<PaymentSource>> paymentSources = (Map<String, List<PaymentSource>>) request.getAttribute(StringConstants.PAYMENT_SOURCES);
        List<PaymentSource> ccSources = getSources(request, fieldVO, sb, paymentSources, PaymentSource.CREDIT_CARD);
        createBeginOptGroup(request, fieldVO, sb, ccSources);

        SimpleDateFormat sdf = new SimpleDateFormat("MM / yyyy");
        if (ccSources != null) {
            for (PaymentSource thisCcSrc : ccSources) {
                sb.append("<option value='" + thisCcSrc.getId() + "' address='" + checkForNull(thisCcSrc.getSelectedAddress().getId()) + "' phone='" + checkForNull(thisCcSrc.getSelectedPhone().getId()) + "'");
                sb.append(" cardholder='" + thisCcSrc.getCreditCardHolderName() + "' cardType='" + thisCcSrc.getCreditCardType() + "' number='" + thisCcSrc.getCreditCardNumberDisplay() + "'");
                sb.append(" exp='" + sdf.format(thisCcSrc.getCreditCardExpiration()) + "'");
                if (paymentSource.isUserCreated() == false && thisCcSrc.getId().equals(selectedPaymentSource.getId())) {
                    sb.append(" selected='selected'");
                }
                sb.append(">");
                sb.append(thisCcSrc.getProfile());
                if (thisCcSrc.isInactive()) {
                    sb.append("&nbsp;").append(getMessage("inactive"));
                }
                sb.append("</option>");
            }
        }
        
        createEndOptGroup(request, fieldVO, sb, ccSources);
        sb.append("</select>");
    }
}
