package com.orangeleap.tangerine.domain.customization;

import java.util.List;

import com.orangeleap.tangerine.domain.Site;

public class DashboardItem {

    private Long id;
    private String type;
    private String title;
    private String url;
	private Integer order;
	private String roles;
	private Site site;
	private List<DashboardItemDataset> datasets;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Integer getOrder() {
		return order;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getRoles() {
		return roles;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public Site getSite() {
		return site;
	}
	public void setDatasets(List<DashboardItemDataset> datasets) {
		this.datasets = datasets;
	}
	public List<DashboardItemDataset> getDatasets() {
		return datasets;
	}
	
	
}
