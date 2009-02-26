package com.mpower.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.AbstractCommunicationEntity;
import com.mpower.util.StringConstants;
import com.mpower.util.TangerineUserHelper;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport {
    
    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name="tangerineUserHelper")
    public void setTangerineUserHelper(TangerineUserHelper tangerineUserHelper) {
        this.tangerineUserHelper = tangerineUserHelper;
    }

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }
    
    protected Map<String, Object> setupParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(StringConstants.SITE_NAME, getSiteName());
        return params;
    }
    
    // Update if exists, otherwise insert.  
    // Useful for maintain* methods.
    protected Object insertOrUpdate(AbstractEntity o, String table) {
    
        if (logger.isDebugEnabled()) {
            logger.debug("insertOrUpdate: o = " + o + " table = " + table);
        }

        o.prePersist();
        
    	Long id = o.getId();
    	if (id == null || id == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("insert " + table);
            }
        	String insertId = "INSERT_" + table;
        	Long generatedId = (Long)getSqlMapClientTemplate().insert(insertId, o);
            if (logger.isDebugEnabled()) {
                logger.debug("insertOrUpdate: generatedId = " + generatedId + " for o = " + o + " table = " + table);
            }
    	    o.setId(generatedId);
    	} else {
            if (logger.isDebugEnabled()) {
                logger.debug("update " + table + ", id=" + id);
            }
        	String updateId = "UPDATE_" + table;
            getSqlMapClientTemplate().update(updateId, o);
    	}
        return o;
    }
    
}
