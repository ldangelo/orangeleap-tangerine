package com.orangeleap.tangerine.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.AdjustedGiftDao;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository("adjustedGiftDAO")
public class IBatisAdjustedGiftDao extends AbstractPaymentInfoEntityDao<AdjustedGift> implements AdjustedGiftDao {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    protected IBatisAdjustedGiftDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public AdjustedGift maintainAdjustedGift(final AdjustedGift adjustedGift) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainAdjustedGift: adjustedGiftId = " + adjustedGift.getId());
        }
        AdjustedGift aAdjustedGift = (AdjustedGift)insertOrUpdate(adjustedGift, "ADJUSTED_GIFT");

        /* Delete DistributionLines first */
        getSqlMapClientTemplate().delete("DELETE_DISTRO_LINE_BY_ADJUSTED_GIFT_ID", aAdjustedGift.getId());
        /* Then insert DistributionLines */
        insertDistributionLines(aAdjustedGift, "adjustedGiftId");

        return aAdjustedGift;
    }

    @Override
    public AdjustedGift readAdjustedGiftById(final Long adjustedGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftById: adjustedGiftId = " + adjustedGiftId);
        }
        final Map<String, Object> params = setupParams();
        params.put(StringConstants.ADJUSTED_GIFT_ID, adjustedGiftId);
        AdjustedGift adjustedGift = (AdjustedGift) getSqlMapClientTemplate().queryForObject("SELECT_ADJUSTED_GIFT_BY_ID", params);

        if (adjustedGift != null) {
            loadDistributionLinesCustomFields(adjustedGift);
            loadCustomFields(adjustedGift.getConstituent());
            loadCustomFields(adjustedGift.getAddress());
            loadCustomFields(adjustedGift.getPhone());
        }
        return adjustedGift;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(final Long originalGiftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsForOriginalGiftId: originalGiftId = " + originalGiftId);
        }
        final Map<String, Object> params = setupParams();
        params.put(StringConstants.ORIGINAL_GIFT_ID, originalGiftId);
        return getSqlMapClientTemplate().queryForList("SELECT_ADJUSTED_GIFTS_BY_ORIGINAL_GIFT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Long, Long> countAdjustedGiftsByOriginalGiftId(final List<Long> originalGiftIds) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAdjustedGiftsForOriginalGiftId: originalGiftIds = " + originalGiftIds);
        }
        final Map<String, Object> params = setupParams();
        params.put("giftIds", originalGiftIds);
        return getSqlMapClientTemplate().queryForMap("COUNT_ADJUSTED_GIFTS_BY_ORIGINAL_GIFT_ID", params, "giftId", "adjustedGiftCount");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AdjustedGift> readAllAdjustedGiftsByConstituentGiftId(Long constituentId, Long giftId, String sortPropertyName, String direction,
                                                                      int start, int limit, Locale locale) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAllAdjustedGiftsByConstituentGiftId: constituentId = " + constituentId + " giftId = " + giftId + " sortPropertyName = " + sortPropertyName +
                    " direction = " + direction + " start = " + start + " limit = " + limit);
        }
        Map<String, Object> params = setupSortParams(StringConstants.ADJUSTED_GIFT, "ADJUSTED_GIFT.ADJUSTED_GIFT_LIST_RESULT",
                sortPropertyName, direction, start, limit, locale);
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        params.put(StringConstants.GIFT_ID, giftId);

        return getSqlMapClientTemplate().queryForList("SELECT_LIMITED_ADJUSTED_GIFTS_BY_CONSTITUENT_GIFT_ID", params);
    }

    @Override
    public int readCountByConstituentGiftId(Long constituentId, Long giftId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCountByConstituentGiftId: constituentId = " + constituentId + " giftId = " + giftId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        params.put(StringConstants.GIFT_ID, giftId);
        return (Integer) getSqlMapClientTemplate().queryForObject("SELECT_ADJUSTED_GIFTS_COUNT_BY_CONSTITUENT_GIFT_ID", params);
    }

    @Override
    public BigDecimal readTotalAdjustedAmountByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readTotalAdjustedAmountByConstituentId: constituentId = " + constituentId);
        }
        Map<String,Object> params = setupParams();
        params.put(StringConstants.CONSTITUENT_ID, constituentId);
        return (BigDecimal) getSqlMapClientTemplate().queryForObject("SELECT_SUM_ADJUSTED_GIFTS_AMOUNT_BY_CONSTITUENT_ID", params);
    }
}
