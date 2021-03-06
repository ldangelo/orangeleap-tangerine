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

package com.orangeleap.tangerine.service.validator;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.PicklistItem;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.service.PicklistItemService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Map;

public class CodeValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    private PicklistItemService picklistItemService;
    private TangerineUserHelper tangerineUserHelper;
    public static final String PROJECT_CODE = "projectCode";
    public static final String MOTIVATION_CODE = "motivationCode";
    public static final String DISTRIBUTION_LINES = "distributionLines";
   
    public void setPicklistItemService(PicklistItemService picklistItemService) {
        this.picklistItemService = picklistItemService;
    }

    public void setTangerineUserHelper(TangerineUserHelper tangerineUserHelper) {
        this.tangerineUserHelper = tangerineUserHelper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return AbstractEntity.class.isAssignableFrom(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validate:");
        }
        if (target instanceof AbstractEntity) {
        	String sitename = tangerineUserHelper.lookupUserSiteName();
            AbstractEntity entity = (AbstractEntity)target;
            Map<String, FieldDefinition> map = entity.getFieldTypeMap();
            if (map != null) {
                for (Map.Entry<String, FieldDefinition> e: map.entrySet()) {
                    String key = e.getKey();
                    FieldDefinition fd = e.getValue();
                    if (FieldType.CODE == fd.getFieldType() || FieldType.CODE_OTHER == fd.getFieldType()) {
                        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
                        
                        if (key.startsWith(DISTRIBUTION_LINES)) {
                            if (beanWrapper.isReadableProperty(DISTRIBUTION_LINES)) {
                                List<DistributionLine> lines = (List<DistributionLine>)beanWrapper.getPropertyValue(DISTRIBUTION_LINES);
                                if (lines != null) {
                                    key = key.replaceFirst(DISTRIBUTION_LINES + ".", StringConstants.EMPTY); 
                                    for (DistributionLine aLine : lines) {
                                        if (aLine != null) {
                                            BeanWrapper lineBw = PropertyAccessorFactory.forBeanPropertyAccess(aLine);
                                            validateCode(lineBw, key, aLine, fd, errors, sitename);                                                
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            validateCode(beanWrapper, key, entity, fd, errors, sitename);
                        }
                    }
                }
            }
        }
    }
    
    private void validateCode(BeanWrapper beanWrapper, String key, AbstractEntity entity, FieldDefinition fd, Errors errors, String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("validateCode: key = " + key + " siteName = " + siteName);
        }
        if (beanWrapper.isReadableProperty(key)) {
            Object propertyValue = beanWrapper.getPropertyValue(key);
            boolean isValid = this.isValidCode(key, propertyValue, siteName);
            
            if (!isValid && FieldType.CODE_OTHER == fd.getFieldType()) {
                if (this.hasOtherCode(key, beanWrapper)) {
                    isValid = true;
                }
            }
            if (!isValid) {
                errors.reject("invalidCode", new String[] { (String)propertyValue, fd.getDefaultLabel() }, "\"" + propertyValue + "\" is an invalid " + fd.getDefaultLabel());
            }
        }
    }
    
    private boolean isValidCode(String key, Object propertyValue, String siteName) {
        if (logger.isDebugEnabled()) {
            logger.debug("isValidCode: key = " + key + " site = " + siteName);
        }
        boolean isValid = false;
        
        if (propertyValue != null) {
            String itemName = (String)propertyValue;
            PicklistItem item = picklistItemService.getPicklistItem(key, itemName);
            if (item != null && itemName.equals(item.getItemName())) {
                isValid = true;
            }
        }
        else {
            // No property, passes validation
            isValid = true;
        }
        return isValid;
    }
    
    public boolean hasOtherCode(String key, BeanWrapper beanWrapper) {
        boolean hasOtherCode = false;
        String keyOther = AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, key);
        if (logger.isDebugEnabled()) {
            logger.debug("hasOtherCode: keyOther = " + keyOther);
        }
        if (beanWrapper.isReadableProperty(keyOther)) {
            if (beanWrapper.getPropertyValue(keyOther) != null) { // if other code is entered, don't care what the value is, just that a value exists
                hasOtherCode = true;
            }
        }
        return hasOtherCode;
    }
}
