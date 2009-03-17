package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.web.common.PaginatedResult;

public interface AuditDao {

    public Audit auditObject(Audit audit);

    public List<Audit> allAuditHistoryForSite();
    
    public PaginatedResult allAuditHistoryForSite(String sortColumn, String dir, int start, int limit);

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public PaginatedResult auditHistoryForEntity(String entityTypeDisplay, Long objectId,
                                             String sortColumn, String dir, int start, int limit);

    public List<Audit> auditHistoryForConstituent(Long constituentId);

    public PaginatedResult auditHistoryForConstituent(Long constituentId,
                                                  String sortColumn, String dir, int start, int limit);
}
