package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.Audit;

public interface AuditDao {

    public Audit auditObject(Audit audit);

    public List<Audit> allAuditHistoryForSite();

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public List<Audit> auditHistoryForConstituent(Long constituentId);
}
