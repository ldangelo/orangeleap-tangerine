package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.Background;

public interface BackgroundDao {

    public Background readBackgroundById(Long id);

    public Background maintainBackground(Background background);

	public List<Background> readBackgroundByConstituentId(Long id);

	public int readCountByConstituentId(Long constituentId);

}