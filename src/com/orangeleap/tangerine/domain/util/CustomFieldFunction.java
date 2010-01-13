package com.orangeleap.tangerine.domain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.service.rule.RuleHelperService;

public class CustomFieldFunction {

	public static String function(String function, String value) {
		
		try {
			
			if (("age").equals(function)) {
				
				if (value == null) return "";
				SimpleDateFormat sdf = new SimpleDateFormat(AbstractCustomizableEntity.FMT);
				return "" + RuleHelperService.age(sdf.parse(value), new Date());
				
			}
			
		} catch (Exception e) {
			// Do not log here since we are in ibatis custom field helper
		}
		
		return "";
		
	}
	
}
