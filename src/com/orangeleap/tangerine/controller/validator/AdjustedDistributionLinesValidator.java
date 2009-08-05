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

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.AdjustedGiftService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

public class AdjustedDistributionLinesValidator extends DistributionLinesValidator {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    private AdjustedGiftService adjustedGiftService;

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return AdjustedGift.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");

        AdjustedGift adjustedGift = (AdjustedGift) target;

        checkTotaledDistributionLineAmountMatch(adjustedGift, errors);
        checkAmountsNotPositive(adjustedGift, errors);
        checkTotalAdjustedAmount(adjustedGift, errors);
    }

    public void checkTotaledDistributionLineAmountMatch(AdjustedGift adjustedGift, Errors errors) {
        BigDecimal total = getTotal(adjustedGift.getDistributionLines());
        BigDecimal adjustedAmount = adjustedGift.getAdjustedAmount();
        if (total == null || adjustedAmount == null || adjustedAmount.compareTo(total) != 0) {
            if (adjustedGift.getDistributionLines() != null) {
                for (int x = 0; x < adjustedGift.getDistributionLines().size(); x++) {
                   errors.rejectValue("distributionLines[" + x + "].amount", "errorDistributionLineAmounts");
                }
            }
            else {
                errors.reject("errorDistributionLineAmounts");
            }
        }
    }

    public void checkAmountsNotPositive(AdjustedGift adjustedGift, Errors errors) {
        if (adjustedGift.getAdjustedAmount() != null && adjustedGift.getAdjustedAmount().compareTo(BigDecimal.ZERO) >= 0) {
            errors.rejectValue("adjustedAmount", "errorAdjustedAmountPositive");
        }
        int x = 0;
        for (DistributionLine aLine : adjustedGift.getDistributionLines()) {
            if (aLine != null) {
                if (aLine.getAmount() != null && aLine.getAmount().compareTo(BigDecimal.ZERO) == 1) {
                    errors.rejectValue("distributionLines[" + x + "].amount", "errorIndividualAdjustedDistributionLineAmountPositive");
                }
                x++;
            }
        }
    }

    public void checkTotalAdjustedAmount(AdjustedGift adjustedGift, Errors errors) {
        BigDecimal existingAmount = adjustedGiftService.findCurrentTotalAdjustedAmount(adjustedGift.getOriginalGiftId());
        BigDecimal totalAmount = existingAmount.add(adjustedGift.getAdjustedAmount() == null ? BigDecimal.ZERO : adjustedGift.getAdjustedAmount());
        BigDecimal originalAmount = adjustedGift.getOriginalAmount();

        BigDecimal leftoverAmount = originalAmount.add(totalAmount);
        if (leftoverAmount.compareTo(BigDecimal.ZERO) == -1) {
            errors.rejectValue("adjustedAmount", "errorTotalAdjustedAmount", new String[]{leftoverAmount.toString()}, null);
        }
    }

    /**
     * Used only in unit tests
     */
    public void setAdjustedGiftService(AdjustedGiftService adjustedGiftService) {
        this.adjustedGiftService = adjustedGiftService;
    }
}
