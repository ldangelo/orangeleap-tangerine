package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.Audit;
import com.mpower.domain.model.Person;

public interface AuditService {
    public List<Audit> auditObject(Object object);

    public List<Audit> auditObject(Object object, Person constituent);
    
    public List<Audit> auditObject(Object object, Long userId);

    public List<Audit> allAuditHistoryForSite();

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public List<Audit> auditHistoryForConstituent(Long constituentId);

    public Audit auditObjectInactive(Object object);

    public Audit auditObjectInactive(Object object, Person constituent);

    public Audit auditObjectInactive(Object object, Long userId);
}
