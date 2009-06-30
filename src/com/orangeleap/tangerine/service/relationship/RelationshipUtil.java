package com.orangeleap.tangerine.service.relationship;

import java.util.ArrayList;
import java.util.List;

import com.orangeleap.tangerine.util.StringConstants;

public class RelationshipUtil {
    public static List<Long> getIds(String fieldValue) {
		List<Long> ids = new ArrayList<Long>();
		if (fieldValue == null) {
			return ids;
		}
		String[] sids = fieldValue.split(StringConstants.CUSTOM_FIELD_SEPARATOR); 
		for (String sid: sids) {
			if (sid.length() > 0) {
		   	   Long id = new Long(sid); 
			   ids.add(id);
			}
		}
        return ids;
    }
    
    public static String getIdString(List<Long> ids) {
    	StringBuilder sb = new StringBuilder();
    	for (Long id: ids) {
    		if (sb.length() > 0) {
				sb.append(StringConstants.CUSTOM_FIELD_SEPARATOR);
			}
    		sb.append(id);
    	}
    	return sb.toString();
    }
    

}
