package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.rollup.RollupSeries;

public interface RollupSeriesDao {

    public RollupSeries readRollupSeriesById(Long id);
    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries);
    public List<RollupSeries> readAllRollupSeries();

}