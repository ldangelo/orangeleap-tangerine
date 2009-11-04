package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupAttribute implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long rollupSeriesId;
    private boolean sumOrCount;
    private String tableName;
    private String valueColumnName;
    private boolean isValueCustomField;
    private String currencyColumnName;
    private String dateColumnName;
    private String groupByColumnName;
    private boolean isGroupByCustomField;
	
    public RollupAttribute() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getRollupSeriesId() {
        return rollupSeriesId;
    }

    public void setRollupSeriesId(Long rollupSeriesId) {
        this.rollupSeriesId = rollupSeriesId;
    }

    
    public boolean getSumOrCount() {
        return sumOrCount;
    }

    public void setSumOrCount(boolean sumOrCount) {
        this.sumOrCount = sumOrCount;
    }

    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    
    public String getValueColumnName() {
        return valueColumnName;
    }

    public void setValueColumnName(String valueColumnName) {
        this.valueColumnName = valueColumnName;
    }

    
    public boolean getIsValueCustomField() {
        return isValueCustomField;
    }

    public void setIsValueCustomField(boolean isValueCustomField) {
        this.isValueCustomField = isValueCustomField;
    }

    
    public String getCurrencyColumnName() {
        return currencyColumnName;
    }

    public void setCurrencyColumnName(String currencyColumnName) {
        this.currencyColumnName = currencyColumnName;
    }

    
    public String getDateColumnName() {
        return dateColumnName;
    }

    public void setDateColumnName(String dateColumnName) {
        this.dateColumnName = dateColumnName;
    }

    
    public String getGroupByColumnName() {
        return groupByColumnName;
    }

    public void setGroupByColumnName(String groupByColumnName) {
        this.groupByColumnName = groupByColumnName;
    }

    
    public boolean getIsGroupByCustomField() {
        return isGroupByCustomField;
    }

    public void setIsGroupByCustomField(boolean isGroupByCustomField) {
        this.isGroupByCustomField = isGroupByCustomField;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RollupAttribute)) {
            return false;
        }
        RollupAttribute a = (RollupAttribute) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(rollupSeriesId, a.getRollupSeriesId())
        .append(sumOrCount, a.getSumOrCount())
        .append(tableName, a.getTableName())
        .append(valueColumnName, a.getValueColumnName())
        .append(isValueCustomField, a.getIsValueCustomField())
        .append(currencyColumnName, a.getCurrencyColumnName())
        .append(dateColumnName, a.getDateColumnName())
        .append(groupByColumnName, a.getGroupByColumnName())
        .append(isGroupByCustomField, a.getIsGroupByCustomField())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+rollupSeriesId)
        .append(""+sumOrCount)
        .append(""+tableName)
        .append(""+valueColumnName)
        .append(""+isValueCustomField)
        .append(""+currencyColumnName)
        .append(""+dateColumnName)
        .append(""+groupByColumnName)
        .append(""+isGroupByCustomField)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("rollupSeriesId", ""+rollupSeriesId)
        .append("sumOrCount", ""+sumOrCount)
        .append("tableName", ""+tableName)
        .append("valueColumnName", ""+valueColumnName)
        .append("isValueCustomField", ""+isValueCustomField)
        .append("currencyColumnName", ""+currencyColumnName)
        .append("dateColumnName", ""+dateColumnName)
        .append("groupByColumnName", ""+groupByColumnName)
        .append("isGroupByCustomField", ""+isGroupByCustomField)
        .toString();
    }

}