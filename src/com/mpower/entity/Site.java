package com.mpower.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mpower.entity.listener.EmptyStringNullifyerListener;

@Entity
@EntityListeners(value = { EmptyStringNullifyerListener.class })
@Table(name = "SITE")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "SITE_ID")
    private Long id;

    @Column(name = "SITE_NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_SITE_ID", referencedColumnName = "SITE_ID")
    private Site parentSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
