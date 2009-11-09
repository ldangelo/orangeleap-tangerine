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

package com.orangeleap.tangerine.service.rollup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.RollupAttributeDao;
import com.orangeleap.tangerine.dao.RollupSeriesDao;
import com.orangeleap.tangerine.dao.RollupSeriesXAttributeDao;
import com.orangeleap.tangerine.dao.RollupValueDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesType;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.domain.rollup.RollupValueSource;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

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

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;
    
    
	// Determine if certain parameter combinations (such as daily x constituent) are unsupported. 
	// Cardinality checks will not work for large number of obsolete designation codes
	@Override
    public boolean validateCubeSize(RollupSeries rollupSeries, RollupAttribute rollupAttribute) {
		
		RollupSeriesType rollupSeriesType = rollupSeries.getSeriesType();
		
		if (rollupAttribute.getRollupEntityType().equals("constituent")) {
			 if ( RollupSeriesType.DAY.equals(rollupSeriesType) || RollupSeriesType.WEEK.equals(rollupSeriesType) )
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
    public List<RollupValue> readRollupValuesByAttributeAndConstituentId(Long attributeId, Long constituentId) {
		return rollupValueDao.readRollupValuesByAttributeAndConstituentId(attributeId, constituentId);
	}
    
	// Rollup values updaters
	
	@Override
    public void updateRollupsForRollupValueSource(RollupValueSource rvs) {
		//TODO
	}
	
	@Override
    public void updateRollupsForConstituent(Constituent constituent) {
		//TODO
	}

	@Override
    public void updateAllRollupsForSite() {
		
	    List<RollupAttribute> ras = getAllRollupAttributes(); /// or byConstituent for constituent rollup only
	    for (RollupAttribute ra : ras) {
	    	List<RollupSeriesXAttribute> rsxas = selectRollupSeriesForAttribute(ra.getId());
	    	for (RollupSeriesXAttribute rsxa : rsxas) {
	    		RollupSeries rs = rollupSeriesDao.readRollupSeriesById(rsxa.getRollupSeriesId());
	    		List<RollupValue> rvs = generateRollupValuesDateRanges(rs, ra);
	    		for (RollupValue rv : rvs) {
	    			Date startDate = rv.getStartDate();
	    			Date endDate = rv.getEndDate();
	    			
	    		}
	    	}
	    }
		
	}
	
	// Create date range templates for series types
	@Override
	public List<RollupValue> generateRollupValuesDateRanges(RollupSeries rs, RollupAttribute ra) {
		List<RollupValue> result = new ArrayList<RollupValue>();
		
		// Determine start date, number and amount of increments based on series type.
		// number of future periods (subtract from total #of periods) - would be 1 less than total for current and future only
		Date beginDate = null;
		int datefield = Calendar.YEAR;
		int incrementAmount = 1;
		RollupSeriesType rst = rs.getSeriesType();
		if (rst.equals(RollupSeriesType.ALLTIME)) {
			RollupValue rv = new RollupValue();
			rv.setStartDate(CustomField.PAST_DATE);
			rv.setEndDate(CustomField.FUTURE_DATE);
			result.add(rv);
			return result;
		} else if (rst.equals(RollupSeriesType.CALENDAR_YEAR)) {
			datefield = Calendar.YEAR;
		} else if (rst.equals(RollupSeriesType.FISCAL_YEAR)) {
			// TODO from beginDate = siteoptons
			datefield = Calendar.YEAR;
		} else if (rst.equals(RollupSeriesType.MONTH)) {
			datefield = Calendar.MONTH;
		} else if (rst.equals(RollupSeriesType.WEEK)) {
			datefield = Calendar.DAY_OF_YEAR;
			incrementAmount = 7;
		} else if (rst.equals(RollupSeriesType.DAY)) {
			datefield = Calendar.DATE;
		} 
		
		// Generate date ranges
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
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


}