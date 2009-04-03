package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.type.EntityType;

@Repository("pledgeDAO")
public class IBatisPledgeDao extends AbstractPaymentInfoEntityDao<Pledge> implements PledgeDao {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	public IBatisPledgeDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
	}

	@Override
	public Pledge maintainPledge(Pledge pledge) {
		if (logger.isDebugEnabled()) {
			logger.debug("maintainCommitment: pledge = " + pledge);
		}
		Pledge aPledge = (Pledge) insertOrUpdate(pledge, "PLEDGE");
        
		/* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_PLEDGE_ID", aPledge.getId()); // TODO
        /* Then Insert DistributionLines */
        insertDistributionLines(aPledge, "pledgeId");

        return aPledge;
	}

	@Override
	public Pledge readPledgeById(Long pledgeId) {
		if (logger.isDebugEnabled()) {
			logger.debug("readPledgeById: pledgeId = " + pledgeId);
		}
		Map<String, Object> params = setupParams();
		params.put("id", pledgeId);
		Pledge pledge = (Pledge) getSqlMapClientTemplate().queryForObject("SELECT_PLEDGE_BY_ID", params);
		loadDistributionLinesCustomFields(pledge);

		return pledge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pledge> readPledgesByConstituentId(Long constituentId) {
		if (logger.isDebugEnabled()) {
			logger.debug("readCommitmentsByConstituentIdType: constituentId = "	+ constituentId);
		}
		Map<String, Object> params = setupParams();
		params.put("constituentId", constituentId);
		return getSqlMapClientTemplate().queryForList("SELECT_PLEDGES_BY_CONSTITUENT_ID", params);
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> findNotCancelledPledgesByGiftId(Long giftId, Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("findNotCancelledPledgesByGiftId: giftId = " + giftId + " constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("giftId", giftId);
        return getSqlMapClientTemplate().queryForList("SELECT_NOT_CANCELLED_PLEDGES_FOR_GIFT", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> findNotCancelledPledges(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("findNotCancelledPledges: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_NOT_CANCELLED_PLEDGES", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Pledge> searchPledges(Map<String, Object> searchParams) {
        if (logger.isDebugEnabled()) {
            logger.debug("searchPledges: searchParams = " + searchParams);
        }
		Map<String, Object> params = setupParams();
		QueryUtil.translateSearchParamsToIBatisParams(searchParams, params,	new SearchFieldMapperFactory().getMapper(EntityType.pledge).getMap());

		return getSqlMapClientTemplate().queryForList("SELECT_PLEDGE_BY_SEARCH_TERMS", params);
	}
}