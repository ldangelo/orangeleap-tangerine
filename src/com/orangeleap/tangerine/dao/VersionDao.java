package com.orangeleap.tangerine.dao;

import com.orangeleap.tangerine.domain.Version;

public interface VersionDao {
	
	public Version selectVersion(String id);

}
