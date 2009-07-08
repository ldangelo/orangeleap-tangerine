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
import com.orangeleap.tangerine.dao.AuditDao;
import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Corresponds to the AUDIT table
 */
@Repository("auditDAO")
public class IBatisAuditDao extends AbstractIBatisDao implements AuditDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisAuditDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Audit> allAuditHistoryForSite() {
        if (logger.isTraceEnabled()) {
            logger.trace("readAuditHistoryForSite:");
        }
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_SITE", setupParams());
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult allAuditHistoryForSite(String sortColumn, String dir, int start, int limit) {

        if (logger.isTraceEnabled()) {
            logger.trace("readAuditHistoryForSite:");
        }

        Map<String, Object> params = setupSortParams(sortColumn, dir, start, limit);

        List rows = getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_SITE_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("AUDIT_HISTORY_FOR_SITE_ROWCOUNT", params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readAuditHistoryForEntity: entityTypeDisplay = " + entityTypeDisplay + " objectId = " + objectId);
        }
        Map<String, Object> params = setupParams();
        params.put("entityType", entityTypeDisplay);
        params.put("objectId", objectId);
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_ENTITY", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult auditHistoryForEntity(String entityTypeDisplay, Long objectId,
                                                 String sortColumn, String dir, int start, int limit) {

        if (logger.isTraceEnabled()) {
            logger.trace("readAuditHistoryForEntity: entityTypeDisplay = " + entityTypeDisplay + " objectId = " + objectId);
        }

        Map<String, Object> params = setupSortParams(sortColumn, dir, start, limit);
        params.put("entityType", oneWord(entityTypeDisplay));
        params.put("objectId", objectId);

        List rows = getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_ENTITY_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("AUDIT_HISTORY_FOR_ENTITY_ROWCOUNT", params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<Audit> auditHistoryForConstituent(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("auditHistoryForConstituent: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_CONSTITUENT", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult auditHistoryForConstituent(Long constituentId,
                                                      String sortColumn, String dir, int start, int limit) {

        if (logger.isDebugEnabled()) {
            logger.debug("auditHistoryForConstituent: constituentId = " + constituentId);
        }
        Map<String, Object> params = setupSortParams(sortColumn, dir, start, limit);
        params.put("constituentId", constituentId);

        List rows = getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_CONSTITUENT_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("AUDIT_HISTORY_FOR_CONSTITUENT_ROWCOUNT", params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }


    @Override
    public Audit auditObject(Audit audit) {

        audit.setSiteName(getSiteName());

        if (logger.isDebugEnabled()) {
            logger.debug("auditObject:" + audit.getEntityType() + " - " + audit.getObjectId());
        }

        getSqlMapClientTemplate().insert("INSERT_AUDIT", audit);
        return audit;
    }
}