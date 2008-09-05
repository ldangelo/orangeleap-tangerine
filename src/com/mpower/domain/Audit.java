package com.mpower.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.type.AuditType;
import com.mpower.type.EntityType;

@Entity
@Table(name = "Audit")
public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "AUDIT_ID")
    private Long id;

    @Column(name="AUDIT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private AuditType auditType;

    @Column(name = "DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "USER")
    private String user;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME", nullable = false)
    private Site site;

    @Column(name="ENTITY_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityType entityType;

    @Column(name="OBJECT_ID", nullable = false)
    private Long objectId;

    public Audit() {
        super();
    }

    public Audit(AuditType auditType, String user, Date date, String description, Site site, EntityType entityType, Long objectId) {
        this.auditType = auditType;
        this.user = user;
        this.date = date;
        this.description = description;
        this.site = site;
        this.entityType = entityType;
        this.objectId = objectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
