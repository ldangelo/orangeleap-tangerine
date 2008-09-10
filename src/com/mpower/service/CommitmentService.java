package com.mpower.service;

import java.util.List;
import java.util.Map;

import com.mpower.domain.Commitment;
import com.mpower.domain.Person;

public interface CommitmentService {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment readCommitmentById(Long commitmentId);

    public List<Commitment> readCommitments(Person person);

    public List<Commitment> readCommitments(Long personId);

    public List<Commitment> readCommitments(String siteName, Map<String, Object> params);

    public Commitment createDefaultCommitment(String siteName);

    public void setAuditService(AuditService auditService);
}
