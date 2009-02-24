package com.mpower.domain.model.customization;

import java.io.Serializable;

import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.Site;

public class CodeType implements GeneratedId, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Site site;

	private String name;

	private String label;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

    @Override
	public Long getId() {
		return id;
	}

    @Override
	public void setId(Long id) {
		this.id = id;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public String getSiteName() {
		return site.getName();
	}

}
