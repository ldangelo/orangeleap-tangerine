package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;

public interface RollupSeriesXAttributeDao {

    public List<RollupSeriesXAttribute> selectRollupSeriesForAttribute(Long attributeId);
    public void maintainRollupSeriesXAttribute(Long attributeId, List<RollupSeriesXAttribute> rollupSeriesXAttributes);

}