package com.orangeleap.tangerine.domain.customization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

// This structure provides data to google.visualization.DataTable
public class DashboardData {
	
	private String title = "";
	private String graphType = "";
	private String url = "";
	private List<String> columnTypes = new ArrayList<String>();
	private List<String> columnTitles = new ArrayList<String>();
	private List<String> rowLabels = new ArrayList<String>();
	private List<List<BigDecimal>> rowData = new ArrayList<List<BigDecimal>>();
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
	public String getGraphType() {
		return graphType;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrl() {
		return url;
	}
	public void setColumnTypes(List<String> columnTypes) {
		this.columnTypes = columnTypes;
	}
	public List<String> getColumnTypes() {
		return columnTypes;
	}
	public void setColumnTitles(List<String> columnTitles) {
		this.columnTitles = columnTitles;
	}
	public List<String> getColumnTitles() {
		return columnTitles;
	}
	public void setRowLabels(List<String> rowLabels) {
		this.rowLabels = rowLabels;
	}
	public List<String> getRowLabels() {
		return rowLabels;
	}
	public void setRowData(List<List<BigDecimal>> rowData) {
		this.rowData = rowData;
	}
	public List<List<BigDecimal>> getRowData() {
		return rowData;
	}
	
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);	
	}
	
}