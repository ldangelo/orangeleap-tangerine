package com.mpower.dao;

import java.util.List;
import java.util.Map;

import com.mpower.domain.Commitment;

public interface CommitmentDao {

    public Commitment maintainCommitment(Commitment commitment);

    public Commitment readCommitment(Long commitmentId);

    public List<Commitment> readCommitments(Long personId);

    public List<Commitment> readCommitments(String siteName, Map<String, Object> params);
}
