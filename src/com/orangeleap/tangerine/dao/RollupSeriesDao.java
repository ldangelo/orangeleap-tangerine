package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.rollup.RollupSeries;

public interface RollupSeriesDao {

    public RollupSeries readRollupSeriesById(Long id);

    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries);

}