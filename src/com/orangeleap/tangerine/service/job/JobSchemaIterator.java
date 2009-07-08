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
import java.util.Date;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.service.SchemaService;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.util.TangerineDataSource;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class JobSchemaIterator extends QuartzJobBean {

	/** Logger for this class and subclasses */
	protected final Log logger = OLLogger.getLog(getClass());

	public JobSchemaIterator() {}

	// Allows one instance on the cluster to run jobs and not the others.
	private static final String JOBS_ENABLED = "tangerine.jobs.enabled";


    private static long minimumTime = 1000 * 60 * 60 * 22; // 1 day - 2 hrs for time changes and delays
    
    private static Date lastRun = new Date(0);

    private static boolean isTooFrequent(Date d1, Date d2) {
        return d1.getTime() - d2.getTime() < minimumTime;
    }


	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        synchronized(JobSchemaIterator.class) {

            
//            Date now = new Date();
//            if (isTooFrequent(now, lastRun)) {
//                logger.error("Skipping multiple quartz run.");
//                return;
//            }
//            lastRun = now;



            String jobsEnabledValue = System.getProperty(JOBS_ENABLED);
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
                logger.info("Processing jobs for "+schema);
                schemaService.setSchema(schema);  //  sets tangerine user helper with site name for TangerineDatasource to read.
                executeInternalForSchema(context, applicationContext);
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
