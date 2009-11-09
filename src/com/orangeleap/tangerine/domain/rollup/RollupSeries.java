package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.GeneratedId;

public class RollupSeries implements GeneratedId, Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String seriesDesc;
    private RollupSeriesType seriesType;
    private Long maintainPeriods;
    private Long futurePeriods;
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

    
    public RollupSeriesType getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(RollupSeriesType seriesType) {
        this.seriesType = seriesType;
    }
    
    public Long getMaintainPeriods() {
        return maintainPeriods;
    }

    public void setMaintainPeriods(Long maintainPeriods) {
        this.maintainPeriods = maintainPeriods;
    }

	public Long getFuturePeriods() {
		return futurePeriods;
	}

	public void setFuturePeriods(Long futurePeriods) {
		this.futurePeriods = futurePeriods;
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
        .append(maintainPeriods, a.getMaintainPeriods())
        .append(futurePeriods, a.getFuturePeriods())
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
        .append(""+maintainPeriods)
        .append(""+futurePeriods)
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
        .append("maintainPeriods", ""+maintainPeriods)
        .append("futurePeriods", ""+futurePeriods)
        .append("keepUnmaintained", ""+keepUnmaintained)
        .append("siteName", ""+siteName)
        .toString();
    }


}