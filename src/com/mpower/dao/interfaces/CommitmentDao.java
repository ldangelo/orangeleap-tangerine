package com.mpower.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.type.CommitmentType;

public interface CommitmentDao {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment readCommitment(Long commitmentId);

    public List<Commitment> readCommitments(Long personId, CommitmentType commitmentType);

    public List<Commitment> readCommitments(String siteName, CommitmentType commitmentType, Map<String, Object> params);
}
