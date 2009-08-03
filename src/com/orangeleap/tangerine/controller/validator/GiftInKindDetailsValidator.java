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

package com.orangeleap.tangerine.controller.validator;

import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

public class GiftInKindDetailsValidator implements Validator {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return GiftInKind.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");

        GiftInKind gik = (GiftInKind) target;
        BigDecimal total = getTotal(gik.getDetails());
        BigDecimal fairMarketValue = gik.getFairMarketValue();
        if (total == null || fairMarketValue == null || total.compareTo(fairMarketValue) != 0) {
            errors.reject("giftInKindFairMarketValues");
        }
        validateDescription(gik.getDetails(), errors);
    }

    private BigDecimal getTotal(List<GiftInKindDetail> details) {
        BigDecimal total = new BigDecimal(0);
        for (GiftInKindDetail detail : details) {
            if (detail != null) {
                total = total.add(detail.getDetailFairMarketValue() == null ? new BigDecimal(0) : detail.getDetailFairMarketValue());
            }
        }
        return total;
    }

    private void validateDescription(List<GiftInKindDetail> details, Errors errors) {
        for (GiftInKindDetail detail : details) {
            if (detail != null) {
                if (detail.getDetailFairMarketValue() != null && !StringUtils.hasText(detail.getDescription())) {
                    errors.reject("giftInKindDescription");
                    break;
                }
            }
        }
    }
}
