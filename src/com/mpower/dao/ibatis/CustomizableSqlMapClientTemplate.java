package com.mpower.dao.ibatis;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.dao.DataAccessException;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mpower.domain.model.AbstractCustomizableEntity;

import java.util.Map;
import java.util.List;

/**
 * Extends the base SqlMapClientTemplate to know what to do with entities
 * which which extend from AbstractCustomizableEntity. The "getter" methods
 * of this class which check for the type of object being returned, and if it
 * derives from AbstractCustomizableEntity, it will also load the custom fields
 * for the entity.  All the queryForXXX() methods are overriden with the logic to
 * load custom fields.
 *
 * @version 1.0
 */
public class CustomizableSqlMapClientTemplate extends SqlMapClientTemplate {

    public CustomizableSqlMapClientTemplate(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @Override
    public Map queryForMap(String statementName, Object parameterObject, String keyProperty) throws DataAccessException {
        Map ret = super.queryForMap(statementName, parameterObject, keyProperty);
        for (Object key : ret.keySet()) {
            loadCustomFields(ret.get(key));
        }
        return ret;
    }

    @Override
    public Map queryForMap(String statementName, Object parameterObject, String keyProperty, String valueProperty) throws DataAccessException {
        Map ret = super.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
        for (Object key : ret.keySet()) {
            loadCustomFields(ret.get(key));
        }
        return ret;
    }

    @Override
    public List queryForList(String statementName, Object parameterObject, int skipResults, int maxResults) throws DataAccessException {
        List ret = super.queryForList(statementName, parameterObject, skipResults, maxResults);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @Override
    public List queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
        List ret = super.queryForList(statementName, skipResults, maxResults);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @Override
    public List queryForList(String statementName, Object parameterObject) throws DataAccessException {
        List ret = super.queryForList(statementName, parameterObject);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @Override
    public List queryForList(String statementName) throws DataAccessException {
        List ret = super.queryForList(statementName);

        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @Override
    public Object queryForObject(String statementName, Object parameterObject, Object resultObject) throws DataAccessException {
        Object ret = super.queryForObject(statementName, parameterObject, resultObject);
        loadCustomFields(ret);
        return ret;
    }

    @Override
    public Object queryForObject(String statementName, Object parameterObject) throws DataAccessException {
        Object ret = super.queryForObject(statementName, parameterObject);
        loadCustomFields(ret);
        return ret;
    }

    @Override
    public Object queryForObject(String statementName) throws DataAccessException {
        Object ret = super.queryForObject(statementName);
        loadCustomFields(ret);
        return ret;
    }

    @Override
    public Object insert(String statementName, Object parameterObject) throws DataAccessException {

        Object ret = super.insert(statementName, parameterObject);
        saveCustomFields(parameterObject);
        return ret;
    }

    @Override
    public int update(String statementName, Object parameterObject) throws DataAccessException {
        int ret = super.update(statementName, parameterObject);
        saveCustomFields(parameterObject);
        return ret;
    }

    @Override
    public void update(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
        super.update(statementName, parameterObject, requiredRowsAffected);
        saveCustomFields(parameterObject);
    }

    @Override
    public int delete(String statementName, Object parameterObject) throws DataAccessException {
        int ret = super.delete(statementName, parameterObject);
        deleteCustomFields(parameterObject);
        return ret;
    }

    @Override
    public void delete(String statementName, Object parameterObject, int requiredRowsAffected) throws DataAccessException {
        super.delete(statementName, parameterObject, requiredRowsAffected);
        deleteCustomFields(parameterObject);
    }

    /**
     * Helper method which checks if the object is an AbstractCustomizableEntity,
     * and if so, loads the CustomFields
     *
     * @param entity
     */
    private void loadCustomFields(Object entity) {

        if (entity != null && entity instanceof AbstractCustomizableEntity) {

            AbstractCustomizableEntity custom = (AbstractCustomizableEntity) entity;
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this);

            custom.setCustomFieldMap(helper.readCustomFields(custom.getId(), custom.getType()));
        }
    }

    private void saveCustomFields(Object entity) {

        if (entity != null && entity instanceof AbstractCustomizableEntity) {

            AbstractCustomizableEntity custom = (AbstractCustomizableEntity) entity;
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this);
            helper.maintainCustomFields(custom.getCustomFieldMap());
        }
    }

    private void deleteCustomFields(Object entity) {
        if (entity != null && entity instanceof AbstractCustomizableEntity) {

            AbstractCustomizableEntity custom = (AbstractCustomizableEntity) entity;
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this);
            helper.deleteCustomFields( custom.getCustomFieldMap());
        }
    }

}
