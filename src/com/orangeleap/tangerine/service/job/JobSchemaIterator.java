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

package com.orangeleap.tangerine.service.job;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.SchemaService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineDataSource;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class JobSchemaIterator extends QuartzJobBean {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	public JobSchemaIterator() {}

	// Allows one instance on the cluster to run jobs and not the others.
	private static final String JOBS_ENABLED = "tangerine.jobs.enabled";
	private static final String JOBS_FILTER = "tangerine.jobs.filter";



	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        synchronized(JobSchemaIterator.class) {

            
            String jobsEnabledValue = System.getProperty(JOBS_ENABLED);
            String jobsFilterValue = System.getProperty(JOBS_FILTER);
            
            Pattern pattern = null;
            if (jobsFilterValue != null) {
            	logger.info("tangerine.jobs.filter="+jobsFilterValue);
            	pattern = Pattern.compile(jobsFilterValue);
            }
            
            boolean enabled = !"false".equalsIgnoreCase(jobsEnabledValue);
            if (!enabled) {
                logger.info("Skipping jobs: tangerine.jobs.enabled="+jobsEnabledValue);
                return;
            }

            ApplicationContext applicationContext = null;
            SchemaService schemaService = null;
            TangerineDataSource ds = null;
            try {
                applicationContext = getApplicationContext(context);
                schemaService = (SchemaService)applicationContext.getBean("schemaService");
                ds = (TangerineDataSource)applicationContext.getBean("dataSource");
            } catch (Exception e) {
                e.printStackTrace();
                logger.fatal(e);
                return;
            }

            if (!ds.isSplitDatabases()) {
                SiteService ss = (SiteService) applicationContext.getBean("siteService");
                TangerineUserHelper th = (TangerineUserHelper) applicationContext.getBean("tangerineUserHelper");
                List<Site> siteList = ss.readSites();

                for (Site s : siteList) {
                    th.setSystemUserAndSiteName(s.getName());

                    executeInternalForSchema(context, applicationContext);
                }
                return;
            }

            List<String> schemas = schemaService.readSchemas();
            for (String schema : schemas) {
            	try {
            		
            		if (pattern != null && !pattern.matcher(schema).matches()) {
            			continue;
            		}
            		
	                schemaService.setSchema(schema);  //  sets tangerine user helper with site name for TangerineDatasource to read.
	                logger.info("Processing jobs for [" + schema + "] ---------------------------------------------------");  // Must log after changing schema context
	                long t = System.currentTimeMillis();
	                executeInternalForSchema(context, applicationContext);
	                logger.info("Jobs for schema " + schema + " took " + (System.currentTimeMillis() - t)/1000.0f/60f + " minutes.");
            	} catch (Exception e) {
            		e.printStackTrace();
            		logger.error("Error processing job for "+schema, e);
            	}
            }

        }
	}
	
	// Run list of jobs methods in job map.
	private void executeInternalForSchema(JobExecutionContext context, ApplicationContext applicationContext) {
		JobDataMap map = context.getMergedJobDataMap();
		String [] keys = map.getKeys();
		Arrays.sort(keys);
		for (String key : keys) {
			if (key.startsWith("job-")) {
				String value = map.getString(key);
				logger.debug("Running " + key + ": " + value);
				try {
					int i = value.indexOf(".");
					String service = value.substring(0,i);
					String method = value.substring(i+1);
					Object o = applicationContext.getBean(service);
					Method m = o.getClass().getMethod(method, new Class[0]);
					m.invoke(o, new Object[0]);
				} catch (Throwable e) {
					if (e instanceof InvocationTargetException) {
						e = ((InvocationTargetException)e).getTargetException();
					}
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
	}
	
	private static final String APPLICATION_CONTEXT_KEY = "applicationContext";

	private ApplicationContext getApplicationContext(JobExecutionContext context ) throws Exception {
		ApplicationContext applicationContext = null;
		applicationContext = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		if (applicationContext == null) {
			throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");        }
		return applicationContext;    
	}

}
