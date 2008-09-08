package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Audit;
import com.mpower.domain.Site;

public interface AuditDao {

    public Audit auditObject(Audit audit);
    
    public List<Audit> allAuditHistoryForSite(Site site);
}
