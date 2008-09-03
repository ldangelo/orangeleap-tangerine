package com.mpower.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Entity
@Table(name = "SITE")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

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
