package com.orangeleap.tangerine.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.type.CacheGroupType;

public class CacheGroupFilter extends OncePerRequestFilter {
	
	
    protected final Log logger = LogFactory.getLog(getClass());

	    private Object getBean(HttpServletRequest request, String bean) {
	    	ServletContext servletContext = request.getSession().getServletContext();
	    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    	return wac.getBean(bean);
	    }
	    
	    private boolean suppressCacheCheck(HttpServletRequest request) {
	    	 return FilterUtil.isResourceRequest(request);
	    }

		protected void doFilterInternal(
				HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			
			if (!suppressCacheCheck(request)) {
				
				Cache picklistCache = (Cache)getBean(request, "picklistCache");
				// Add others here...

				
				CacheGroupDao cacheDao = (CacheGroupDao)getBean(request, "cacheGroupDAO");
				List<CacheGroup> cacheGroups = cacheDao.readCacheGroups();
				synchronized (s_cacheGroups) {
					for (CacheGroup dbCacheGroup : cacheGroups) {
						String key = dbCacheGroup.getId().toString();
						CacheGroup memoryCacheGroup = s_cacheGroups.get(key);
						if (memoryCacheGroup != null) {
							if (!memoryCacheGroup.getUpdateDate().equals(dbCacheGroup.getUpdateDate())) {
								if (key.equals(CacheGroupType.PICKLIST.toString())) {
									logger.debug("Update detected, clearing PICKLIST cache.");
									picklistCache.removeAll();
								}
							}
						}
						s_cacheGroups.put(key, dbCacheGroup);
					}
				}
				
				
			}
			
			filterChain.doFilter(request, response);
		
		}
		
		
	    private static Map<String, CacheGroup> s_cacheGroups = new HashMap<String, CacheGroup>();
	    

}
