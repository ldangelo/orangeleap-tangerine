package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.util.Date;

import com.orangeleap.tangerine.type.AuditType;

public class ErrorLog implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String siteName;
    private Long constituentId;
    private String context;
    private String message;
    private Date createDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Long getConstituentId() {
        return constituentId;
    }

    public void setConstituentId(Long constituentId) {
        this.constituentId = constituentId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}