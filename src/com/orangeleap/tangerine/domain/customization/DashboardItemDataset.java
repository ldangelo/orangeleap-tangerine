package com.orangeleap.tangerine.domain.customization;

public class DashboardItemDataset {

	private Long id;
	private Long itemId;
	private Integer num;
	private String label;
	private String sqltext;
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getNum() {
		return num;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}
	public String getSqltext() {
		return sqltext;
	}
	
}
