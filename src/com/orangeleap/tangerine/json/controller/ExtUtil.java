package com.orangeleap.tangerine.json.controller;

import org.apache.commons.lang.StringUtils;

public class ExtUtil {

    public static String scrub(String s) {
    	return StringUtils.trimToEmpty(s).replace('\n', ' ').replace('\r', ' ');
    }
	
}
