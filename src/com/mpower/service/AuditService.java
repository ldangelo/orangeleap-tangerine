package com.mpower.service;

import java.util.List;

import com.mpower.domain.Audit;
import com.mpower.domain.Viewable;

public interface AuditService {

    public List<Audit> auditObject(Viewable entity);
}
