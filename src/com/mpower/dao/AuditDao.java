package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Audit;

public interface AuditDao {

    public Audit auditObject(Audit audit);
    
    public List<Audit> allAuditHistoryForSite(String siteName);
}
