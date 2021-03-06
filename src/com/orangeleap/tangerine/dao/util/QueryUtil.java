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

package com.orangeleap.tangerine.dao.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.validator.GenericValidator;
import com.orangeleap.tangerine.dao.ibatis.AbstractIBatisDao;

public class QueryUtil {
	
    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    
    private QueryUtil() {
    }

    public static final String ADDITIONAL_WHERE = "additionalWhere";
    
    public static Map<String, Object> translateSearchParamsToIBatisParams(Map<String, Object> searchparams, Map<String, Object> fieldparams, Map<String, String> fieldMap) {
    	return translateSearchParamsToIBatisParams(searchparams,fieldparams,fieldMap,true);
    }
    
    public static Map<String, Object> translateSearchParamsToIBatisParams(Map<String, Object> searchparams, Map<String, Object> fieldparams, Map<String, String> fieldMap,boolean useLike) {
        Map<String, Object> refConstituentParams = new HashMap<String, Object>();
        Map<String, Object> addressParams = new HashMap<String, Object>();
        Map<String, Object> phoneParams = new HashMap<String, Object>();
        Map<String, Object> emailParams = new HashMap<String, Object>();
        Map<String, String> customParams = new HashMap<String, String>();
        Map<String, String> stringParams = new HashMap<String, String>();
        Map<String, Object> nonStringParams = new HashMap<String, Object>();
        String quote = "";
        
        if (useLike) quote = "%";
        else quote = "";
        
        if (searchparams != null) {
            for (Map.Entry<String, Object> pair : searchparams.entrySet()) {

                String key = pair.getKey();
                
                key = AbstractIBatisDao.oneWord(key);

                Object value = pair.getValue();

                boolean isString = true;
                if (value instanceof String) {
                    if (GenericValidator.isBlankOrNull((String) value) || value.equals("null")) {
                        continue;
                    }
                    value = ((String)value).replace('\\', ' ');
                } 
                else {
                    if (value == null) {
                        continue;
                    }
                    isString = false;
                }
                
                
                // Constituent fields not including nested fields.
                if (key.startsWith("constituent.") && (key.contains("[") || key.indexOf("") != key.lastIndexOf("."))) {
                	key = key.substring(key.indexOf('.') + 1);
                }
                
                
                if (key.startsWith("constituent.")) {
                	refConstituentParams.put(key, quote + value + quote);
                }
                else if (key.startsWith("primaryAddress")) {
                	addressParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                }
                else if (key.startsWith("primaryPhone")) {
                	phoneParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                }
                else if (key.startsWith("primaryEmail")) {
                	emailParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                }
                else if (key.startsWith("addressMap[")) {
                    addressParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                } 
                else if (key.startsWith("phoneMap[")) {
                    phoneParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                } 
                else if (key.startsWith("emailMap[")) {
                    emailParams.put(key.substring(key.indexOf('.') + 1), quote + value + quote);
                } 
                else if (key.startsWith("customFieldMap[")) {
                    customParams.put(key.substring(key.indexOf('[') + 1, key.indexOf(']')), quote + value + quote);
                } 
                else {
                    if (isString) {
                    	// These use LIKE
                    	stringParams.put(key, quote + value + quote);
                    } else {
                    	// These use =
                    	nonStringParams.put(key, value);
                    }
                }
            }
        }
        
        mapParmsToColumns(refConstituentParams, fieldMap);
        mapParmsToColumns(addressParams, fieldMap);
        mapParmsToColumns(phoneParams, fieldMap);
        mapParmsToColumns(emailParams, fieldMap);
        mapParmsToColumns(stringParams, fieldMap);
        mapParmsToColumns(nonStringParams, fieldMap);
        
        if (refConstituentParams.size() > 0) fieldparams.put("refConstituentParams", refConstituentParams.entrySet().toArray());
        if (addressParams.size() > 0) fieldparams.put("addressParams", addressParams.entrySet().toArray());
        if (phoneParams.size() > 0) fieldparams.put("phoneParams", phoneParams.entrySet().toArray());
        if (emailParams.size() > 0) fieldparams.put("emailParams", emailParams.entrySet().toArray());
        if (customParams.size() > 0) fieldparams.put("customParams", customParams.entrySet().toArray());
        if (stringParams.size() > 0) fieldparams.put("stringParams", stringParams.entrySet().toArray());
        if (nonStringParams.size() > 0) fieldparams.put("nonStringParams", nonStringParams.entrySet().toArray());
        
        String where = (String)searchparams.get(ADDITIONAL_WHERE);
        if (where != null) fieldparams.put(ADDITIONAL_WHERE, where);
	
        return fieldparams;
    }
    
    
    @SuppressWarnings("unchecked")
	private static void mapParmsToColumns(Map params, Map<String, String> fieldMap) {
    	Map result = new HashMap();
    	Iterator it = params.entrySet().iterator();
    	while (it.hasNext()) {
    		Map.Entry e = (Map.Entry)it.next();
    		String key = (String) e.getKey();
    		Object value = e.getValue();
    		String columnKey = fieldMap.get(key);
    		if (columnKey != null) result.put(columnKey, value);
    	}
    	params.clear();
    	params.putAll(result);
    }
    
    
    
 
}
