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
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.List;
import java.util.Map;

/**
 * Extends the base SqlMapClientTemplate to know what to do with entities
 * which which extend from AbstractCustomizableEntity. The "getter" methods
 * of this class which check for the type of object being returned, and if it
 * derives from AbstractCustomizableEntity, it will also load the custom fields
 * for the entity.  All the queryForXXX() methods are overriden with the logic to
 * load custom fields.<br/>
 * This class will also correctly handle insertions and deletions in cases
 * where the parameterObject is an AbstractCustomizableEntity. In situations
 * where this is not the case, the Entity is responsible for deleting, updating
 * or inserting the customfields itself.
 *
 * @version 1.0
 */


public class CustomizableSqlMapClientTemplate extends SqlMapClientTemplate {

    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public CustomizableSqlMapClientTemplate(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map queryForMap(String statementName, Object parameterObject, String keyProperty) throws DataAccessException {
        Map ret = super.queryForMap(statementName, parameterObject, keyProperty);
        for (Object key : ret.keySet()) {
            loadCustomFields(ret.get(key));
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map queryForMap(String statementName, Object parameterObject, String keyProperty, String valueProperty) throws DataAccessException {
        Map ret = super.queryForMap(statementName, parameterObject, keyProperty, valueProperty);
        for (Object key : ret.keySet()) {
            loadCustomFields(ret.get(key));
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List queryForList(String statementName, Object parameterObject, int skipResults, int maxResults) throws DataAccessException {
        List ret = super.queryForList(statementName, parameterObject, skipResults, maxResults);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List queryForList(String statementName, int skipResults, int maxResults) throws DataAccessException {
        List ret = super.queryForList(statementName, skipResults, maxResults);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List queryForList(String statementName, Object parameterObject) throws DataAccessException {
        List ret = super.queryForList(statementName, parameterObject);
        for (Object obj : ret) {
            loadCustomFields(obj);
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
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
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this, this.applicationContext);

            custom.setCustomFieldMap(helper.readCustomFields(custom));
        }

        if (entity != null && entity instanceof AbstractCommunicatorEntity) {
            AbstractCommunicatorEntity comm = (AbstractCommunicatorEntity) entity;
            comm.setCommunicationFields(applicationContext);
        }
    }

    private void saveCustomFields(Object entity) {

        if (entity != null && entity instanceof AbstractCustomizableEntity) {

            AbstractCustomizableEntity custom = (AbstractCustomizableEntity) entity;
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this, this.applicationContext);
            helper.maintainCustomFields(custom);
        }
    }

    private void deleteCustomFields(Object entity) {
        if (entity != null && entity instanceof AbstractCustomizableEntity) {

            AbstractCustomizableEntity custom = (AbstractCustomizableEntity) entity;
            IBatisCustomFieldHelper helper = new IBatisCustomFieldHelper(this, this.applicationContext);
            helper.deleteCustomFields(custom);
        }
    }

}
