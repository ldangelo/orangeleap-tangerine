package com.mpower.dao;

import java.util.List;

import com.mpower.domain.Audit;
import com.mpower.domain.Site;
import com.mpower.type.EntityType;

public interface AuditDao {

	public Audit auditObject(Audit audit);

	public List<Audit> allAuditHistoryForSite(Site site);

	public List<Audit> auditHistoryForEntity(Site site, EntityType entityType, Long objectId);

	public List<Audit> auditHistoryForPerson(Long personId);
}
