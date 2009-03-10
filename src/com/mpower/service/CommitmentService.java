package com.mpower.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.Person;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.type.CommitmentType;

public interface CommitmentService {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment editCommitment(Commitment commitment);

    public Commitment readCommitmentById(Long commitmentId);
    
    public Commitment readCommitmentByIdCreateIfNull(String commitmentId, Person constituent, CommitmentType commitmentType);

    public List<Commitment> readCommitments(Person constituent, CommitmentType commitmentType);

    public List<Commitment> readCommitments(Long constituentId, CommitmentType commitmentType);

    public List<Commitment> searchCommitments(CommitmentType commitmentType, Map<String, Object> params);

    public Commitment createDefaultCommitment(Person person, CommitmentType commitmentType);

    public List<Calendar> getCommitmentGiftDates(Commitment commitment);

    public List<Gift> getCommitmentGifts(Commitment commitment);

    public BigDecimal getAmountReceived(Long commitmentId);
    
    public void findGiftSum(Map<String, Object> refData, Commitment commitment);
}
