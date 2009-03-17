package com.orangeleap.tangerine.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.AuditDao;
import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.web.common.PaginatedResult;

/** 
 * Corresponds to the AUDIT table
 */
@Repository("auditDAO")
public class IBatisAuditDao extends AbstractIBatisDao implements AuditDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisAuditDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Audit> allAuditHistoryForSite() {
        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForSite:");
        }
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_SITE", setupParams());
	}

    @SuppressWarnings("unchecked")
    @Override
    public PaginatedResult allAuditHistoryForSite(String sortColumn, String dir, int start, int limit) {

        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForSite:");
        }

        Map<String, Object> params = setupParams();
        params.put("sortColumn", sortColumn);
        params.put("sortDir", dir);
        params.put("offset", start);
        params.put("limit", limit);

        List rows = getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_SITE_PAGINATED", params);
        Long count = (Long)getSqlMapClientTemplate().queryForObject("AUDIT_HISTORY_FOR_SITE_ROWCOUNT",params);
        PaginatedResult resp = new PaginatedResult();
        resp.setRows(rows);
        resp.setRowCount(count);
        return resp;
    }


    @SuppressWarnings("unchecked")
	@Override
	public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForEntity: entityTypeDisplay = " + entityTypeDisplay + " objectId = " + objectId);
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

        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForEntity: entityTypeDisplay = " + entityTypeDisplay + " objectId = " + objectId);
        }

        Map<String, Object> params = setupParams();
        params.put("entityType", entityTypeDisplay);
        params.put("objectId", objectId);
        params.put("sortColumn", sortColumn);
        params.put("sortDir", dir);
        params.put("offset", start);
        params.put("limit", limit);

        List rows = getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_ENTITY_PAGINATED", params);
        Long count = (Long) getSqlMapClientTemplate().queryForObject("AUDIT_HISTORY_FOR_ENTITY_ROWCOUNT",params);
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
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("sortColumn", sortColumn);
        params.put("sortDir", dir);
        params.put("offset", start);
        params.put("limit", limit);

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
            logger.debug("auditObject:"+audit.getEntityType()+" - "+audit.getObjectId());
        }
        
        getSqlMapClientTemplate().insert("INSERT_AUDIT", audit);
        return audit;
	}
}