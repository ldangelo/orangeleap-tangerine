package com.orangeleap.tangerine.dao.ibatis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.GeneratedId;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class AbstractIBatisDao extends SqlMapClientDaoSupport implements ApplicationContextAware {

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;
    protected ApplicationContext applicationContext;

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    protected String getSiteName() {
        return tangerineUserHelper.lookupUserSiteName();
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) {
    	((CustomizableSqlMapClientTemplate)this.getSqlMapClientTemplate()).setApplicationContext(applicationContext);
    }
    
    public ApplicationContext getApplicationContext() {
    	return applicationContext;
    }

    protected AbstractIBatisDao(SqlMapClient sqlMapClient) {
       setSqlMapClientTemplate(new CustomizableSqlMapClientTemplate(sqlMapClient));
    }

    protected Map<String, Object> setupParams() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(StringConstants.SITE_NAME, getSiteName());
        return params;
    }

    protected Map<String, Object> setupSortParams(String sortColumn, String dir, int start, int limit) {
        Map<String, Object> params = setupParams();
        params.put("sortColumn", oneWord(sortColumn));
        params.put("sortDir", oneWord(dir));
        params.put("offset", start);
        params.put("limit", limit);
        return params;
    }

    // Ensures literal parameter value is one word to avoid SQL injection.
    // This should be used on all passed parameters that use the literal $ syntax, unless the value is safely system-generated.
    public static String oneWord(String literalDollarParameterValue) {
        if (literalDollarParameterValue != null && literalDollarParameterValue.contains(" ")) throw new RuntimeException("Invalid parameter");
        return check(literalDollarParameterValue);
    }

    // Ensures literal parameter value contains no semicolons or escaped chars to avoid SQL injection.
    // This should be used on all passed parameters that use the literal $ IBatis syntax.
    public static String check(String literalDollarParameterValue) {
        if (literalDollarParameterValue != null && (literalDollarParameterValue.contains(";") || literalDollarParameterValue.contains("\\"))) throw new RuntimeException("Invalid parameter");
        return literalDollarParameterValue;
    }

    /**
     * Update if exists, otherwise insert. Useful for maintain* methods.
     * Sets the generated IDs for inserts.
     * @param o
     * @param table
     * @return object inserted or updated
     */
    protected GeneratedId insertOrUpdate(final GeneratedId o, final String table) {
        if (logger.isTraceEnabled()) {
            logger.trace("insertOrUpdate: id = " + o.getId() + " table = " + table);
        }
    	setSite(o);

        if (o instanceof AbstractEntity) {
            ((AbstractEntity)o).prePersist();
        }
        if (o.getId() == null || o.getId() <= 0) {
            Long generatedId = (Long)getSqlMapClientTemplate().insert("INSERT_" + table, o);
            if (logger.isDebugEnabled()) {
                logger.debug("insertOrUpdate: generatedId = " + generatedId + " for o = " + o.getClass().getName() + " table = " + table);
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

    
    public final void loadCustomFields(AbstractCustomizableEntity entity) {
        if (entity != null && entity.getId() != null && entity.getId() > 0) {
            Map<String, CustomField> customFieldMap = getCustomFieldHelper().readCustomFields(entity);
            entity.setCustomFieldMap(customFieldMap);
        }
    }
    
    protected IBatisCustomFieldHelper getCustomFieldHelper() {
        return new IBatisCustomFieldHelper(getSqlMapClientTemplate(), getApplicationContext());
    }
}
