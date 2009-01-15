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
	public List<String[]> exportAll() {
		
		List result = new ArrayList<String[]>();

		List<FieldDescriptor> fields = getExportFieldDescriptors();
		String[] header = new String[fields.size() + 1];
		header[0] = "action";
		for (int i = 0; i < fields.size();i++) {
			FieldDescriptor fd = fields.get(i);
			header[i+1] = getExportFieldNameForMap(fd.getName());
		}
		result.add(header);
		
		List list = readAll();
		for (Object o: list) {
			String[] line = new String[fields.size() + 1];
			result.add(line);
			line[0] = "change";
			for (int i = 0; i < fields.size(); i++) {
				FieldDescriptor fd = fields.get(i);
				String value = getFieldValue(o, fd);
				//logger.debug("name="+fd.getName()+", value="+value);
				line[i+1] = value;
			}
		}
		
		return result;
		
	}
	
	protected List<FieldDescriptor> getExportFieldDescriptors() {
		
		Map<String, FieldDefinition> fields = siteservice.readFieldTypes(SessionServiceImpl.lookupUserSiteName(), getPageType(), SessionServiceImpl.lookupUserRoles());
		
		// Use siteservice to read FIELD_DEFINITIONS to get all entity fields for site.
		List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
		for (Map.Entry<String, FieldDefinition> es : fields.entrySet()) {
			String name = es.getKey();
			FieldDefinition fd = es.getValue();
			logger.debug("HEADER_FIELD : "+ name);
			if (fd.isCustom()) {
				list.add(new FieldDescriptor(fd.getCustomFieldName(), FieldDescriptor.CUSTOM));
			} else if (name.contains("addressMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.ADDRESS));
			} else if (name.contains("phoneMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.PHONE));
			} else if (name.contains("emailMap[")) {
				list.add(new FieldDescriptor(name, FieldDescriptor.EMAIL));
			} else {
				list.add(new FieldDescriptor(name, FieldDescriptor.NATIVE));
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

	@SuppressWarnings("unchecked")
	private String getFieldValue(Object o, FieldDescriptor fd) {
		try {
			String name = fd.getName();
			if (fd.getType() == FieldDescriptor.CUSTOM) {
				Method m = o.getClass().getMethod("getCustomFieldValue", new Class[]{String.class});
				return (String)m.invoke(o, name);
			} else if (isMap(name)) {
				Method m = o.getClass().getMethod("get"+getMapType(name)+"Map");
				Map map = (Map)m.invoke(o);
				Object so = map.get(getKey(name));
				return BeanUtils.getProperty(so, getSubField(name));
			} else {
				return BeanUtils.getProperty(o, name);
			}
		} catch (Exception e) {
			logger.debug(""+e);
			return "";
		}
	}
	
	private boolean isMap(String name) {
		return name.contains("Map[");
	}

	private String getMapType(String name) {
		int i = name.indexOf("Map[");
		if (i < 0 ) return "";
		String type = name.substring(0,i);
		return type.substring(0,1).toUpperCase()+type.substring(1);
	}
	
	private String getKey(String name) {
		int i = name.indexOf("[");
		int j = name.indexOf("]");
		if (i < 0 || j < 0 || i > j) return "";
		return name.substring(i + 1, j);
	}
	
	private String getSubField(String name) {
		int j = name.indexOf("]");
		if (j < 0) return "";
		return name.substring(j+2);
	}
	
	
	// Create an export field name for a mapped field.
	protected String getExportFieldNameForMap(String name) {
		if (!isMap(name)) return name;
		return getMapType(name) + "[" + getKey(name) + "]" + getSubField(name);
	}
		


	
}
