package com.mpower.service;

import java.util.List;

import com.mpower.domain.Audit;

public interface AuditService {

    public List<Audit> auditObject(Object object);

    public List<Audit> allAuditHistoryForSite(String siteName);

    public List<Audit> auditHistoryForEntity(String siteName, String entityTypeDisplay, Long objectId);

    public List<Audit> auditHistoryForPerson(Long personId);

    public Audit auditObjectInactive(Object object);
}
