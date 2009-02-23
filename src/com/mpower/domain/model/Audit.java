package com.mpower.domain.model;

import java.io.Serializable;
import java.util.Date;

import com.mpower.domain.Person;
import com.mpower.type.AuditType;

public class Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private AuditType auditType;

    private Date date;

    private String user;

    private String description;

    private Person person;

    private String siteName;

    private String entityType;

    private Long objectId;

    public Audit() {
        super();
    }

    public Audit(AuditType auditType, String user, Date date, String description, String siteName, String entityType, Long objectId, Person person) {
        this.auditType = auditType;
        this.user = user;
        this.date = date;
        this.description = description;
        this.siteName = siteName;
        this.entityType = entityType;
        this.objectId = objectId;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
