package com.orangeleap.tangerine.dao;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.type.CommitmentType;

public interface CommitmentDao {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment readCommitmentById(Long commitmentId);

    public List<Commitment> readCommitmentsByConstituentIdType(Long personId, CommitmentType commitmentType);

    public List<Commitment> searchCommitments(CommitmentType commitmentType, Map<String, Object> params);
}
