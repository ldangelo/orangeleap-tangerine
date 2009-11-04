package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupSeries implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String seriesDesc;
    private String seriesType;
    private Date beginDate;
    private Long maintainPeriods;
    private boolean keepUnmaintained;
    private String siteName;
	
    public RollupSeries() { }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getSeriesDesc() {
        return seriesDesc;
    }

    public void setSeriesDesc(String seriesDesc) {
        this.seriesDesc = seriesDesc;
    }

    
    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    
    public Long getMaintainPeriods() {
        return maintainPeriods;
    }

    public void setMaintainPeriods(Long maintainPeriods) {
        this.maintainPeriods = maintainPeriods;
    }

    
    public boolean getKeepUnmaintained() {
        return keepUnmaintained;
    }

    public void setKeepUnmaintained(boolean keepUnmaintained) {
        this.keepUnmaintained = keepUnmaintained;
    }

    
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RollupSeries)) {
            return false;
        }
        RollupSeries a = (RollupSeries) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(seriesDesc, a.getSeriesDesc())
        .append(seriesType, a.getSeriesType())
        .append(beginDate, a.getBeginDate())
        .append(maintainPeriods, a.getMaintainPeriods())
        .append(keepUnmaintained, a.getKeepUnmaintained())
        .append(siteName, a.getSiteName())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+seriesDesc)
        .append(""+seriesType)
        .append(""+beginDate)
        .append(""+maintainPeriods)
        .append(""+keepUnmaintained)
        .append(""+siteName)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("seriesDesc", ""+seriesDesc)
        .append("seriesType", ""+seriesType)
        .append("beginDate", ""+beginDate)
        .append("maintainPeriods", ""+maintainPeriods)
        .append("keepUnmaintained", ""+keepUnmaintained)
        .append("siteName", ""+siteName)
        .toString();
    }

}