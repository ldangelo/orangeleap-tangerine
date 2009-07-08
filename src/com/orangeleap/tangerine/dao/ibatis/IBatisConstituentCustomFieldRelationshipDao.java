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

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.ConstituentCustomFieldRelationshipDao;
import com.orangeleap.tangerine.domain.customization.ConstituentCustomFieldRelationship;

/** 
 * Corresponds to the CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP table
 */
@Repository("constituentCustomFieldRelationshipDAO")
public class IBatisConstituentCustomFieldRelationshipDao extends AbstractIBatisDao implements ConstituentCustomFieldRelationshipDao {
	
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisConstituentCustomFieldRelationshipDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    
    @Override
    public ConstituentCustomFieldRelationship readById(Long id) {
        if (logger.isTraceEnabled()) {
            logger.trace("readById: id = " + id);
        }
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (ConstituentCustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_ID", params);
    }
    
    @Override
    public ConstituentCustomFieldRelationship maintainConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship constituentCustomFieldRelationship) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainConstituentCustomFieldRelationship: id = " + constituentCustomFieldRelationship.getId());
        }
        constituentCustomFieldRelationship.setSiteName(getSiteName());
        return (ConstituentCustomFieldRelationship)insertOrUpdate(constituentCustomFieldRelationship, "CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP");
    }

	@Override
	public ConstituentCustomFieldRelationship readByConstituentFieldDefinitionCustomFieldIds(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("readByConstituentFieldDefinitionCustomFieldIds: constituentId = " + constituentId + " fieldDefinitionId = " + fieldDefinitionId + " customFieldValue = " + customFieldValue + " customFieldStartDate = " + customFieldStartDate);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("customFieldValue", customFieldValue);
        params.put("customFieldStartDate", customFieldStartDate);
        return (ConstituentCustomFieldRelationship)getSqlMapClientTemplate().queryForObject("SELECT_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_CONSTITUENT_AND_FIELD_DEFINITION_AND_CUSTOM_FIELD", params);
	}


	@Override
	public void deleteConstituentCustomFieldRelationship(Long constituentId, String fieldDefinitionId, String customFieldValue, Date customFieldStartDate) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteConstituentCustomFieldRelationship: constituentId = " + constituentId + " fieldDefinitionId = " + fieldDefinitionId + " customFieldValue = " + customFieldValue + " customFieldStartDate = " + customFieldStartDate);
        }
        Map<String, Object> params = setupParams();
        params.put("constituentId", constituentId);
        params.put("fieldDefinitionId", fieldDefinitionId);
        params.put("customFieldValue", customFieldValue);
        params.put("customFieldStartDate", customFieldStartDate);
        getSqlMapClientTemplate().delete("DELETE_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP", params);
	}

	@Override
	public void deleteConstituentCustomFieldRelationship(ConstituentCustomFieldRelationship ccr) {
        if (logger.isTraceEnabled()) {
            logger.trace("deleteConstituentCustomFieldRelationship: id = " + ccr.getId());
        }
        getSqlMapClientTemplate().delete("DELETE_CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP_BY_ID", ccr);
	}
}