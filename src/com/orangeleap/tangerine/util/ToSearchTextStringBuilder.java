package com.orangeleap.tangerine.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import com.orangeleap.tangerine.domain.AbstractEntity;

public class ToSearchTextStringBuilder {
	
	public static Set<String> reflectionToKeywords(AbstractEntity entity) {
		
		Set<String> set = new TreeSet<String>();
		
		// Get local properties.
		Method[] methods = entity.getClass().getMethods();
		for (Method getter : methods) {
			if ((getter.getModifiers() & Modifier.PUBLIC) > 0) {
				String name = getter.getName();
				if (name.startsWith("get")) {
					// Stop at AbstractEntity
					if (!AbstractEntity.class.isAssignableFrom(getter.getDeclaringClass())) continue;
					for (Method setter: methods) {
						// Is there also a setter? (don't call getFullTextSearchString() again, for example)
						if (setter.getName().equals("s" + name.substring(1))) {
							try {
								Object result = getter.invoke(entity, new Object[0]);
								if (result != null) {
									
									if (result instanceof String) {
						        		String[] sa = ((String)result).split(StringConstants.CUSTOM_FIELD_SEPARATOR);
						        		for (String s: sa) if (s.trim().length() > 0) set.add(s.trim().toLowerCase());
									} else if (result instanceof Number) {
										set.add(result.toString());
									} else if (result instanceof Date) {
										SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
										set.add(sdf.format(result));
									}
									
								}
							} catch (Exception e) {}
							break;
						}
					}
				}
			}
		}
		
		return set;
		
	}
	
}
