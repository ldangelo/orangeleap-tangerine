package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.Audit;

public interface AuditService {

    public List<Audit> auditObject(Object object);

    public List<Audit> allAuditHistoryForSite();

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public List<Audit> auditHistoryForConstituent(Long constituentId);

    public Audit auditObjectInactive(Object object);
}
