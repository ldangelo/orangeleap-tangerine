package com.mpower.controller.importexport.exporters;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.mpower.domain.customization.FieldDefinition;
import com.mpower.service.SiteService;
import com.mpower.service.impl.SessionServiceImpl;
import com.mpower.type.FieldType;
import com.mpower.type.PageType;


public abstract class EntityExporter {

    protected final Log logger = LogFactory.getLog(getClass());

	@SuppressWarnings("unchecked")
	protected abstract List readAll();
	protected abstract PageType getPageType();
	
	protected String entity;
	protected ApplicationContext applicationContext;
	protected SiteService siteservice;
	
	protected EntityExporter(String entity, ApplicationContext applicationContext) {
		this.entity = entity;
		this.applicationContext = applicationContext;
		siteservice = (SiteService)applicationContext.getBean("siteService");
	}
	
	@SuppressWarnings("unchecked")
	public List<List<String>> exportAll() {
		
		List<List<String>> result = new ArrayList<List<String>>();

		List<FieldDescriptor> fields = getExportFieldDescriptors();
		List<String> header = new ArrayList<String>();
		header.add("action");
		for (int i = 0; i < fields.size();i++) {
			FieldDescriptor fd = fields.get(i);
			header.add(fd.getExportFieldNameForInternalName());
		}
		result.add(header);
		
		List list = readAll();
		for (Object o: list) {
			List<String> line = new ArrayList<String>();
			result.add(line);
			line.add("change");
			for (int i = 0; i < fields.size(); i++) {
				FieldDescriptor fd = fields.get(i);
				String value = getFieldValue(o, fd);
				//logger.debug("name="+fd.getName()+", value="+value);
				line.add(value);
			}
		}
		
		removeInvalidFields(fields, result);
		
		return result;
		
	}
	
	private void removeInvalidFields(List<FieldDescriptor> fields, List<List<String>> list) {
		for (int i = fields.size() - 1; i >= 0; i--) {
			FieldDescriptor fd  = fields.get(i);
			if (fd.isDisabled()) {
				for (List<String> line : list) {
					line.remove(i+1);
				}
			}
		}
	}
	
	public List<FieldDescriptor> getExportFieldDescriptors() {
		
		Map<String, FieldDefinition> fields = siteservice.readFieldTypes(SessionServiceImpl.lookupUserSiteName(), getPageType(), SessionServiceImpl.lookupUserRoles());
		
		// Use siteservice to read FIELD_DEFINITIONS to get all entity fields for site.
		List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
		for (Map.Entry<String, FieldDefinition> es : fields.entrySet()) {
			String name = es.getKey();
			FieldDefinition fd = es.getValue();
			if (exclude(name, fd)) continue;
			logger.debug("HEADER_FIELD : "+ name);
			if (fd.isCustom()) {
				list.add(new FieldDescriptor(fd.getCustomFieldName(), FieldDescriptor.CUSTOM, fd));
			} else if (name.contains("addressMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.ADDRESS, fd));
			} else if (name.contains("phoneMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.PHONE, fd));
			} else if (name.contains("emailMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.EMAIL, fd));
			} else {
				list.add(new FieldDescriptor(name, FieldDescriptor.NATIVE, fd));
			}
		}
		
		Collections.sort(list, new Comparator<FieldDescriptor>() {
			@Override
			public int compare(FieldDescriptor o1, FieldDescriptor o2) {
				if (o1.getType() == o2.getType()) {
					return o1.getName().compareTo(o2.getName());
				} else {
				    return o1.getType() - o2.getType();
				}
			}
		});

		return list;
		
	}
	
	protected boolean exclude(String name, FieldDefinition fd) {
		return  
				fd.getFieldType() == FieldType.ADDRESS_PICKLIST
				|| fd.getFieldType() == FieldType.EMAIL_PICKLIST
				|| fd.getFieldType() == FieldType.PHONE_PICKLIST
				|| fd.getFieldType() == FieldType.PAYMENT_SOURCE_PICKLIST
				|| fd.getFieldType() == FieldType.PREFERRED_PHONE_TYPES
				;
	}

	@SuppressWarnings("unchecked")
	private String getFieldValue(Object o, FieldDescriptor fd) {
		try {
			String name = fd.getName();
			if (fd.getType() == FieldDescriptor.CUSTOM) {
				Method m = o.getClass().getMethod("getCustomFieldValue", new Class[]{String.class});
				return (String)m.invoke(o, name);
			} else if (fd.isMap()) {
				Method m = o.getClass().getMethod("get"+fd.getMapType()+"Map");
				Map map = (Map)m.invoke(o);
				Object so = map.get(fd.getKey());
				return BeanUtils.getProperty(so, fd.getSubField());
			} else if (fd.isDependentField()) {
				String depobject = fd.getDependentObject();
				Method m = o.getClass().getMethod("get"+FieldDescriptor.toInitialUpperCase(depobject));
				Object so = m.invoke(o);
				if (so == null) return "";
				return BeanUtils.getProperty(so, fd.getDependentField());
			} else {
				return BeanUtils.getProperty(o, name);
			}
		} catch (Exception e) {
			// Some screen fields are not entity properties
			fd.setDisabled(true);
			return "";
		}
	}
	


	
}
