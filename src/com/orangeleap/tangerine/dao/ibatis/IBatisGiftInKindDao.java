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

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.GiftInKindDao;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKindDetail;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("giftInKindDAO")
public class IBatisGiftInKindDao extends AbstractIBatisDao implements GiftInKindDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisGiftInKindDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public GiftInKind maintainGiftInKind(GiftInKind giftInKind) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainGiftInKind: giftInKindId = " + giftInKind.getId());
        }
        GiftInKind aGiftInKind = (GiftInKind) insertOrUpdate(giftInKind, "GIFT_IN_KIND");

        /* Delete Details first */
        getSqlMapClientTemplate().delete("DELETE_GIFT_IN_KIND_DETAIL", aGiftInKind.getId());

        /* Then Insert Details */
        if (aGiftInKind.getDetails() != null) {
            for (GiftInKindDetail detail : aGiftInKind.getDetails()) {
                detail.resetIdToNull();
                detail.setGiftInKindId(aGiftInKind.getId());
                insertOrUpdate(detail, "GIFT_IN_KIND_DETAIL");
            }
        }
        return aGiftInKind;
    }

    @Override
    public GiftInKind readGiftInKindById(Long giftInKindId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftInKindById: giftInKindId = " + giftInKindId);
        }
        Map<String, Object> params = setupParams();
        params.put("id", giftInKindId);
        return (GiftInKind) getSqlMapClientTemplate().queryForObject("SELECT_GIFT_IN_KIND_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GiftInKind> readGiftsInKindByConstituentId(Long constituentId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("SELECT_GIFTS_IN_KIND_BY_CONSTITUENT_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult readPaginatedGiftsInKindByConstituentId(Long constituentId, SortInfo sortinfo) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPaginatedGiftsInKindByConstituentId: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        sortinfo.addParams(params);

        params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("SELECT_GIFTS_IN_KIND_BY_CONSTITUENT_ID_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("GIFTS_IN_KIND_BY_CONSTITUENT_ID_ROWCOUNT", params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }

}
