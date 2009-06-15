package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.common.PaginatedResult;

public interface AuditService {
    public List<Audit> auditObject(Object object);

    public List<Audit> auditObject(Object object, Constituent constituent);
    
    public List<Audit> auditObject(Object object, Long userId);

    public List<Audit> allAuditHistoryForSite();

    public PaginatedResult allAuditHistoryForSite(SortInfo sortInfo);

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public PaginatedResult auditHistoryForEntity(String entityTypeDisplay, Long objectId, SortInfo sortInfo);

    public List<Audit> auditHistoryForConstituent(Long constituentId);

    public PaginatedResult auditHistoryForConstituent(Long constituentId, SortInfo sortInfo);

    public Audit auditObjectInactive(Object object);

    public Audit auditObjectInactive(Object object, Constituent constituent);

    public Audit auditObjectInactive(Object object, Long userId);
}
