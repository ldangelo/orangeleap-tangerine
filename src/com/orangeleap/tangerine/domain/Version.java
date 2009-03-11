package com.orangeleap.tangerine.domain;

import java.io.Serializable;

public class Version implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String id;
    private String description;
    private int majorVersion;
    private int minorVersion;
    
    
    public Version() {
        super();
    }

    public Version(String id, String description, int majorVersion, int minorVersion) {
        this.setId(id);
        this.setDescription(description);
        this.setMajorVersion(majorVersion);
        this.setMinorVersion(minorVersion);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

}
