package com.orangeleap.tangerine.domain.rollup;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

public class RollupSeriesXAttribute implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long rollupSeriesId;
    private Long rollupAttributeId;
	
    public RollupSeriesXAttribute() { }

    
    public Long getRollupSeriesId() {
        return rollupSeriesId;
    }

    public void setRollupSeriesId(Long rollupSeriesId) {
        this.rollupSeriesId = rollupSeriesId;
    }

    
    public Long getRollupAttributeId() {
        return rollupAttributeId;
    }

    public void setRollupAttributeId(Long rollupAttributeId) {
        this.rollupAttributeId = rollupAttributeId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RollupSeriesXAttribute)) {
            return false;
        }
        RollupSeriesXAttribute a = (RollupSeriesXAttribute) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(rollupSeriesId, a.getRollupSeriesId())
        .append(rollupAttributeId, a.getRollupAttributeId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+rollupSeriesId)
        .append(""+rollupAttributeId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("rollupSeriesId", ""+rollupSeriesId)
        .append("rollupAttributeId", ""+rollupAttributeId)
        .toString();
    }

}