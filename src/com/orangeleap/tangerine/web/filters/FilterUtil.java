package com.orangeleap.tangerine.web.filters;

import javax.servlet.http.HttpServletRequest;

public class FilterUtil {

	public static boolean isResourceRequest(HttpServletRequest request) {
	   	String url = request.getRequestURL().toString();
    	return url.endsWith(".gif") 
    	|| url.endsWith(".jpg") 
    	|| url.endsWith(".png")
    	|| url.endsWith(".js")
    	|| url.endsWith(".css")
    	;
	}
	
}
