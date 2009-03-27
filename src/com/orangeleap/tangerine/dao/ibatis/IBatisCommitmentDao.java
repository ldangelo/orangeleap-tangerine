package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.CommitmentDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.paymentInfo.Commitment;
import com.orangeleap.tangerine.type.CommitmentType;
import com.orangeleap.tangerine.type.EntityType;

@Repository("commitmentDAO")
public class IBatisCommitmentDao extends AbstractPaymentInfoEntityDao<Commitment> implements CommitmentDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	public IBatisCommitmentDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
	}

	@Override
	public Commitment maintainCommitment(Commitment commitment) {
		if (logger.isDebugEnabled()) {
			logger.debug("maintainCommitment: commitment = " + commitment);
		}
		Commitment aCommitment = (Commitment) insertOrUpdate(commitment, "COMMITMENT");
        
		/* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_COMMITMENT_ID", aCommitment.getId());
        /* Then Insert DistributionLines */
        insertDistributionLines(aCommitment, "commitmentId");

        return aCommitment;
	}

	@Override
	public Commitment readCommitmentById(Long commitmentId) {
		if (logger.isDebugEnabled()) {
			logger.debug("readCommitmentById: commitmentId = " + commitmentId);
		}
		Map<String, Object> params = setupParams();
		params.put("id", commitmentId);
		Commitment commitment = (Commitment) getSqlMapClientTemplate().queryForObject("SELECT_COMMITMENT_BY_ID", params);
		loadDistributionLinesCustomFields(commitment);

		return commitment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commitment> readCommitmentsByConstituentIdType(Long constituentId, CommitmentType commitmentType) {
		if (logger.isDebugEnabled()) {
			logger.debug("readCommitmentsByConstituentIdType: constituentId = "	+ constituentId + " commitmentType = " + commitmentType);
		}
		Map<String, Object> params = setupParams();
		params.put("constituentId", constituentId);
		params.put("commitmentType", commitmentType);
		return getSqlMapClientTemplate().queryForList("SELECT_COMMITMENTS_BY_CONSTITUENT_ID_AND_TYPE", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Commitment> searchCommitments(CommitmentType commitmentType,
			Map<String, Object> searchparams) {
		Map<String, Object> params = setupParams();
		QueryUtil.translateSearchParamsToIBatisParams(searchparams, params,
				new SearchFieldMapperFactory().getMapper(EntityType.commitment).getMap());

		List<Commitment> commitments = getSqlMapClientTemplate().queryForList(
				"SELECT_COMMITMENT_BY_SEARCH_TERMS", params);
		return commitments;
	}

}
