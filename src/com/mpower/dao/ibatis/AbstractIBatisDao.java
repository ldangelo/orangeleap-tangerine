package com.mpower.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.domain.GeneratedId;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.util.StringConstants;
import com.mpower.util.TangerineUserHelper;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport {

    protected TangerineUserHelper tangerineUserHelper;

    @Resource(name="tangerineUserHelper")
    public void setTangerineUserHelper(TangerineUserHelper tangerineUserHelper) {
        this.tangerineUserHelper = tangerineUserHelper;
    }

    protected AbstractIBatisDao(SqlMapClient sqlMapClient) {
        setSqlMapClientTemplate( new CustomizableSqlMapClientTemplate(sqlMapClient));
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

    protected GeneratedId insert(final GeneratedId o, final String table) {
        if (logger.isDebugEnabled()) {
            logger.debug("insert: o = " + o + " table = " + table);
        }
        String insertId = "INSERT_" + table;
        Long generatedId = (Long)getSqlMapClientTemplate().insert(insertId, o);
        if (logger.isDebugEnabled()) {
            logger.debug("insert: generatedId = " + generatedId + " for o = " + o + " table = " + table);
        }
        o.setId(generatedId);
        return o;
    }

    /**
     * Update if exists, otherwise insert. Useful for maintain* methods.
     * Sets the generated IDs for inserts.
     * @param o
     * @param table
     * @return object inserted or updated
     */
    protected Object insertOrUpdate(final AbstractEntity o, final String table) {
        if (logger.isDebugEnabled()) {
            logger.debug("insertOrUpdate: o = " + o + " table = " + table);
        }
        o.prePersist();

    	Long id = o.getId();
    	if (id == null || id <= 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("insert " + table);
            }
        	String insertId = "INSERT_" + table;
        	Long generatedId = (Long)getSqlMapClientTemplate().insert(insertId, o);
            if (logger.isDebugEnabled()) {
                logger.debug("insertOrUpdate: generatedId = " + generatedId + " for o = " + o + " table = " + table);
            }
    	    o.setId(generatedId);
    	}
    	else {
            if (logger.isDebugEnabled()) {
                logger.debug("update " + table + ", id=" + id);
            }
        	String updateId = "UPDATE_" + table;
            getSqlMapClientTemplate().update(updateId, o);
    	}
        return o;
    }

    /**
     * Perform a batch insert or update.
     * <strong>NOTE:</strong> for INSERTS, IBatis will not be able to return back the generated IDs correctly, thus they are not set.
     * You will have to do a SELECT to get the generated IDs.
     * @param entities to batch update or insert
     * @param table
     * @return list
     */
    protected List<? extends AbstractEntity> batchInsertOrUpdate(final List<? extends AbstractEntity> entities, final String table) {
        if (logger.isDebugEnabled()) {
            logger.debug("batchInsertOrUpdate: entities = " + entities + " table = " + table);
        }
        if (entities != null && !entities.isEmpty()) {
            getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
                public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                    executor.startBatch();
                    for (AbstractEntity entity : entities) {
                        entity.prePersist();

                        if (entity.getId() == null || entity.getId() <= 0) {
                            executor.insert("INSERT_" + table, entity);
                        }
                        else {
                            executor.update("UPDATE_" + table, entity);
                        }
                    }
                    executor.executeBatch();
                    return null;
                }
            });
        }
        return entities;
    }
}
