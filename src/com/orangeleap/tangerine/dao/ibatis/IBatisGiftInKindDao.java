package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;

@Repository("giftInKindDAO")
public class IBatisGiftInKindDao extends AbstractIBatisDao implements GiftInKindDao {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisGiftInKindDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public GiftInKind maintainGiftInKind(GiftInKind giftInKind) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainGiftInKind: giftInKind = " + giftInKind);
        }
        GiftInKind aGiftInKind = (GiftInKind) insertOrUpdate(giftInKind, "GIFT_IN_KIND");
        
        /* Delete Details first */
        getSqlMapClientTemplate().delete("DELETE_GIFT_IN_KIND_DETAIL", aGiftInKind.getId());
        
        /* Then Insert Details */
        if (aGiftInKind.getDetails() != null) {
            for (GiftInKindDetail detail : aGiftInKind.getDetails()) {
                detail.resetIdToNull();
                detail.setGiftInKindId(aGiftInKind.getId());
            }
        }
        batchInsertOrUpdate(aGiftInKind.getDetails(), "GIFT_IN_KIND_DETAIL");
        return aGiftInKind;
    }

    @Override
    public GiftInKind readGiftInKindById(Long giftInKindId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftInKindById: giftInKindId = " + giftInKindId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftInKindId);
        return (GiftInKind) getSqlMapClientTemplate().queryForObject("SELECT_GIFT_IN_KIND_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_IN_KIND_BY_CONSTITUENT_ID", params);
    }
}
