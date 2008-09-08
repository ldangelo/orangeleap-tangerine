package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Audit;
import com.mpower.domain.Site;
import com.mpower.type.EntityType;

public interface AuditDao {

	public Audit auditObject(Audit audit);

	public List<Audit> allAuditHistoryForSite(Site site);

	public List<Audit> AuditHistoryForEntity(Site site, EntityType entityType, Long objectId);
}
