package com.orangeleap.tangerine.service;

import java.math.BigDecimal;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;

public interface CommitmentService<T extends Commitment> {

//    public Commitment maintainCommitment(Commitment commitment);
//
//    public Commitment editCommitment(Commitment commitment);
//
//    public Commitment readCommitmentById(Long commitmentId);
//    
//    public Commitment readCommitmentByIdCreateIfNull(String commitmentId, Person constituent, CommitmentType commitmentType);
//
//    public List<Commitment> readCommitments(Person constituent, CommitmentType commitmentType);
//
//    public List<Commitment> readCommitments(Long constituentId, CommitmentType commitmentType);
//
//    public List<Commitment> searchCommitments(CommitmentType commitmentType, Map<String, Object> params);

//    public void createDefault(Person constituent, T commitment, EntityType entityType, String lineIdProperty);

//    public List<Calendar> getCommitmentGiftDates(Commitment commitment);
//
//    public List<Gift> getCommitmentGifts(Commitment commitment);
    
//    public List<Commitment> findNotCancelledPledgesByGiftId(Long giftId, Long constituentId);
//    
//    public Map<String, List<Commitment>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds);

    public BigDecimal getAmountReceived(T commitment);
    
    public void findGiftSum(Map<String, Object> refData, T commitment);
}
