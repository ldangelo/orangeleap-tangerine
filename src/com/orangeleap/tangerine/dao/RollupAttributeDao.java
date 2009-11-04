package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.rollup.RollupAttribute;

public interface RollupAttributeDao {

    public RollupAttribute readRollupAttributeById(Long id);

    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute);

}