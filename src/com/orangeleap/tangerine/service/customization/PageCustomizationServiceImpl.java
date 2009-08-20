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
import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.dao.QueryLookupDao;
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.QueryLookup;
import com.orangeleap.tangerine.domain.QueryLookupParam;
import com.orangeleap.tangerine.domain.customization.*;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.type.RoleType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource(name = "cacheGroupDAO")
    private CacheGroupDao cacheGroupDao;


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

    /*
     * (non-Javadoc)
     * @see com.orangeleap.tangerine.service.customization.PageCustomizationService#readSectionFieldsBySection(com.orangeleap.tangerine.domain.model.customization.SectionDefinition)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SectionField> readSectionFieldsBySection(SectionDefinition sectionDefinition) {
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
                if (!ZERO.equals(customizedField.getFieldOrder())) {
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
     * Takes a <code>List</code> of <code>SectionDefinition</code> objects and keeps the section name with the highest role
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
                    Integer sectionDefinitionRoleRank = sectionDefinition.getRole() == null ? -1 : RoleType.valueOf(sectionDefinition.getRole()).getRoleRank();
                    Integer sdRoleRank = sd.getRole() == null ? -1 : RoleType.valueOf(sd.getRole()).getRoleRank();
                    if ((sd.getSite() == null && sectionDefinition.getSite() != null) || sdRoleRank < sectionDefinitionRoleRank) {
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
                PageAccess pd = pageAccessMap.get(pageAccess.getPageType().name());
                if (pd == null || pd.getSite() == null || (RoleType.valueOf(pd.getRole()).getRoleRank() < RoleType.valueOf(pageAccess.getRole()).getRoleRank())) {
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
        cacheGroupDao.updateCacheGroupTimestamp(CacheGroupType.PAGE_CUSTOMIZATION);
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
        return sectionDao.maintainSectionDefinition(sectionDefinition);
    }


}