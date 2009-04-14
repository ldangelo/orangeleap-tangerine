package com.orangeleap.tangerine.domain;

import java.io.Serializable;

import com.orangeleap.tangerine.type.CacheGroupType;

public class CacheGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public CacheGroup() {
        super();
    }

    public CacheGroup(CacheGroupType id) {
        super();
        this.setId(id);
    }
    
	private CacheGroupType id;
	private java.util.Date updateDate;

	public void setId(CacheGroupType id) {
		this.id = id;
	}

	public CacheGroupType getId() {
		return id;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public String toString() {
		return id + " " + updateDate;
	}


	
}