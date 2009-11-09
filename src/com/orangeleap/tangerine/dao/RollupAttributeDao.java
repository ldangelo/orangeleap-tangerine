package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.rollup.RollupAttribute;

public interface RollupAttributeDao {

    public RollupAttribute readRollupAttributeById(Long id);
    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute);
    public void deleteRollupAttributeById(Long id);
    public List<RollupAttribute> readAllRollupAttributes();
    public List<RollupAttribute> readAllRollupAttributesByType(String entityType);

}