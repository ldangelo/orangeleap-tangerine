package com.orangeleap.tangerine.service;

import java.math.BigDecimal;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;

public interface CommitmentService<T extends Commitment> {

    public BigDecimal getAmountReceived(T commitment);
    
    public void findGiftSum(Map<String, Object> refData, T commitment);
}
