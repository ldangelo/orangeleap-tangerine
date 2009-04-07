package com.orangeleap.tangerine.controller.gift;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;


/**
 * Form Bean for Gift Pledge Lines
 * @author Alex Lo
 * @date Apr 6, 2009
 */
@SuppressWarnings("unchecked")
public class GiftPledgeLinesForm {
    
    private String selectedPledgeIds;
    private Set<String> pledgeIds;
    private String amount;
    private BigDecimal enteredAmount;
    
    /** Form bean representation of the DistributionLines */
    private List<DistributionLine> mutableDistributionLines = LazyList.decorate(new ArrayList<DistributionLine>(), new Factory() {
        public Object create() {
            DistributionLine line = new DistributionLine();
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
        }
        else {
            enteredAmount = BigDecimal.ZERO;
        }
    }

    public BigDecimal getEnteredAmount() {
        return enteredAmount;
    }
}
