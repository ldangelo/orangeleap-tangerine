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

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.customization.FieldVO;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.util.List;

@Component("adjustedGiftPaymentTypePicklistInput")
public class AdjustedGiftPaymentTypePicklistInput extends PicklistInput {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public String handleField(HttpServletRequest request, HttpServletResponse response, PageContext pageContext, FieldVO fieldVO) {
        if (logger.isTraceEnabled()) {
            logger.trace("handleField: field.fieldName = " + fieldVO.getFieldName());
        }
        StringBuilder sb = new StringBuilder();
        createBeginSelect(request, fieldVO, sb, fieldVO.getUniqueReferenceValues());
        createNoneOption(request, fieldVO, sb);
        String selectedRef = createOptions(request, fieldVO, sb);
        createEndSelect(request, fieldVO, sb);
        createSelectedRef(request, fieldVO, sb, selectedRef);
        return sb.toString();
    }

    @Override
    protected String createOptions(HttpServletRequest request, FieldVO fieldVO, StringBuilder sb) {
        List<String> augmentedCodes = fieldVO.getAugmentedCodes();

        String selectedRef = null;
        if (augmentedCodes != null) {
            List<String> references = fieldVO.getReferenceValues();
            List<Object> displayValues = fieldVO.getDisplayValues();

            AdjustedGift adjustedGift = (AdjustedGift) fieldVO.getModel();

            for (int i = 0; i < augmentedCodes.size(); i++) {
                String code = augmentedCodes.get(i);

                if (PaymentSource.CASH.equals(code) || PaymentSource.CHECK.equals(code) ||
                        (adjustedGift.getSelectedPaymentSource() != null && adjustedGift.getSelectedPaymentSource().getId() != null &&
                                adjustedGift.getSelectedPaymentSource().getId() > 0 && adjustedGift.getSelectedPaymentSource().getPaymentType().equals(code))) {
                    sb.append("<option value=\"" + code + "\"");

                    String reference = (references == null || i >= references.size() ? StringConstants.EMPTY : references.get(i));
                    if (StringUtils.hasText(reference)) {
                        sb.append(" reference=\"" + StringEscapeUtils.escapeHtml(reference) + "\"");
                    }
                    if (code.equals(adjustedGift.getPaymentType())) {
                        sb.append(" selected=\"selected\"");
                        selectedRef = reference;
                    }
                    sb.append(">");
                    sb.append(displayValues == null || i >= displayValues.size() ? StringConstants.EMPTY : StringEscapeUtils.escapeHtml(displayValues.get(i).toString()));
                    sb.append("</option>");
                }
            }
        }
        return selectedRef;
    }
}
