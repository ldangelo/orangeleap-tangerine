package com.orangeleap.tangerine.service.customization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.PageAccessDao;
import com.orangeleap.tangerine.dao.SectionDao;
import com.orangeleap.tangerine.domain.customization.PageAccess;
import com.orangeleap.tangerine.domain.customization.SectionDefinition;
import com.orangeleap.tangerine.domain.customization.SectionField;
import com.orangeleap.tangerine.type.AccessType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.type.RoleType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("pageCustomizationService")
public class PageCustomizationServiceImpl implements PageCustomizationService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private static final Integer ZERO = Integer.valueOf(0);

    @Resource(name = "sectionDAO")
    private SectionDao sectionDao;

    @Resource(name = "pageAccessDAO")
    private PageAccessDao pageAccessDao;

     @Resource(name = "pageCustomizationCache")
    private Cache pageCustomizationCache;

    @Autowired
    private TangerineUserHelper tangerineUserHelper;

    public List<String> readDistintSectionDefinitionsRoles() {
        return sectionDao.readDistintSectionDefinitionsRoles();
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

        if(ele == null) {
            outOfBoxSectionFields = sectionDao.readOutOfBoxSectionFields(sectionDefinition.getPageType(), sectionDefinition.getSectionName());
            pageCustomizationCache.put(new Element(outOfBoxKey, outOfBoxSectionFields));
        } else {
            outOfBoxSectionFields = (List<SectionField>) ele.getValue();
        }

        String customSectionKey = siteName + "." + sectionDefinition.getId();
        ele = pageCustomizationCache.get(customSectionKey);
        List<SectionField> customSectionFields = null;
        if(ele == null) {
            customSectionFields = sectionDao.readCustomizedSectionFields(sectionDefinition.getId());
            pageCustomizationCache.put(new Element(customSectionKey, customSectionFields));
        } else {
            customSectionFields = (List<SectionField>)ele.getValue();
        }
            
        if (customSectionFields.isEmpty()) {
            returnFields = outOfBoxSectionFields;
        } 
        else {
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
                } 
                else {
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
}