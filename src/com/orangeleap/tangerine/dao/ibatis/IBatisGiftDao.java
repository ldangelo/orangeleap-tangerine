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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.GiftDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

@Repository("giftDAO")
public class IBatisGiftDao extends AbstractPaymentInfoEntityDao<Gift> implements GiftDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Gift maintainGift(final Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainGift: giftId = " + gift.getId());
        }
        Gift aGift = (Gift) insertOrUpdate(gift, "GIFT");

        /* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_GIFT_ID", aGift.getId());
        /* Then insert DistributionLines */
        insertDistributionLines(aGift, "giftId");

        deleteInsertAssociatedPledges(aGift);
        deleteInsertAssociatedRecurringGifts(aGift);

        return aGift;
    }

    @Override
    public Gift readGiftById(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftById: giftId = " + giftId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftId);
        Gift gift = (Gift) getSqlMapClientTemplate().queryForObject("SELECT_GIFT_BY_ID", params);

        loadDistributionLinesCustomFields(gift);
        if (gift != null) {
            gift.setAssociatedPledgeIds(readAssociatedPledgeIdsForGift(giftId));
            if (gift.getAssociatedPledgeIds() == null || gift.getAssociatedPledgeIds().isEmpty()) {
                gift.setAssociatedRecurringGiftIds(readAssociatedRecurringGiftIdsForGift(giftId));
            } else {
                gift.setAssociatedRecurringGiftIds(new ArrayList<Long>(0)); // default set
            }
            loadCustomFields(gift.getConstituent());
            loadCustomFields(gift.getAddress());
            loadCustomFields(gift.getPhone());
        }
        return gift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readMonetaryGiftsByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult readPaginatedGiftListByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedGiftListByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

        params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("GIFTS_BY_CONSTITUENT_ID_ROWCOUNT", params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<Gift> searchGifts(Map<String, Object> parameters, String sortPropertyName, String direction, int start, int limit, Locale locale) {
        Map<String, Object> params = setupSortParams(StringConstants.GIFT, "GIFT.GIFT_SEARCH_RESULT", sortPropertyName, direction, start, limit, locale);
        List<Map<String,Object>> searchColumnList = setupSearchParams(parameters, PropertyAccessorFactory.forBeanPropertyAccess(new Gift()), "GIFT.GIFT_SEARCH_RESULT");
        params.put("searchTerms", searchColumnList);
    	return getSqlMapClientTemplate().queryForList("SELECT_GIFT_BY_SEARCH_TERMS", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> searchGifts(Map<String, Object> parameters) {
        Map<String, Object> params = setupParams();
        List<Map<String,Object>> searchColumnList = setupSearchParams(parameters, PropertyAccessorFactory.forBeanPropertyAccess(new Gift()), "GIFT.GIFT_SEARCH_RESULT");
        params.put("searchTerms", searchColumnList);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFT_BY_SEARCH_TERMS", params);
    }

    @Override
    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeMajorDonor: constituentId = " + constituentId + " beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        BigDecimal bd = (BigDecimal) getSqlMapClientTemplate().queryForObject("ANALYZE_FOR_MAJOR_DONOR", params);
        if (bd == null) {
            return 0.00d;
        }
        return bd.doubleValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("analyzeLapsedDonor:  beginDate = " + beginDate + " currentDate = " + currentDate);
        }
        Map<String, Object> params = setupParams();
        params.put("beginDate", beginDate);
        params.put("currentDate", currentDate);
        return getSqlMapClientTemplate().queryForList("ANALYZE_FOR_LAPSED_DONOR", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readAllGiftsBySite() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsBySite:");
        }
        Map<String, Object> params = setupParams();
        return getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_SITE", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByDateRange:");
        }
        Map<String, Object> params = setupParams();
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        List list = getSqlMapClientTemplate().queryForList("SELECT_ALL_GIFTS_BY_DATE_RANGE", params);
        if (list.size() > 5000) {
            throw new RuntimeException("Selection too large, reduce selection range."); // Note this needs to be one less than the 5001 in gift.xml
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedPledgeIdsForGift(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedPledgeIdsForGift: giftId = " + giftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("giftId", giftId);
        return getSqlMapClientTemplate().queryForList("SELECT_PLEDGE_GIFT_BY_GIFT_ID", paramMap);
    }

    @SuppressWarnings("unchecked")
    protected List<Long> readAssociatedRecurringGiftIdsForGift(Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAssociatedRecurringGiftIdsForGift: giftId = " + giftId);
        }
        Map<String, Object> paramMap = setupParams();
        paramMap.put("giftId", giftId);
        return getSqlMapClientTemplate().queryForList("SELECT_RECURRING_GIFT_GIFT_BY_GIFT_ID", paramMap);
    }

    protected void deleteInsertAssociatedPledges(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteInsertAssociatedPledges: giftId = " + gift.getId());
        }
        getSqlMapClientTemplate().delete("DELETE_PLEDGE_GIFT_BY_GIFT_ID", gift.getId());
        if (gift.getAssociatedPledgeIds() != null) {
            for (Long associatedPledgeId : gift.getAssociatedPledgeIds()) {
                Map<String, Long> paramMap = new HashMap<String, Long>(2);
                paramMap.put("pledgeId", associatedPledgeId);
                paramMap.put("giftId", gift.getId());
                getSqlMapClientTemplate().insert("INSERT_PLEDGE_GIFT", paramMap);
            }
        }
    }

    protected void deleteInsertAssociatedRecurringGifts(Gift gift) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteInsertAssociatedRecurringGifts: giftId = " + gift.getId());
        }
        getSqlMapClientTemplate().delete("DELETE_RECURRING_GIFT_GIFT_BY_GIFT_ID", gift.getId());
        if (gift.getAssociatedRecurringGiftIds() != null) {
            for (Long associatedRecurringGiftId : gift.getAssociatedRecurringGiftIds()) {
                Map<String, Long> paramMap = new HashMap<String, Long>(2);
                paramMap.put("recurringGiftId", associatedRecurringGiftId);
                paramMap.put("giftId", gift.getId());
                getSqlMapClientTemplate().insert("INSERT_RECURRING_GIFT_GIFT", paramMap);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readGiftsBySegmentationReportIds(Set<Long> reportIds, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsBySegmentationReportIds: reportIds = " + reportIds + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.GIFT, "GIFT.GIFT_LIST_RESULT",
                sortPropertyName, direction, start, limit, locale);
        params.put("reportIds", new ArrayList<Long>(reportIds));

        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_BY_SEGMENTATION_RESULT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Gift> readAllGiftsByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllGiftsByConstituentId: constituentId = " + constituentId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.GIFT, "GIFT.GIFT_LIST_RESULT",
                sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_GIFTS_BY_CONSTITUENT_ID", params);
    }

    @Override
    public int readCountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_GIFTS_COUNT_BY_CONSTITUENT_ID", params);
    }

	@Override
	public Gift readFirstOrLastGiftByConstituent(Long constituentId, Date fromDate, Date toDate, GiftType giftType, String giftStatus, boolean first) {
        if (logger.isTraceEnabled()) {
            logger.trace("readFirstOrLastGiftByConstituent: constituentId = " + constituentId + " fromDate = " + fromDate +
                    " toDate = " + toDate + " first = "+first);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fromDate", fromDate);
        params.put("toDate", toDate);
        params.put("giftType", giftType);
        params.put("giftStatus", giftStatus);
        if (first) params.put("first", first);
        return (Gift)getSqlMapClientTemplate().queryForObject("SELECT_FIRST_OR_LAST_GIFT_BY_CONSTITUENT", params);
	}

}
