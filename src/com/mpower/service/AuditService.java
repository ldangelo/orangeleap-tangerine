package com.mpower.service;

import java.util.List;

import com.mpower.domain.Audit;
import com.mpower.type.EntityType;

public interface AuditService {

    public List<Audit> auditObject(Object object);

    public List<Audit> allAuditHistoryForSite(String siteName);

    public List<Audit> AuditHistoryForEntity(String siteName, EntityType entityType, Long objectId);

    public List<Audit> auditObjectDelete(Object object);
}
