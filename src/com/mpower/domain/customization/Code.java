package com.mpower.domain.customization;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.Auditable;
import com.mpower.domain.Site;

@Entity
@Table(name = "CODE", uniqueConstraints = { @UniqueConstraint(columnNames = { "SITE_NAME", "CODE_TYPE", "CODE_VALUE" }) })
public class Code implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "CODE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME", nullable = false)
    private Site site;

    @Column(name = "CODE_TYPE", nullable = false)
    private String codeType;

    @Column(name = "CODE_VALUE", nullable = false)
    private String value;

    @Column(name = "INACTIVE")
    private boolean inactive = false;

    @Column(name = "CODE_DESCRIPTION")
    private String description;

    @Transient
    private Auditable originalObject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Auditable getOriginalObject() {
        return originalObject;
    }

    public void setOriginalObject(Auditable originalObject) {
        this.originalObject = originalObject;
    }
}
