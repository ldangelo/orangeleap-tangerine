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

package com.orangeleap.tangerine.service.validator;

import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.logging.Log;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;

public class DistributionLinesValidator implements Validator {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return AbstractPaymentInfoEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.trace("validate:");

        BigDecimal total = null;
        BigDecimal amount = null;
        List<DistributionLine> lines = null;

        if (target instanceof Gift) {
            Gift gift = (Gift) target;
            total = getTotal(gift.getDistributionLines());
            amount = gift.getAmount();
            lines = gift.getDistributionLines();
        }
        else if (target instanceof Pledge) {
            Pledge pledge = (Pledge) target;
            total = getTotal(pledge.getDistributionLines());
            if (pledge.isRecurring()) {
                amount = pledge.getAmountPerGift();
            }
            else {
                amount = pledge.getAmountTotal();
            }
            lines = pledge.getDistributionLines();
        }
        else if (target instanceof RecurringGift) {
            RecurringGift recurringGift = (RecurringGift) target;
            total = getTotal(recurringGift.getDistributionLines());
            amount = recurringGift.getAmountPerGift();
            lines = recurringGift.getDistributionLines();
        }
        checkAmountsNotNegative(amount, lines, errors);
        if (total == null || amount == null || total.compareTo(amount) != 0) {
            if (lines != null) {
                for (int x = 0; x < lines.size(); x++) {
                    errors.rejectValue("distributionLines[" + x + "].amount", "errorDistributionLineAmounts");
                }
            }
            else {
                errors.reject("errorDistributionLineAmounts");                
            }
        }
    }

    public void checkAmountsNotNegative(BigDecimal amount, List<DistributionLine> lines, Errors errors) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) == -1) {
            errors.rejectValue("amount", "errorAmountNegative");
        }
        int x = 0;
        for (DistributionLine aLine : lines) {
            if (aLine != null) {
                if (aLine.getAmount() != null && aLine.getAmount().compareTo(BigDecimal.ZERO) == -1) {
                    errors.rejectValue("distributionLines[" + x + "].amount", "errorIndividualDistributionLineAmountNegative");
                }
                x++;
            }
        }
    }

    protected BigDecimal getTotal(List<DistributionLine> lines) {
        BigDecimal total = new BigDecimal(0);
        for (DistributionLine line : lines) {
            if (line != null) {
                total = total.add(line.getAmount() == null ? new BigDecimal(0) : line.getAmount());
            }
        }
        return total;
    }
}
