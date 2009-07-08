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

package com.orangeleap.tangerine.controller.gift;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * Form Bean for Gift Pledge Lines
 *
 * @author Alex Lo
 * @date Apr 6, 2009
 */
@SuppressWarnings("unchecked")
public class GiftPledgeRecurringGiftLinesForm {
    private Constituent constituent;

    @SuppressWarnings("unused")
    private String selectedPledgeIds;
    private Set<String> pledgeIds;

    @SuppressWarnings("unused")
    private String selectedRecurringGiftIds;
    private Set<String> recurringGiftIds;

    @SuppressWarnings("unused")
    private String amount;
    private BigDecimal enteredAmount;

    public GiftPledgeRecurringGiftLinesForm(Constituent constituent) {
        super();
        this.constituent = constituent;
    }

    /**
     * Form bean representation of the DistributionLines
     */
    private List<DistributionLine> mutableDistributionLines = LazyList.decorate(new ArrayList<DistributionLine>(), new Factory() {
        public Object create() {
            DistributionLine line = new DistributionLine(constituent);
            line.setDefaults();
            return line;
        }
    });

    public Set<String> getPledgeIds() {
        return pledgeIds;
    }

    public void setSelectedPledgeIds(String selectedPledgeIds) {
        this.selectedPledgeIds = selectedPledgeIds;
        this.pledgeIds = selectedPledgeIds == null ? null : StringUtils.commaDelimitedListToSet(selectedPledgeIds);
    }

    public Set<String> getRecurringGiftIds() {
        return recurringGiftIds;
    }

    public void setSelectedRecurringGiftIds(String selectedRecurringGiftIds) {
        this.selectedRecurringGiftIds = selectedRecurringGiftIds;
        this.recurringGiftIds = selectedRecurringGiftIds == null ? null : StringUtils.commaDelimitedListToSet(selectedRecurringGiftIds);
    }

    public List<DistributionLine> getMutableDistributionLines() {
        return mutableDistributionLines;
    }

    public void setMutableDistributionLines(List<DistributionLine> mutableDistributionLines) {
        this.mutableDistributionLines = mutableDistributionLines;
    }

    public void setAmount(String amount) {
        this.amount = amount;
        if (NumberUtils.isNumber(amount)) {
            enteredAmount = new BigDecimal(amount);
        } else {
            enteredAmount = BigDecimal.ZERO;
        }
    }

    public BigDecimal getEnteredAmount() {
        return enteredAmount;
    }
}
