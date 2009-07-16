package com.orangeleap.tangerine.controller;

import com.orangeleap.tangerine.domain.AbstractEntity;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class TangerineForm {
	
	protected Map<String, Object> fieldMap = new HashMap<String, Object>();
	protected Object domainObject;
	public static final String DOT = "\\.";
	public static final String START_BRACKET = "\\[";
	public static final String END_BRACKET = "\\]";
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
			addField(entry.getKey(), entry.getValue());
		}
	}
	
	public static String escapeFieldName(String key) {
		key = key.replaceAll(DOT, TANG_DOT).replaceAll(START_BRACKET, TANG_START_BRACKET).replaceAll(END_BRACKET, TANG_END_BRACKET);
		return StringEscapeUtils.escapeHtml(key);
	}

	public static String unescapeFieldName(String key) {
		return key.replaceAll(TANG_DOT, DOT).replaceAll(TANG_START_BRACKET, START_BRACKET).replaceAll(TANG_END_BRACKET, END_BRACKET);
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
		fieldMap.put(escapeFieldName(key), value);
	}

	public Object getFieldValueFromUnescapedFieldName(String unescapedFieldName) {
		return fieldMap.get(escapeFieldName(unescapedFieldName));
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

	public static String getGridCollectionName(String fieldName) {
	    Matcher matcher = java.util.regex.Pattern.compile("^(.+)\\[\\d+\\].*$").matcher(fieldName);

	    int start = 0;
	    String s = null;
	    if (matcher != null) {
	        while (matcher.find(start)) {
	            s = matcher.group(1);
	            start = matcher.end();
	        }
	    }
	    return s;
	}
}
