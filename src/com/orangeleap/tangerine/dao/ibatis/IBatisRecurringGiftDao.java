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
import com.orangeleap.tangerine.dao.RecurringGiftDao;
import com.orangeleap.tangerine.dao.util.QueryUtil;
import com.orangeleap.tangerine.dao.util.search.SearchFieldMapperFactory;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("recurringGiftDAO")
public class IBatisRecurringGiftDao extends AbstractPaymentInfoEntityDao<RecurringGift> implements RecurringGiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisRecurringGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    private void loadCustomFieldsForEntities(RecurringGift recurringGift) {
        if (recurringGift != null) {
            loadDistributionLinesCustomFields(recurringGift);
            if (recurringGift.getId() != null) {
                recurringGift.setAssociatedGiftIds(readAssociatedGiftIdsForRecurringGift(recurringGift.getId()));
            }
            loadCustomFields(recurringGift.getConstituent());
            loadCustomFields(recurringGift.getAddress());
            loadCustomFields(recurringGift.getPhone());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses, long offset, int limit) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGifts: date = " + date + " statuses = " + statuses);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);
        params.put("offset", offset);
        params.put("limit", limit);

        List<RecurringGift> recurringGifts = getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_ON_OR_AFTER_DATE", params);
        if (recurringGifts != null) {
            for (RecurringGift recurringGift : recurringGifts) {
                loadCustomFieldsForEntities(recurringGift);
            }
        }
        return recurringGifts;
    }

    @Override
    public long readRecurringGiftsCount(Date date, List<String> statuses) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGiftsCount: date = " + date + " statuses = " + statuses);
        }
        Map<String, Object> params = setupParams();
        params.put("date", date);
        params.put("statuses", statuses);

        return (Long)getSqlMapClientTemplate().queryForObject("SELECT_RECURRING_GIFTS_ON_OR_AFTER_DATE_COUNT", params);
    }

    @Override
    public RecurringGift maintainRecurringGift(RecurringGift rg) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRecurringGift: recurringGiftId = " + rg.getId());
        }
		RecurringGift aRecurringGift = (RecurringGift)insertOrUpdate(rg, "RECURRING_GIFT");
		
		/* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_RECURRING_GIFT_ID", aRecurringGift.getId()); 
        /* Then Insert DistributionLines */
        insertDistributionLines(aRecurringGift, "recurringGiftId");
        return aRecurringGift;
    }
    
    /**
     * Updates the recurringGift AMOUNT_PAID, AMOUNT_REMAINING, and RECURRING_GIFT_STATUS fields ONLY
     * @param recurringGift
     */
    @Override
    public void maintainRecurringGiftAmountPaidRemainingStatus(RecurringGift recurringGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainRecurringGiftAmountPaidRemainingStatus: recurringGiftId = " + recurringGift.getId() + " amountPaid = " + recurringGift.getAmountPaid() + 
                    " amountRemaining = " + recurringGift.getAmountRemaining() + " recurringGiftStatus = " + recurringGift.getRecurringGiftStatus());
        }
        getSqlMapClientTemplate().update("UPDATE_RECURRING_GIFT_AMOUNT_PAID_REMAINING_STATUS", recurringGift);
    }
    

    @Override
    public void removeRecurringGift(RecurringGift rg) {
        if (logger.isTraceEnabled()) {
            logger.trace("removeRecurringGift: id = " + rg.getId());
        }
        Map<String, Object> params = setupParams();
        params.put("id", rg.getId());
        int rows = getSqlMapClientTemplate().delete("DELETE_RECURRING_GIFT", params);
        if (logger.isInfoEnabled()) {
            logger.info("removeRecurringGift: number of rows deleted = " + rows);
        }
    }
    
    @Override
    public RecurringGift readRecurringGiftById(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGiftById: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", recurringGiftId);
        RecurringGift recurringGift = (RecurringGift) getSqlMapClientTemplate().queryForObject("SELECT_RECURRING_GIFT_BY_ID", params);
        loadCustomFieldsForEntities(recurringGift);
        return recurringGift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readRecurringGiftsByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readRecurringGiftsByConstituentIdType: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_BY_CONSTITUENT_ID", params);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedRecurringGiftsByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

		params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFTS_BY_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("RECURRING_GIFTS_BY_CONSTITUENT_ID_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> searchParams) {
        if (logger.isTraceEnabled()) {
            logger.trace("searchRecurringGifts: searchParams = " + searchParams);
        }
        Map<String, Object> params = setupParams();
        QueryUtil.translateSearchParamsToIBatisParams(searchParams, params, new SearchFieldMapperFactory().getMapper(EntityType.recurringGift).getMap());

        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFT_BY_SEARCH_TERMS", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DistributionLine> findDistributionLinesForRecurringGifts(List<String> recurringGiftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("findDistributionLinesForRecurringGifts: recurringGiftIds = " + recurringGiftIds);
        }
        Map<String, Object> params = setupParams();
        params.put("recurringGiftIds", recurringGiftIds);
        return getSqlMapClientTemplate().queryForList("SELECT_DISTRO_LINE_BY_RECURRING_GIFT_ID", params);
    }
    
    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedGiftIdsForRecurringGift(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedGiftIdsForRecurringGift: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("recurringGiftId", recurringGiftId);
        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFT_GIFT_BY_RECURRING_GIFT_ID", paramMap);
    }
    
    @Override
    public BigDecimal readAmountPaidForRecurringGiftId(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAmountPaidForRecurringGiftId: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("recurringGiftId", recurringGiftId.toString());
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("SELECT_AMOUNT_PAID_BY_RECURRING_GIFT_ID", paramMap);
    }

    @Override
    public Long readPaymentsAppliedToRecurringGiftId(Long recurringGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaymentsAppliedToRecurringGiftId: recurringGiftId = " + recurringGiftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("recurringGiftId", recurringGiftId.toString()); // use string value to match custom field value
        return (Long) getSqlMapClientTemplate().queryForObject("SELECT_PAYMENTS_APPLIED_TO_RECURRING_GIFT_ID", paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<RecurringGift> readAllRecurringGiftsByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllRecurringGiftsByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.RECURRING_GIFT, "RECURRING_GIFT.RECURRING_GIFT_LIST_RESULT",
                sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_RECURRING_GIFTS_BY_CONSTITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_RECURRING_GIFTS_COUNT_BY_CONSTITUENT_ID", params);
    }
    
	@Override
	public RecurringGift readFirstOrLastRecurringGiftByConstituent(Long constituentId, Date fromDate, Date toDate, boolean first) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFirstOrLastRecurringGiftByConstituent: constituentId = " + constituentId + " fromDate = " + fromDate +
                    " toDate = " + toDate + " first = "+first);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        if (first) params.put("first", first);
        return (RecurringGift)getSqlMapClientTemplate().queryForObject("SELECT_FIRST_OR_LAST_RECURRING_GIFT_BY_CONSTITUENT", params);
	}

	@Override
	public RecurringGift readLargestRecurringGiftByConstituent(Long constituentId, Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readLargestRecurringGiftByConstituent: constituentId = " + constituentId + " fromDate = " + fromDate +
                    " toDate = " + toDate );
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        return (RecurringGift)getSqlMapClientTemplate().queryForObject("SELECT_LARGEST_RECURRING_GIFT_BY_CONSTITUENT", params);
	}


}


