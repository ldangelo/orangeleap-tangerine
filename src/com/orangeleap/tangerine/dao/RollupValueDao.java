package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.rollup.RollupValue;

public interface RollupValueDao {

    public RollupValue readRollupValueById(Long id);

    public RollupValue maintainRollupValue(RollupValue rollupValue);

}