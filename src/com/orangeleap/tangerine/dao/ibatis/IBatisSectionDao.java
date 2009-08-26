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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;

/**
 * Corresponds to the SECTION tables
 */
@Repository("sectionDAO")
public class IBatisSectionDao extends AbstractIBatisDao implements SectionDao {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Autowired
    public IBatisSectionDao(SqlMapClient sqlMapClient) {
        super(sqlMapClient);
    }
    
    @Override
    public SectionDefinition readSectionDefinition(Long id) {
        Map<String, Object> params = setupParams();
        params.put("id", id);
        return (SectionDefinition)getSqlMapClientTemplate().queryForObject("SELECT_SECTION_DEFINITION_BY_ID", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> readDistintSectionDefinitionsPageTypes() {
        return getSqlMapClientTemplate().queryForList("SELECT_DISTINCT_PAGE_TYPES");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> readDistintSectionDefinitionsRoles() {
    	List result = new ArrayList();
    	List<String> list = getSqlMapClientTemplate().queryForList("SELECT_DISTINCT_ROLES");
    	for (String s : list) {
    		String [] sa = s.split(",");
    		for (String role : sa) if (!result.contains(role)) result.add(role);
    	}
    	return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitions(PageType pageType, List<String> roles) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionDefinitions: pageType = " + pageType + " roles = " + roles);
        }
        Map<String, Object> params = setupParams();
        params.put("pageType", pageType);
        List<String> aroles = new ArrayList<String>();
        for (String role : roles) aroles.add(","+role+",");
        params.put("roles", aroles);
        List<SectionDefinition> list = getSqlMapClientTemplate().queryForList("SELECT_BY_PAGE_TYPE_SITE_ROLES", params);
        removeGenericOverriddenRows(list);
        return list;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitions(PageType pageType) {
    	
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionDefinitions: pageType = " + pageType );
        }
        Map<String, Object> params = setupParams();
        params.put("pageType", pageType);
        List<SectionDefinition> list = getSqlMapClientTemplate().queryForList("SELECT_BY_PAGE_TYPE_SITE", params);
        removeGenericOverriddenRows(list);
        return list;

    }
    
    private void removeGenericOverriddenRows(List<SectionDefinition> list) {
        Iterator<SectionDefinition> it = list.iterator();
        String last = "";
        while (it.hasNext()) {
           SectionDefinition sd = it.next();
           String key = sd.getPageType().getName()+sd.getSectionName()+sd.getRole();
           if (key.equals(last) && sd.getSite() == null) it.remove(); // sorted by SITE_NAME desc so this removes overridden generic values
           last = key;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readCustomizedSectionFields(Long sectionDefinitionId) {
        if (logger.isTraceEnabled()) {
            logger.trace("readCustomizedSectionFields: sectionDefinitionId = " + sectionDefinitionId);
        }
        Map<String, Object> params = setupParams();
        params.put("sectionDefinitionId", sectionDefinitionId);
        return getSqlMapClientTemplate().queryForList("SELECT_CUSTOMIZED_SEC_FLDS", params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readOutOfBoxSectionFields(PageType pageType, String sectionName) {
        if (logger.isTraceEnabled()) {
            logger.trace("readOutOfBoxSectionFields: sectionName = " + sectionName + " pageType = " + pageType);
        }
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("sectionName", sectionName);
        params.put("pageType", pageType);
        return getSqlMapClientTemplate().queryForList("SELECT_OUT_OF_BOX_SEC_FLDS", params);
    }

    @Override
    public SectionField maintainSectionField(SectionField sectionField) {
        if (logger.isTraceEnabled()) {
            logger.trace("maintainSectionField: sectionField = " + sectionField.getId());
        }
        // Always create a site-specific version instead of updating the generic record.
        if (sectionField.getSite() == null) {
        	sectionField.setId(new Long(0));
        	sectionField.setSite(new Site(getSiteName()));
        }
        return (SectionField) insertOrUpdate(sectionField, "SECTION_FIELD");
    }

    @Override
    public SectionDefinition maintainSectionDefinition(
            SectionDefinition sectionDefinition) {
        // Always create a site-specific version instead of updating the generic record.
        if (sectionDefinition.getSite() == null) {
        	sectionDefinition.setId(new Long(0));
        	sectionDefinition.setSite(new Site(getSiteName()));
        }
        return (SectionDefinition) insertOrUpdate(sectionDefinition, "SECTION_DEFINITION");
    }


}