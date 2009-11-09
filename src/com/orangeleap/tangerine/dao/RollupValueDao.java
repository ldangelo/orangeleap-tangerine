package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.rollup.RollupValue;

public interface RollupValueDao {

    public RollupValue readRollupValueById(Long id);

    public RollupValue maintainRollupValue(RollupValue rollupValue);
    
    public List<RollupValue> readRollupValuesByAttributeAndConstituentId(Long attributeId, Long constituentId);


}