package com.orangeleap.tangerine.controller.importexport.exporters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.orangeleap.tangerine.controller.importexport.ExportRequest;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public abstract class EntityExporter {

    protected final Log logger = LogFactory.getLog(getClass());

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
		siteservice = (SiteService)applicationContext.getBean("siteService");
		tangerineUserHelper = (TangerineUserHelper)applicationContext.getBean("tangerineUserHelper");
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
		
		Map<String, FieldDefinition> fields = siteservice.readFieldTypes(getPageType(), tangerineUserHelper.lookupUserRoles());
		
		// Use siteservice to read FIELD_DEFINITIONS to get all entity fields for site.
		List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
		for (Map.Entry<String, FieldDefinition> es : fields.entrySet()) {
			String name = es.getKey();
			FieldDefinition fd = es.getValue();
			if (exclude(name, fd)) {
                continue;
            }
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
		
		FieldType fieldType = fd.getFieldDefinition().getFieldType();
		
		String result = "";
		try {
			String name = fd.getName();
			if (fd.getType() == FieldDescriptor.CUSTOM) {
				Method m = o.getClass().getMethod("getCustomFieldValue", new Class[]{String.class});
				result = (String)m.invoke(o, name);
			} else if (fd.isMap()) {
				Method m = o.getClass().getMethod("get"+fd.getMapType()+"Map");
				Map map = (Map)m.invoke(o);
				Object so = map.get(fd.getKey());
				result = getProperty(so, fd.getSubField(), fieldType);
			} else if (fd.isDependentField()) {
				String depobject = fd.getDependentObject();
				Method m = o.getClass().getMethod("get"+FieldDescriptor.toInitialUpperCase(depobject));
				Object so = m.invoke(o);
				if (so == null) {
                    return "";
                }
				result = getProperty(so, fd.getDependentField(), fieldType);
			} else {
				result = getProperty(o, name, fieldType);
			}
		} catch (Exception e) {
			// Some screen fields are not entity properties
			fd.setDisabled(true);
		}
		if (result == null) {
            result = "";
        }
		
		return result;
	}
	
	private String getProperty(Object o, String field, FieldType fieldType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (fieldType == FieldType.DATE || fieldType == FieldType.DATE_DISPLAY) {
			Object result = getObject(o, field);
			return formatDate(result, "MM/dd/yyyy");
		} else if (fieldType == FieldType.CC_EXPIRATION || fieldType == FieldType.CC_EXPIRATION_DISPLAY) {
			Object result = getObject(o, field);
			return formatDate(result, "MM/yyyy");
		} else {
			return BeanUtils.getProperty(o, field);
		}
	}
	
	private Object getObject(Object o, String field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Method m = o.getClass().getMethod("get"+StringUtils.capitalize(field), new Class[0]);
		return m.invoke(o, new Object[0]);
	}
	
	private String formatDate(Object o, String format) {
		if (o instanceof java.util.Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format((java.util.Date)o);
		} else {
			if (o == null) return ""; else return o.toString();
		}
	}


	
}
