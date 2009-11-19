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


// note: processing rollups over a midnite break is not recommended.

package com.orangeleap.tangerine.service.rollup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.orangeleap.tangerine.dao.RollupAttributeDao;
import com.orangeleap.tangerine.dao.RollupSeriesDao;
import com.orangeleap.tangerine.dao.RollupSeriesXAttributeDao;
import com.orangeleap.tangerine.dao.RollupValueDao;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesType;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;

@Transactional(propagation = Propagation.REQUIRED)
@Service("rollupService")
public class RollupServiceImpl extends AbstractTangerineService implements RollupService {

    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "rollupSeriesDAO")
    private RollupSeriesDao rollupSeriesDao;

    @Resource(name = "rollupAttributeDAO")
    private RollupAttributeDao rollupAttributeDao;

    @Resource(name = "rollupSeriesXAttributeDAO")
    private RollupSeriesXAttributeDao rollupSeriesXAttributeDao;

    @Resource(name = "rollupValueDAO")
    private RollupValueDao rollupValueDao;

    @Resource(name = "siteService")
    private SiteService siteService;
    
    
	// Determine if certain parameter combinations (such as daily x constituent) are unsupported. 
	// Cardinality checks will not work for large number of obsolete designation codes
	@Override
    public boolean validateCubeSize(RollupSeries rollupSeries, RollupAttribute rollupAttribute) {
		
		RollupSeriesType rollupSeriesType = rollupSeries.getSeriesType();
		
		if (rollupAttribute.getRollupEntityType().equals("constituent")) {
			 if ( 
				 RollupSeriesType.DAY.equals(rollupSeriesType) 
				 || RollupSeriesType.WEEK.equals(rollupSeriesType) 
				 || RollupSeriesType.MONTH.equals(rollupSeriesType) 
			 	)
			    return false;
		}
		
		return true;
		
	}
	
	@Override
    public List<RollupSeries> getAllRollupSeries() { 
		return rollupSeriesDao.readAllRollupSeries();
	}
	
	@Override
    public List<RollupAttribute> getAllRollupAttributes() {
		return rollupAttributeDao.readAllRollupAttributes(); 
	}

	@Override
    public List<RollupAttribute> readAllRollupAttributesByType(String entityType) {
		return rollupAttributeDao.readAllRollupAttributesByType(entityType); 
	}

	@Override
    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries) {
		return rollupSeriesDao.maintainRollupSeries(rollupSeries);
	}
	
	@Override
    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute) {
		return rollupAttributeDao.maintainRollupAttribute(rollupAttribute);
	}

	@Override
    public void deleteRollupSeriesById(Long id) { 
		rollupSeriesDao.deleteRollupSeriesById(id);
	}
	
	@Override
    public void deleteRollupAttributeById(Long id) { 
		rollupAttributeDao.deleteRollupAttributeById(id);
	}

	@Override
	public List<RollupSeriesXAttribute> selectRollupSeriesForAttribute(Long attributeId) {
		return rollupSeriesXAttributeDao.selectRollupSeriesForAttribute(attributeId);
	}
	
	@Override
	public void maintainRollupSeriesForAttribute(Long attributeId, List<RollupSeriesXAttribute> rollupSeriesXAttributes) {
		rollupSeriesXAttributeDao.maintainRollupSeriesXAttribute(attributeId, rollupSeriesXAttributes);
	}

	@Override
    public List<RollupValue> readRollupValuesByAttributeSeriesAndConstituentId(Long attributeId, Long seriesId, Long constituentId) {
		return rollupValueDao.readRollupValuesByAttributeSeriesAndConstituentId(attributeId, seriesId, constituentId);
	}
	
	@Override
    public Map<RollupAttribute, Map<RollupSeries, List<RollupValue>>> readGiftViewRollupValuesByConstituentId(Long constituentId) {
		Map<RollupAttribute, Map<RollupSeries, List<RollupValue>>> result = new TreeMap<RollupAttribute, Map<RollupSeries, List<RollupValue>>>();
		List<RollupAttribute> ras = readAllRollupAttributesByType("constituent"); 
		for (RollupAttribute ra: ras) {
			Map<RollupSeries, List<RollupValue>> attributeresult = new TreeMap<RollupSeries, List<RollupValue>>();
			result.put(ra, attributeresult);
			List<RollupSeriesXAttribute> sxas = rollupSeriesXAttributeDao.selectRollupSeriesForAttribute(ra.getId());
			for (RollupSeriesXAttribute sxa : sxas) {
				RollupSeries rs = rollupSeriesDao.readRollupSeriesById(sxa.getRollupSeriesId());
				List<RollupValue> rvs = readRollupValuesByAttributeSeriesAndConstituentId(ra.getId(), rs.getId(), constituentId); 
				attributeresult.put(rs, rvs);
			}
		}
		return result;
	}	
    

	@Override
	public void deleteRollupValuesForAttributeSeries(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date deleteStartDate, Date deleteEndDate) {
		rollupValueDao.deleteRollupValuesForAttributeSeries(groupByValue, ra, rs, deleteStartDate, deleteEndDate);
    }
	
	@Override
	public void insertRollupDimensionValues(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date deleteStartDate, Date deleteEndDate) {
		rollupValueDao.insertRollupDimensionValues(groupByValue, ra, rs, deleteStartDate, deleteEndDate);
    }
	
	// Create date range templates for series types
	@Override
	public List<RollupValue> generateRollupValuesDateRanges(RollupAttribute ra, RollupSeries rs) {
		List<RollupValue> result = new ArrayList<RollupValue>();
		
		// Determine start date, number and amount of increments based on series type.
		// number of future periods (subtract from total #of periods) - would be 1 less than total for current and future only
		
		Date beginDate = new java.util.Date(); 
		int datefield = Calendar.YEAR;
		int incrementAmount = 1;

		RollupSeriesType rst = rs.getSeriesType();
		if (rst.equals(RollupSeriesType.ALLTIME)) {
			RollupValue rv = new RollupValue();
			rv.setRollupSeriesId(rs.getId());
			rv.setRollupAttributeId(ra.getId());
			rv.setStartDate(CustomField.PAST_DATE);
			rv.setEndDate(CustomField.FUTURE_DATE);
			result.add(rv);
			return result;
		} else if (rst.equals(RollupSeriesType.CALENDAR_YEAR)) {
			datefield = Calendar.YEAR;
			beginDate = DateUtils.truncate(beginDate, Calendar.YEAR);
		} else if (rst.equals(RollupSeriesType.FISCAL_YEAR)) {
			datefield = Calendar.YEAR;
			Date fy = getFiscalYearStartDate();
			if (fy == null) {
				beginDate = DateUtils.truncate(beginDate, Calendar.YEAR);
			} else {
				beginDate = fy;
			}
		} else if (rst.equals(RollupSeriesType.MONTH)) {
			datefield = Calendar.MONTH;
			beginDate = DateUtils.truncate(beginDate, Calendar.MONTH);
		} else if (rst.equals(RollupSeriesType.WEEK)) {
			datefield = Calendar.DAY_OF_YEAR;
			incrementAmount = 7;
			beginDate = DateUtils.truncate(beginDate, Calendar.WEEK_OF_YEAR);
		} else if (rst.equals(RollupSeriesType.DAY)) {
			datefield = Calendar.DATE;
			beginDate = DateUtils.truncate(beginDate, Calendar.DATE);
		} 
		
		// Set starting period start date
		int periodOffset = -(int)(rs.getMaintainPeriods().longValue() - rs.getFuturePeriods().longValue() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(datefield, incrementAmount * periodOffset);
		
		// Generate date ranges
		for (int i = 0; i < rs.getMaintainPeriods(); i++) {
			RollupValue rv = new RollupValue();
			rv.setStartDate(cal.getTime());
			cal.add(datefield, incrementAmount);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(cal.getTime());
			cal2.add(Calendar.DATE, -1);
			rv.setEndDate(cal2.getTime());
			result.add(rv);
		}
		
		return result;
	}
	
	private Date getFiscalYearStartDate() {
		try {
			Map<String, String> map = siteService.getSiteOptionsMap();
			String month = map.get(StringConstants.FY_START_MONTH_SITE_OPTION_KEY);
			String date = map.get(StringConstants.FY_START_DATE_SITE_OPTION_KEY);
			if (month == null || month.trim().length() == 0) return null;
			if (date == null || date.trim().length() == 0) date = "1";
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, new Integer(month)-1);
			cal.set(Calendar.DATE, new Integer(date));
			cal = DateUtils.truncate(cal, Calendar.DATE);
			if (cal.after(DateUtils.truncate(Calendar.getInstance(), Calendar.DATE))) {
				cal.add(Calendar.YEAR, -1);
			}
			return cal.getTime();
		} catch (Exception e) {
			logger.error("Error determining Fiscal Year start date for rollups - using calendar dates.",e);
			return null;
		}
	}


}