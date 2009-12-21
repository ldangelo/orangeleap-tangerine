/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.dao.ibatis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.PledgeDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("pledgeDAO")
public class IBatisPledgeDao extends AbstractPaymentInfoEntityDao<Pledge> implements PledgeDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

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

    /**
     * Updates the pledge AMOUNT_PAID, AMOUNT_REMAINING, and PLEDGE_STATUS fields ONLY
     *
     * @param pledge
     */
    @Override
    public void maintainPledgeAmountPaidRemainingStatus(Pledge pledge) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainPledgeAmountPaidRemainingStatus: pledgeId = " + pledge.getId() + " amountPaid = " + pledge.getAmountPaid() +
                    " amountRemaining = " + pledge.getAmountRemaining() + " pledgeStatus = " + pledge.getPledgeStatus());
        }
        getSqlMapClientTemplate().update("UPDATE_PLEDGE_AMOUNT_PAID_REMAINING_STATUS", pledge);
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
            loadCustomFields(pledge.getConstituent());
            loadCustomFields(pledge.getAddress());
            loadCustomFields(pledge.getPhone());
        }

        return pledge;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> readPledgesByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCommitmentsByConstituentIdType: constituentId = " + constituentId);
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
        Long count = (Long) getSqlMapClientTemplate().queryForObject("PLEDGES_BY_CONSTITUENT_ID_ROWCOUNT", params);
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
        QueryUtil.translateSearchParamsToIBatisParams(searchParams, params, new SearchFieldMapperFactory().getMapper(EntityType.pledge).getMap());

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

    @Override
    public BigDecimal readAmountPaidForPledgeId(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAmountPaidForPledgeId: pledgeId = " + pledgeId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("pledgeId", pledgeId);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("SELECT_AMOUNT_PAID_BY_PLEDGE_ID", paramMap);
    }

    @Override
    public Long readPaymentsAppliedToPledgeId(Long pledgeId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentsAppliedToPledgeId: pledgeId = " + pledgeId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("pledgeId", pledgeId.toString()); // use string value to match custom field value
        return (Long) getSqlMapClientTemplate().queryForObject("SELECT_PAYMENTS_APPLIED_TO_PLEDGE_ID", paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> readPledges(Date date, List<String> statuses, long offset, int limit) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledges: date = " + date + " statuses = " + statuses);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);
        params.put("offset", offset);
        params.put("limit", limit);

        List<Pledge> pledges = getSqlMapClientTemplate().queryForList("SELECT_PLEDGES_ON_OR_AFTER_DATE", params);

        return pledges;
    }

    @Override
    public long readPledgesCount(Date date, List<String> statuses) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPledgesCount: date = " + date + " statuses = " + statuses);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);

        return (Long)getSqlMapClientTemplate().queryForObject("SELECT_PLEDGES_ON_OR_AFTER_DATE_COUNT", params);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Pledge> readAllPledgesByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllPledgesByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.PLEDGE, "PLEDGE.PLEDGE_RESULT_NO_DISTRO_LINES",
                sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_PLEDGES_BY_CONSTITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_PLEDGES_COUNT_BY_CONSTITUENT_ID", params);
    }
    
	@Override
	public Pledge readFirstOrLastPledgeByConstituent(Long constituentId, Date fromDate, Date toDate, boolean first) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFirstOrLastPledgeByConstituent: constituentId = " + constituentId + " fromDate = " + fromDate +
                    " toDate = " + toDate + " first = "+first);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        if (first) params.put("first", first);
        return (Pledge)getSqlMapClientTemplate().queryForObject("SELECT_FIRST_OR_LAST_PLEDGE_BY_CONSTITUENT", params);
	}

	@Override
	public Pledge readLargestPledgeByConstituent(Long constituentId, Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readLargestPledgeByConstituent: constituentId = " + constituentId + " fromDate = " + fromDate +
                    " toDate = " + toDate );
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        return (Pledge)getSqlMapClientTemplate().queryForObject("SELECT_LARGEST_PLEDGE_BY_CONSTITUENT", params);
	}


}
