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
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

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
    public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isDebugEnabled()) {
            logger.debug("readPaginatedPledgesByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_PLEDGES_BY_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("PLEDGES_BY_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
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
	
	@SuppressWarnings("unchecked")
	@Override
    public List<DistributionLine> findDistributionLinesForPledges(List<String> pledgeIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("findDistributionLinesForPledges: pledgeIds = " + pledgeIds);
        }
        Map<String, Object> params = setupParams();
        params.put("pledgeIds", pledgeIds);
        return getSqlMapClientTemplate().queryForList("SELECT_DISTRO_LINE_BY_PLEDGE_ID", params);
	}
}
