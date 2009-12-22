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

package com.orangeleap.tangerine.web.filters;


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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orangeleap.tangerine.dao.CacheGroupDao;
import com.orangeleap.tangerine.domain.CacheGroup;
import com.orangeleap.tangerine.service.customization.RulesConfServiceImpl;
import com.orangeleap.tangerine.type.CacheGroupType;
import com.orangeleap.tangerine.util.OLLogger;

public class CacheGroupFilter extends OncePerRequestFilter {


    protected final Log logger = OLLogger.getLog(getClass());

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

            Cache picklistCache = (Cache) getBean(request, "picklistCache");
            Cache pageCustomizationCache = (Cache) getBean(request, "pageCustomizationCache");
            Cache messageResourceCache = (Cache) getBean(request, "messageResourceCache");
            Cache ruleGeneratedCodeCache = (Cache) getBean(request, "ruleGeneratedCodeCache");
            // Add others here...


            CacheGroupDao cacheDao = (CacheGroupDao) getBean(request, "cacheGroupDAO");
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
                            if (key.equals(CacheGroupType.PAGE_CUSTOMIZATION.toString())) {
                                logger.debug("Update detected, clearing PAGE_CUSTOMIZATION cache.");
                                pageCustomizationCache.removeAll();
                            }
                            if (key.equals(CacheGroupType.MESSAGE_RESOURCE.toString())) {
                                logger.debug("Update detected, clearing MESSAGE_RESOURCE cache.");
                                messageResourceCache.removeAll();
                            }
                            if (key.equals(CacheGroupType.RULE_GENERATED_CODE.toString())) {
                                logger.debug("Update detected, clearing RULE_GENERATED_CODE cache.");
                                ruleGeneratedCodeCache.removeAll();
                                RulesConfServiceImpl.resetGroovyObjectCache();
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
