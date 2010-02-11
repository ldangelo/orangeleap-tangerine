package com.orangeleap.tangerine.domain;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

public class Background extends AbstractCustomizableEntity {
    
    private static final long serialVersionUID = 1L;

    private Long id;
    private String comments;
    private String backgroundType;
    private String entryType;
    private Date createDate;
    private Date updateDate;
    private Long constituentId;
	
    public Background() { }

    
    public Background(Constituent constituent) { 
    	this.id = constituent.getId();
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    
    public String getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(String backgroundType) {
        this.backgroundType = backgroundType;
    }

    
    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    
    public Long getConstituentId() {
        return constituentId;
    }

    public void setConstituentId(Long constituentId) {
        this.constituentId = constituentId;
    }

	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Background)) {
            return false;
        }
        Background a = (Background) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(comments, a.getComments())
        .append(backgroundType, a.getBackgroundType())
        .append(entryType, a.getEntryType())
        .append(constituentId, a.getConstituentId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+comments)
        .append(""+backgroundType)
        .append(""+entryType)
        .append(""+constituentId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("comments", ""+comments)
        .append("backgroundType", ""+backgroundType)
        .append("entryType", ""+entryType)
        .append("constituentId", ""+constituentId)
        .toString();
    }

}