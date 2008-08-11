package com.mpower.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mpower.dao.SiteDao;
import com.mpower.dao.customization.FieldDao;
import com.mpower.domain.Site;
import com.mpower.domain.customization.FieldValidation;
import com.mpower.domain.customization.RequiredField;
import com.mpower.type.EntityType;

@Service("siteService")
public class SiteServiceImpl implements SiteService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "fieldDao")
    private FieldDao fieldDao;

    @Resource(name = "siteDao")
    private SiteDao siteDao;

    @Override
    public List<Site> readSites() {
        return siteDao.readSites();
    }

    @Override
    public Map<String, Boolean> readRequiredFields(String siteName, EntityType entityType) {
        Map<String, Boolean> returnMap = new HashMap<String, Boolean>();
        List<RequiredField> requiredFields = fieldDao.readRequiredFields(siteName, entityType);
        if (requiredFields != null) {
            for (RequiredField rf : requiredFields) {
                String key = rf.getFieldDefinition().getFieldName();
                if (rf.getSecondaryFieldDefinition() != null) {
                    key += "." + rf.getSecondaryFieldDefinition().getFieldName();
                }
                if (returnMap.get(key) == null || rf.getSiteName() != null) {
                    returnMap.put(key, rf.isRequired());
                }
            }
        }
        return returnMap;
    }

    @Override
    public Map<String, String> readValidations(String siteName, EntityType entityType) {
        Map<String, String> returnMap = new HashMap<String, String>();
        List<FieldValidation> validations = fieldDao.readFieldValidations(siteName, entityType);
        if (validations != null) {
            for (FieldValidation v : validations) {
                String key = v.getFieldDefinition().getFieldName();
                if (v.getSecondaryFieldDefinition() != null) {
                    key += "." + v.getSecondaryFieldDefinition().getFieldName();
                }
                if (returnMap.get(key) == null || v.getSiteName() != null) {
                    returnMap.put(key, v.getRegex());
                }
            }
        }
        return returnMap;
    }
}
