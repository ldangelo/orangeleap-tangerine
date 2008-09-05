package com.mpower.service;

import java.util.List;

import com.mpower.domain.Audit;

public interface AuditService {

    public List<Audit> auditObject(Object object);

    public List<Audit> allAuditHistoryForSite(String siteName);
}
