package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.domain.AbstractEntity;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;

public class TangerineForm {
	
	protected Map<String, Object> fieldMap = new HashMap<String, Object>();
	protected Object domainObject;
	public static final String TANG_DOT = "-tangDot-";
	public static final String TANG_START_BRACKET = "-tangStartBracket-";
	public static final String TANG_END_BRACKET = "-tangEndBracket-";

	public Map<String, Object> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, Object> fieldMap) {
		this.fieldMap = fieldMap;
	}
	
	public void setFieldMapFromEntity(AbstractEntity entity) {
		for (Map.Entry<String, Object> entry : entity.getFieldValueMap().entrySet()) {
			addField(escapeFieldName(entry.getKey()), entry.getValue());
		}
	}
	
	public static String escapeFieldName(String key) {
		return StringEscapeUtils.escapeHtml(key.replaceAll("\\.", TANG_DOT).replaceAll("\\[", TANG_START_BRACKET).replaceAll("\\]", TANG_END_BRACKET));
	}
	
	public void clearFieldMap() {
		if (fieldMap != null) {
			fieldMap.clear();
		}
	}
	
	public void addField(String key, Object value) {
		if (fieldMap == null) {
			fieldMap = new HashMap<String, Object>();
		}
		fieldMap.put(key, value);
	}

	public Object getFieldValue(String escapedFieldName) {
		return fieldMap.get(escapedFieldName);
	}

	public Object getDomainObject() {
		return domainObject;
	}

	public void setDomainObject(Object domainObject) {
		this.domainObject = domainObject;
	}
}
