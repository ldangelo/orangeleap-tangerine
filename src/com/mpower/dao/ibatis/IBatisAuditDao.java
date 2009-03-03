package com.mpower.dao.ibatis;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.AuditDao;
import com.mpower.domain.model.Audit;

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
	public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForEntity:");
        }
        Map<String, Object> params = setupParams();
        params.put("entityType", entityTypeDisplay);
        params.put("objectId", objectId);
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_ENTITY", params);
	}

    @SuppressWarnings("unchecked")
	@Override
	public List<Audit> auditHistoryForPerson(Long personId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readAuditHistoryForPerson:");
        }
        Map<String, Object> params = setupParams();
        params.put("personId", personId);
        return getSqlMapClientTemplate().queryForList("AUDIT_HISTORY_FOR_PERSON", params);
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