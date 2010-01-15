package com.orangeleap.tangerine.dao;

import java.util.Date;
import java.util.List;

import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupValue;

public interface RollupValueDao {

    public RollupValue readRollupValueById(Long id);

    public RollupValue maintainRollupValue(RollupValue rollupValue);
    
    public List<RollupValue> readRollupValuesByAttributeSeriesAndConstituentId(Long attributeId, Long seriesId, Long constituentId);

    public void deleteRollupValuesForAttributeSeries(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date startDate, Date endDate);

    public void insertRollupDimensionValues(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date startDate, Date endDate, Long groupByRange1, Long groupByRange2);

}