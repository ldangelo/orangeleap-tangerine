package com.mpower.dao.ibatis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mpower.service.impl.SessionServiceImpl;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected Map<String, Object> setupParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("siteName", SessionServiceImpl.lookupUserSiteName());
        return params;
    }
    
    // Update if exists, otherwise insert.  Object must have a getId() method.
    // Useful for maintain* methods.
    protected Object insertOrUpdate(Object o, String table) {
    	Long id = getId(o);
    	if (id == null || id.longValue() == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("insert " + table);
            }
        	String insertId = "INSERT_" + table;
        	getSqlMapClientTemplate().insert(insertId, o);
    	} else {
            if (logger.isDebugEnabled()) {
                logger.debug("update " + table + ", id=" + id);
            }
        	String updateId = "UPDATE_" + table;
            getSqlMapClientTemplate().update(updateId, o);
    	}
        return o;
    }
    
    private Long getId(Object o) {
    	try {
    		Method getId = o.getClass().getMethod("getId", new Class[0]);
			return (Long)getId.invoke(o, new Object[0]);
		} catch (Exception e) {
			return null;
		}
    }


}
