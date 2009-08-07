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

package com.orangeleap.tangerine.controller.importexport.importers;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.controller.importexport.ImportRequest;
import com.orangeleap.tangerine.controller.importexport.exporters.EntityExporter;
import com.orangeleap.tangerine.controller.importexport.exporters.EntityExporterFactory;
import com.orangeleap.tangerine.controller.importexport.exporters.FieldDescriptor;
import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.type.EntityType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class EntityImporter {
	
	public final static String ACTION_ADD = "add";
	public final static String ACTION_CHANGE = "change";
	public final static String ACTION_DELETE = "delete";

	protected final Log logger = OLLogger.getLog(getClass());

	protected ImportRequest importRequest;
	protected ApplicationContext applicationContext;
	protected SiteService siteservice;
	protected FieldDao fieldDao;

	protected TangerineUserHelper tangerineUserHelper;

	protected List<FieldDescriptor> fieldDescriptors; 
	protected Map<String, FieldDescriptor> fieldDescriptorMap = new HashMap<String, FieldDescriptor>();
	
	protected EntityImporter(ImportRequest importRequest, ApplicationContext applicationContext) {
		this.importRequest = importRequest;
		this.applicationContext = applicationContext;
		fieldDao = (FieldDao)applicationContext.getBean("fieldDAO");
		tangerineUserHelper = (TangerineUserHelper)applicationContext.getBean("tangerineUserHelper");

		siteservice = (SiteService)applicationContext.getBean("siteService");
		fieldDescriptors = getFieldDescriptors();
		for (FieldDescriptor fd : fieldDescriptors) {
            fieldDescriptorMap.put(fd.getName(), fd);
        }
	}	
	
	// Default to same field list as corresponding exporter
	protected List<FieldDescriptor> getFieldDescriptors() {
		ExportRequest er = new ExportRequest();
		er.setEntity(importRequest.getEntity());
		EntityExporter entityexporter = new EntityExporterFactory().getEntityExporter(er, applicationContext);
		return entityexporter.getExportFieldDescriptors();
	}
	
	
	public abstract void importValueMap(String action, Map<String, String> m) throws ConstituentValidationException, BindException;
	public abstract String getIdField();
	protected abstract PageType getPageType();

	protected void mapValuesToObject(Map<String, String> values, Object o) {
		for (Map.Entry<String, String> es : values.entrySet()) {
			String key = es.getKey();
			String value = es.getValue();
			setFieldValue(o, key, value);
		}
	}
	
	protected FieldDescriptor getFieldDescriptor(String name) {
		name = FieldDescriptor.getInternalNameForImportFieldName(name);
		return fieldDescriptorMap.get(name);
	}

	protected boolean ignore(String key) {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected void setFieldValue(Object o, String key, String svalue) {
		
			try {
				
				if (ignore(key)) {
                    return;
                }
				
				FieldDescriptor fd = getFieldDescriptor(key);
				if (fd == null) {
                    throw new RuntimeException("Unrecognized import field name: "+key);
                }
				
				Object value = convertToObject(svalue, fd);
				
				if (fd.getType() == FieldDescriptor.CUSTOM) {
					Method m = o.getClass().getMethod("setCustomFieldValue", new Class[]{String.class, String.class});
					m.invoke(o, new Object[]{key, value});
				} else if (fd.isMap()) {
					Method m = o.getClass().getMethod("get"+fd.getMapType()+"Map");
					Map map = (Map)m.invoke(o);
					Object so = map.get(fd.getKey()); 
					if (so == null) {
                        throw new RuntimeException("Unable to import field.");
                    }
					BeanUtils.setProperty(so, fd.getSubField(), value);
				} else if (fd.isDependentField()) {
					String depobject = fd.getDependentObject();
					Method m = o.getClass().getMethod("get"+FieldDescriptor.toInitialUpperCase(depobject));
					Object so = m.invoke(o);
					if (so == null) {
                        Class returnClass = m.getReturnType();
                        so = returnClass.newInstance();
                        if (so instanceof AbstractEntity) {
                            siteservice.setEntityDefaults((AbstractEntity) so, EntityType.valueOf(depobject)); 
                        }
                        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(o);
                        if (bw.isWritableProperty(depobject)) {
                            bw.setPropertyValue(depobject, so);
                        }
                    } 
					BeanUtils.setProperty(so, fd.getDependentField(), value);
					if (o instanceof AddressAware && ((AddressAware)o).getAddress() != null) {
                        ((AddressAware)o).getAddress().setId(0L);  // New
                    }
					if (o instanceof PhoneAware && ((PhoneAware)o).getPhone() != null) {
                        ((PhoneAware)o).getPhone().setId(0L);  // New
                    }
					if (o instanceof EmailAware && ((EmailAware)o).getEmail() != null) {
                        ((EmailAware)o).getEmail().setId(0L);  // New
                    }
				} else {
					BeanUtils.setProperty(o, key, value);
				}
			} catch (Exception e) {
				logger.debug("Exception occurred", e);
				throw new RuntimeException("Unable to set field value "+key+" to "+svalue+": "+e.getMessage());
			}
		
	}
	
	// Convert types for native fields on entity or subentity.  Not needed for custom fields since they are all strings.
	protected Object convertToObject(String svalue, FieldDescriptor fd) {
		FieldType fieldType = fd.getFieldDefinition().getFieldType();
		if (fd.getType() != FieldDescriptor.CUSTOM && (fieldType == FieldType.DATE || fieldType == FieldType.CC_EXPIRATION)) {
			CustomDateEditor ed = new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true);
			ed.setAsText(svalue);
			return ed.getValue();
		}
		return svalue;
	}
	

	
}
