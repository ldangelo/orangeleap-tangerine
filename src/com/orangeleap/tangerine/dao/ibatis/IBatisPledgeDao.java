package com.orangeleap.tangerine.dao.ibatis;

import java.math.BigDecimal;
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
		if (logger.isTraceEnabled()) {
			logger.trace("maintainCommitment: pledgeId = " + pledge.getId());
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
		if (logger.isTraceEnabled()) {
			logger.trace("readPledgeById: pledgeId = " + pledgeId);
		}
		Map<String, Object> params = setupParams();
		params.put("id", pledgeId);
		Pledge pledge = (Pledge) getSqlMapClientTemplate().queryForObject("SELECT_PLEDGE_BY_ID", params);
		loadDistributionLinesCustomFields(pledge);
		if (pledge != null) {
            pledge.setAssociatedGiftIds(readAssociatedGiftIdsForPledge(pledge.getId()));
            loadCustomFields(pledge.getPerson());
            loadCustomFields(pledge.getSelectedAddress());
            loadCustomFields(pledge.getSelectedPhone());
		}

		return pledge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pledge> readPledgesByConstituentId(Long constituentId) {
		if (logger.isTraceEnabled()) {
			logger.trace("readCommitmentsByConstituentIdType: constituentId = "	+ constituentId);
		}
		Map<String, Object> params = setupParams();
		params.put("constituentId", constituentId);
		return getSqlMapClientTemplate().queryForList("SELECT_PLEDGES_BY_CONSTITUENT_ID", params);
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedPledgesByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_PLEDGES_BY_CONSTITUENT_ID_PAGINATED", params);
        readAmountPaidForPledge(rows);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("PLEDGES_BY_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> findNotCancelledPledges(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("findNotCancelledPledges: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_NOT_CANCELLED_PLEDGES", params);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Pledge> searchPledges(Map<String, Object> searchParams) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchPledges: searchParams = " + searchParams);
        }
		Map<String, Object> params = setupParams();
		QueryUtil.translateSearchParamsToIBatisParams(searchParams, params,	new SearchFieldMapperFactory().getMapper(EntityType.pledge).getMap());

		return getSqlMapClientTemplate().queryForList("SELECT_PLEDGE_BY_SEARCH_TERMS", params);
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<DistributionLine> findDistributionLinesForPledges(List<String> pledgeIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findDistributionLinesForPledges: pledgeIds = " + pledgeIds);
        }
        Map<String, Object> params = setupParams();
        params.put("pledgeIds", pledgeIds);
        return getSqlMapClientTemplate().queryForList("SELECT_DISTRO_LINE_BY_PLEDGE_ID", params);
	}
    
    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedGiftIdsForPledge(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedGiftIdsForPledge: pledgeId = " + pledgeId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("pledgeId", pledgeId);
        return getSqlMapClientTemplate().queryForList("SELECT_PLEDGE_GIFT_BY_PLEDGE_ID", paramMap);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void readAmountPaidForPledge(List<Pledge> pledges) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAmountPaidForPledge:");
        }
        if (pledges != null) {
            Map<String, Object> paramMap = setupParams();
            paramMap.put("pledges", pledges);
            
            Map pledgePaidAmounts = getSqlMapClientTemplate().queryForMap("SELECT_PAID_AMOUNT_BY_PLEDGE_ID", paramMap, "pledgeId", "amountPaid"); 
            Map pledgeAdjustedAmounts = getSqlMapClientTemplate().queryForMap("SELECT_ADJUSTED_AMOUNT_BY_PLEDGE_ID", paramMap, "pledgeId", "amountPaid");
            
            for (Pledge thisPledge : pledges) {
                BigDecimal paidAmount = null;
                BigDecimal adjustedAmount = null;
                if (pledgePaidAmounts != null && pledgePaidAmounts.containsKey(thisPledge.getId())) {
                    paidAmount = (BigDecimal)pledgePaidAmounts.get(thisPledge.getId());
                }
                if (pledgeAdjustedAmounts != null && pledgeAdjustedAmounts.containsKey(thisPledge.getId())) {
                    adjustedAmount = (BigDecimal)pledgeAdjustedAmounts.get(thisPledge.getId());
                }
                if (paidAmount == null) {
                    paidAmount = BigDecimal.ZERO;
                }
                if (adjustedAmount == null) {
                    adjustedAmount = BigDecimal.ZERO;
                }
                thisPledge.setAmountPaid(paidAmount.add(adjustedAmount));
            }
        }
    }
}
