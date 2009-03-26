package com.orangeleap.tangerine.dao.ibatis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport implements ApplicationContextAware {

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }
    
    protected ApplicationContext applicationContext;
    
    public void setApplicationContext(ApplicationContext applicationContext) {
    	((CustomizableSqlMapClientTemplate)this.getSqlMapClientTemplate()).setApplicationContext(applicationContext);
    }

    protected AbstractIBatisDao(SqlMapClient sqlMapClient) {
       setSqlMapClientTemplate(new CustomizableSqlMapClientTemplate(sqlMapClient));
    }

    protected Map<String, Object> setupParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(StringConstants.SITE_NAME, getSiteName());
        return params;
    }

    /**
     * Update if exists, otherwise insert. Useful for maintain* methods.
     * Sets the generated IDs for inserts.
     * @param o
     * @param table
     * @return object inserted or updated
     */
    protected GeneratedId insertOrUpdate(final GeneratedId o, final String table) {
        if (logger.isDebugEnabled()) {
            logger.debug("insertOrUpdate: o = " + o + " id = " + o.getId() + " table = " + table);
        }
    	setSite(o);

        if (o instanceof AbstractEntity) {
            ((AbstractEntity)o).prePersist();
        }
        if (o.getId() == null || o.getId() <= 0) {
            Long generatedId = (Long)getSqlMapClientTemplate().insert("INSERT_" + table, o);
            if (logger.isDebugEnabled()) {
                logger.debug("insertOrUpdate: generatedId = " + generatedId + " for o = " + o + " table = " + table);
            }
            o.setId(generatedId);
        }
        else {
            getSqlMapClientTemplate().update("UPDATE_" + table, o);
        }
        return o;
    }
    
    // For security, set site before persisting
    private void setSite(Object o) { 
    	for (Method m : o.getClass().getDeclaredMethods()) {
    		if (m.getName().equals("setSite")) {
    			try {
    			   m.invoke(o, new Object[]{new Site(getSiteName())});
    			} catch (Exception e) {
    				logger.error("Unable to set site");
    				throw new RuntimeException(e);
    			}
    		}
    	}
    }

    /**
     * Used to delete the custom fields associated with an Entity. This method
     * should be called when a delete operation is called and the paramater does
     * not contain the AbstractCustomizableEntity. Implementations need to call
     * this method to avoid creating orphan record in the Custom Field table
     * @param entity the Entity that is being deleted
     */
    public final void deleteCustomFields(AbstractCustomizableEntity entity) {
        IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(getSqlMapClientTemplate());
        helper.deleteCustomFields(entity.getCustomFieldMap());
    }
}
