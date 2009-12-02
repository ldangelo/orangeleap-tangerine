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

package com.orangeleap.tangerine.service.customization;

import com.orangeleap.tangerine.controller.customField.CustomFieldRequest;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.customization.PageAccess;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service("pageCustomizationService")
public class PageCustomizationServiceImpl implements PageCustomizationService {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private static final Integer ZERO = Integer.valueOf(0);

    @Resource(name = "fieldDAO")
    private FieldDao fieldDao;

    @Resource(name = "queryLookupDAO")
    private QueryLookupDao queryLookupDao;

    @Resource(name = "sectionDAO")
    private SectionDao sectionDao;

    @Resource(name = "pageAccessDAO")
    private PageAccessDao pageAccessDao;

    @Resource(name = "pageCustomizationCache")
    private Cache pageCustomizationCache;


    @Autowired
    private TangerineUserHelper tangerineUserHelper;

    @Override
    public List<String> readDistintSectionDefinitionsRoles() {
        return sectionDao.readDistintSectionDefinitionsRoles();
    }

    @Override
    public List<String> readDistintSectionDefinitionsPageTypes() {
        return sectionDao.readDistintSectionDefinitionsPageTypes();
    }

    /*
    * (non-Javadoc)
    * @see com.orangeleap.tangerine.service.customization.PageCustomizationService#readSectionDefinitionsByPageTypeRoles(com.orangeleap.tangerine.type.PageType, java.util.List)
    */
    @SuppressWarnings("unchecked")
    @Override
    public List<SectionDefinition> readSectionDefinitionsByPageTypeRoles(PageType pageType, List<String> roles) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionDefinitionsByPageTypeRoles: pageType = " + pageType + " roles = " + roles);
        }
        List<SectionDefinition> sectionDefinitions = sectionDao.readSectionDefinitions(pageType, roles);
        List<SectionDefinition> returnSections = removeDuplicateSectionDefinitions(sectionDefinitions);
        Collections.sort(returnSections, new BeanComparator("sectionOrder"));
        return returnSections;
    }

    @Override
    public List<SectionField> readSectionFieldsByPageTypeRoles(PageType pageType, List<String> roles) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionFieldsByPageTypeRoles: pageType = " + pageType + " roles = " + roles);
        }
        List<SectionDefinition> sectionDefinitions = readSectionDefinitionsByPageTypeRoles(pageType, roles);
        return readSectionFieldsBySection(sectionDefinitions.get(0));
    }

    @Override
    public List<SectionDefinition> readSectionDefinitionsByPageType(PageType pageType) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionDefinitionsByPageType: pageType = " + pageType );
        }
        List<SectionDefinition> sectionDefinitions = sectionDao.readSectionDefinitions(pageType);
        return sectionDefinitions;
    }

    @Override
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition) {
    	return readSectionFieldsBySection(sectionDefinition, false);
    }

    /*
     * (non-Javadoc)
     * @see com.orangeleap.tangerine.service.customization.PageCustomizationService#readSectionFieldsBySection(com.orangeleap.tangerine.domain.model.customization.SectionDefinition)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition, boolean readAll) {
        if (logger.isTraceEnabled()) {
            logger.trace("readSectionFieldsBySection: sectionDefinition = " + sectionDefinition);
        }
        String siteName = tangerineUserHelper.lookupUserSiteName();

        List<SectionField> returnFields;

        String outOfBoxKey = siteName + "." + sectionDefinition.getPageType().name() + "." + sectionDefinition.getSectionName();

        Element ele = pageCustomizationCache.get(outOfBoxKey);
        List<SectionField> outOfBoxSectionFields = null;

        if (ele == null) {
            outOfBoxSectionFields = sectionDao.readOutOfBoxSectionFields(sectionDefinition.getPageType(), sectionDefinition.getSectionName());
            pageCustomizationCache.put(new Element(outOfBoxKey, outOfBoxSectionFields));
        } else {
            outOfBoxSectionFields = (List<SectionField>) ele.getValue();
        }

        String customSectionKey = siteName + "." + sectionDefinition.getId();
        ele = pageCustomizationCache.get(customSectionKey);
        List<SectionField> customSectionFields = null;
        if (ele == null) {
            customSectionFields = sectionDao.readCustomizedSectionFields(sectionDefinition.getId());
            pageCustomizationCache.put(new Element(customSectionKey, customSectionFields));
        } else {
            customSectionFields = (List<SectionField>) ele.getValue();
        }

        if (customSectionFields.isEmpty()) {
            returnFields = outOfBoxSectionFields;
        } else {
            returnFields = new ArrayList<SectionField>(outOfBoxSectionFields);
            for (SectionField customizedField : customSectionFields) {
                removeMatchingOutOfBoxField(returnFields, customizedField);
                if (readAll || !ZERO.equals(customizedField.getFieldOrder())) {
                    returnFields.add(customizedField);
                }
            }
        }
        Collections.sort(returnFields, new BeanComparator("fieldOrder"));
        return returnFields;
    }

    private void removeMatchingOutOfBoxField(List<SectionField> sectionFields, SectionField customizedField) {
        for (int i = 0; i < sectionFields.size(); i++) {
            SectionField currentField = sectionFields.get(i);
            String currentFieldName = currentField.getFieldPropertyName();
            String customFieldName = customizedField.getFieldPropertyName();
            if (currentFieldName.equals(customFieldName)) {
                sectionFields.remove(i);
                break;
            }
        }
    }

    /**
     * Takes a <code>List</code> of <code>SectionDefinition</code> objects and keeps the first alphabetical role name only to be consistent - should not happen if roles set up consistently
     *
     * @param sectionDefinitions the original <code>List</code> to filter
     * @return
     */
    private List<SectionDefinition> removeDuplicateSectionDefinitions(List<SectionDefinition> sectionDefinitions) {
        if (sectionDefinitions != null) {
            Map<String, SectionDefinition> nameDefinitionMap = new HashMap<String, SectionDefinition>();
            for (SectionDefinition sectionDefinition : sectionDefinitions) {
                SectionDefinition sd = nameDefinitionMap.get(sectionDefinition.getSectionName());
                if (sd == null) {
                    nameDefinitionMap.put(sectionDefinition.getSectionName(), sectionDefinition);
                } else {
                    if (sd.getRole().compareTo(sectionDefinition.getRole()) > 0) {
                        nameDefinitionMap.put(sectionDefinition.getSectionName(), sectionDefinition);
                    }
                }
            }
            return new ArrayList<SectionDefinition>(nameDefinitionMap.values());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see com.orangeleap.tangerine.service.customization.PageCustomizationService#readPageAccess(java.util.List)
     */
    @Override
    @Transactional
    public Map<String, AccessType> readPageAccess(List<String> roles) {
        if (logger.isTraceEnabled()) {
            logger.trace("readPageAccess: roles = " + roles);
        }
        Map<String, PageAccess> pageAccessMap = new HashMap<String, PageAccess>(); // pageType, PageAccess
        List<PageAccess> pages = pageAccessDao.readPageAccess(roles);
        if (pages != null) {
            for (PageAccess pageAccess : pages) {
                PageAccess pa = pageAccessMap.get(pageAccess.getPageType().getPageName());
                if (pa == null || compare(pageAccess, pa) > 0) {
                    pageAccessMap.put(pageAccess.getPageType().getPageName(), pageAccess);
                }
            }
        }

        Map<String, AccessType> accessMap = new HashMap<String, AccessType>(); // pageType, AccessType
        for (String key : pageAccessMap.keySet()) {
            accessMap.put(key, pageAccessMap.get(key).getAccessType());
        }
        return accessMap;
    }
    
    private int compare(PageAccess p1, PageAccess p2) {
    	return val(p1.getAccessType()) - val(p2.getAccessType());
    }
    
    private int val(AccessType at) {
    	if (at == AccessType.ALLOWED) return 0;
    	if (at == AccessType.READ_WRITE) return 1;
    	if (at == AccessType.READ_ONLY) return 2;
    	if (at == AccessType.DENIED) return 3;
    	return -1;
    }

    @Override
    @Transactional
    public void maintainFieldDefinition(FieldDefinition fieldDefinition) {
        fieldDao.maintainFieldDefinition(fieldDefinition);
    }

    @Override
    @Transactional
    public void maintainFieldValidation(FieldValidation fieldValidation) {
        fieldDao.maintainFieldValidation(fieldValidation);
    }

    @Override
    @Transactional
    public void maintainSectionField(SectionField sectionField) {
        sectionDao.maintainSectionField(sectionField);
    }

    @Override
    @Transactional
    public void maintainSectionFields(List<SectionField> sectionFields) {
        for (SectionField sf : sectionFields) {
            sectionDao.maintainSectionField(sf);
        }
    }
    
    @Override
    @Transactional
    public QueryLookup maintainQueryLookup(QueryLookup queryLookup) {
        return queryLookupDao.maintainQueryLookup(queryLookup);
    }

    @Override
    @Transactional
    public void maintainQueryLookupParam(QueryLookupParam queryLookupParam) {
        queryLookupDao.maintainQueryLookupParam(queryLookupParam);
    }


    @Override
    @Transactional
    public void maintainCustomFieldGuruData(CustomFieldRequest customFieldRequest) {
        fieldDao.maintainCustomFieldGuruData(customFieldRequest);
    }

    @Override
    public SectionDefinition maintainSectionDefinition(
            SectionDefinition sectionDefinition) {
    	SectionDefinition newsectionDefinition = sectionDao.maintainSectionDefinition(sectionDefinition);
    	// Was copied from default section to site-specific one, so copy over fields as well.
    	if (!newsectionDefinition.getId().equals(sectionDefinition.getId())) {
            copyFields(sectionDefinition, newsectionDefinition);
    	}
    	return newsectionDefinition;
    }

    @Override
    public SectionDefinition copySectionDefinition(Long id) {
    	
        SectionDefinition sectionDefinition = sectionDao.readSectionDefinition(new Long(id));

        sectionDefinition.setId(null);
        sectionDefinition.setSite(new Site(tangerineUserHelper.lookupUserSiteName()));
        sectionDefinition.setRole("");
        SectionDefinition newsectionDefinition = sectionDao.maintainSectionDefinition(sectionDefinition);

        copyFields(sectionDefinition, newsectionDefinition);

        return newsectionDefinition;
        
    }
    
	@Override
	public void maintainFieldRequired(FieldRequired fieldRequired) {
		fieldDao.maintainFieldRequired(fieldRequired);
	}
    
    // Copy fields from a to b.
    private void copyFields(SectionDefinition a, SectionDefinition b) {
        List<SectionField> sectionFields = readSectionFieldsBySection(a, true);
        for (SectionField sectionField : sectionFields) {
        	sectionField.setId(null);
        	sectionField.setSectionDefinition(b);
        	sectionDao.maintainSectionField(sectionField);
        }
    }

    @Override
    public List<SectionField> getFieldsExceptId(List<SectionField> fields) {
        List<SectionField> filteredFields = new ArrayList<SectionField>();
        for (SectionField thisField : fields) {
            if (!StringConstants.ID.equals(thisField.getFieldPropertyName()) && !StringConstants.CONSTITUENT_ID.equals(thisField.getFieldPropertyName()) &&
                    !StringConstants.CONSTITUENT_DOT_ID.equals(thisField.getFieldPropertyName())) {
                filteredFields.add(thisField);
            }
        }
        return filteredFields;
    }

}