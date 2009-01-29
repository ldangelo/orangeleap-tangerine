package com.mpower.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.mpower.domain.Commitment;
import com.mpower.domain.Gift;
import com.mpower.domain.Person;
import com.mpower.type.CommitmentType;

public interface CommitmentService {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment editCommitment(Commitment commitment);

    public Commitment readCommitmentById(Long commitmentId);
    
    public Commitment readCommitmentByIdCreateIfNull(String commitmentId, Person person);

    public List<Commitment> readCommitments(Person person, CommitmentType commitmentType);

    public List<Commitment> readCommitments(Long personId, CommitmentType commitmentType);

    public List<Commitment> readCommitments(String siteName, CommitmentType commitmentType, Map<String, Object> params);

    public Commitment createDefaultCommitment(Person person, CommitmentType commitmentType);

    public void setAuditService(AuditService auditService);

    public List<Calendar> getCommitmentGiftDates(Commitment commitment);

    public List<Gift> getCommitmentGifts(Commitment commitment);

    public BigDecimal getAmountReceived(Long commitmentId);
}
