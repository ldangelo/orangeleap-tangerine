package com.mpower.dao;

import java.util.List;

import com.mpower.domain.model.Audit;

@Deprecated
public interface AuditDao {

    public Audit auditObject(Audit audit);

    public List<Audit> allAuditHistoryForSite();

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public List<Audit> auditHistoryForPerson(Long personId);
}
