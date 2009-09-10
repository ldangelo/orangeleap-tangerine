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

package com.orangeleap.tangerine.controller.importexport.exporters;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.controller.importexport.fielddefs.FieldDefUtil;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class EntityExporter {

    protected final Log logger = OLLogger.getLog(getClass());

    @SuppressWarnings("unchecked")
    protected abstract List readAll();

    protected abstract PageType getPageType();

    protected ExportRequest er;
    protected ApplicationContext applicationContext;
    protected SiteService siteservice;
    protected TangerineUserHelper tangerineUserHelper;

    protected EntityExporter(ExportRequest er, ApplicationContext applicationContext) {
        this.er = er;
        this.applicationContext = applicationContext;
        siteservice = (SiteService) applicationContext.getBean("siteService");
        tangerineUserHelper = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
    }

    @SuppressWarnings("unchecked")
    public List<List<String>> exportAll() {

        List<List<String>> result = new ArrayList<List<String>>();

        List<FieldDescriptor> fields = getExportFieldDescriptors();
        List<String> header = new ArrayList<String>();
        header.add("action");
        for (int i = 0; i < fields.size(); i++) {
            FieldDescriptor fd = fields.get(i);
            header.add(mapName(fd.getExportFieldNameForInternalName()));
        }
        result.add(header);

        List list = readAll();
        for (Object o : list) {
            List<String> line = new ArrayList<String>();
            result.add(line);
            line.add("change");
            for (int i = 0; i < fields.size(); i++) {
                FieldDescriptor fd = fields.get(i);
                String value = getFieldValue(o, fd);
                value = value.replace(StringConstants.CUSTOM_FIELD_SEPARATOR, ",");
                //logger.debug("name="+fd.getName()+", value="+value);
                line.add(value);
            }
        }

        removeInvalidFields(fields, result);

        return result;

    }

    protected String mapName(String name) {
        return name;
    }

    private void removeInvalidFields(List<FieldDescriptor> fields, List<List<String>> list) {
        for (int i = fields.size() - 1; i >= 0; i--) {
            FieldDescriptor fd = fields.get(i);
            if (fd.isDisabled()) {
                for (List<String> line : list) {
                    line.remove(i + 1);
                }
            }
        }
    }

    public List<FieldDescriptor> getExportFieldDescriptors() {

        FieldDefUtil.Exclusion exclusion = new FieldDefUtil.Exclusion() {
            public boolean excludeField(String name, FieldDefinition fd) {
                return exclude(name, fd);
            }
        };

        return FieldDefUtil.getFieldDescriptors(exclusion, getPageType(), siteservice, tangerineUserHelper);

    }

    private FieldDefUtil.Exclusion defaultExclusion = FieldDefUtil.getDefaultExclusions();

    protected boolean exclude(String name, FieldDefinition fd) {
        return defaultExclusion.excludeField(name, fd);
    }

    private String getFieldValue(Object o, FieldDescriptor fd) {

        FieldType fieldType = fd.getFieldDefinition().getFieldType();
        String id = fd.getFieldDefinition().getId();

        String fieldName = fd.getFieldDefinition().getFieldName();
    	
        if (fieldName.contains("customFieldMap[")) {
    		fieldName += ".value";
    	}

        String result = "";
        try {
        	
            result = getProperty(o, fieldName, id, fieldType);
            
        } catch (Exception e) {
            // Some screen fields are not entity properties
            fd.setDisabled(true);
        }
        if (result == null) {
            result = "";
        }

        return result;
    }
    
    private String getProperty(Object o, String fieldName, String id, FieldType fieldType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	
    	BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(o);
    	Object result = wrapper.getPropertyValue(fieldName);

    	if (result instanceof AbstractEntity) {
    		String additional = getSubfield(fieldName, id);
    		result = PropertyAccessorFactory.forBeanPropertyAccess(result).getPropertyValue(additional);
    		result = result == null ? "" : result;
    	}
    	if (result instanceof CustomField) {
    		result = ((CustomField)result).getValue();
    		result = result == null ? "" : result;
    	}

    	if (result == null) return "";
    	
        if (fieldType == FieldType.DATE || fieldType == FieldType.DATE_DISPLAY) {
            return formatDate(result, "MM/dd/yyyy");
        } else if (fieldType == FieldType.CC_EXPIRATION || fieldType == FieldType.CC_EXPIRATION_DISPLAY) {
            return formatDate(result, "MM/yyyy");
        } else {
            return result.toString();
        }
        
    }

    private String formatDate(Object o, String format) {
        if (o instanceof java.util.Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format((java.util.Date) o);
        } else {
            if (o == null) return "";
            else return o.toString();
        }
    }
    
    public static String getSubfield(String fieldName, String id) {
    	return id.substring(id.indexOf(fieldName) + fieldName.length() + 1);
    }


}
