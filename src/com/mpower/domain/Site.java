package com.mpower.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "SITE")
public class Site implements Serializable {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SITE_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_SITE_NAME", referencedColumnName = "SITE_NAME")
    private Site parentSite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Site getParentSite() {
        return parentSite;
    }

    public void setParentSite(Site parentSite) {
        this.parentSite = parentSite;
    }
}
