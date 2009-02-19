package com.mpower.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.dao.interfaces.FieldDao;
import com.mpower.domain.model.customization.FieldRelationship;

/** 
 * Corresponds to the FIELD tables
 */
@Repository("fieldDAO")
public class IBatisFieldDao extends AbstractIBatisDao implements FieldDao {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    public IBatisFieldDao(SqlMapClient sqlMapClient) {
        super();
        super.setSqlMapClient(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readMasterFieldRelationships(String masterFieldDefId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readMasterFieldRelationships: masterFieldDefId = " + masterFieldDefId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_MASTER_FIELD_DEF_ID", masterFieldDefId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FieldRelationship> readDetailFieldRelationships(String detailFieldDefId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readDetailFieldRelationships: detailFieldDefId = " + detailFieldDefId);
        }
        return getSqlMapClientTemplate().queryForList("SELECT_FIELD_REL_BY_DETAIL_FIELD_DEF_ID", detailFieldDefId);
    }
}