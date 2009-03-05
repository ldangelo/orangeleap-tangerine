package com.mpower.dao.interfaces;

import com.mpower.domain.model.Version;

public interface VersionDao {
	
	public Version selectVersion(String id);

}
