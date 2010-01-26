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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.ConstituentDao;
import com.orangeleap.tangerine.dao.RollupSeriesDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.domain.rollup.RollupValueSource;
import com.orangeleap.tangerine.service.impl.AbstractTangerineService;
import com.orangeleap.tangerine.util.OLLogger;

// Non-transactional service
@Service("rollupHelperService")
public class RollupHelperServiceImpl extends AbstractTangerineService implements RollupHelperService {

    protected final Log logger = OLLogger.getLog(getClass());

    private static final boolean enableLiveRollups = "true".equalsIgnoreCase(System.getProperty("orangeleap.enable.live.rollups"));
    
    @Resource(name = "rollupService")
    private RollupService rollupService;
    
    @Resource(name = "rollupSeriesDAO")
    private RollupSeriesDao rollupSeriesDao;

    @Resource(name = "constituentDAO")
    private ConstituentDao constituentDao;

	// Rollup values updaters
	
	@Override
    public void updateRollupsForConstituentRollupValueSource(RollupValueSource rvs) {
		
		List<RollupAttribute> ras = rollupService.readAllRollupAttributesByType("constituent"); 
		
		// Sometimes only the constituentId is populated rather than the object.
		Long constituentId = null;
		Constituent constituent = rvs.getConstituent();
		if (constituent == null) {
			constituentId = rvs.getConstituentId();
		} else {
			constituentId = constituent.getId();
		}
		
		updateRollups(ras, constituentId);
	}

	@Override
    public void refreshByConstituent(Long constituentId) {
		List<RollupAttribute> ras = rollupService.readAllRollupAttributesByType("constituent"); 
		updateRollups(ras, constituentId);
	}
	
   @Override
    public void updateAllRollupsForSite() {
	    List<RollupAttribute> ras = rollupService.getAllRollupAttributes(); 
	    updateRollups(ras, null);
	}
	
	@Override
    public void updateSummaryRollupsForSite() {
	    List<RollupAttribute> ras = rollupService.readAllRollupAttributesByType("summary"); 
	    updateRollups(ras, null);
	}
	
	// Process this many constituents at at time
	private static final Long KEY_RANGE_INTERVAL = 100000L;  // 100k

	private void updateRollups(List<RollupAttribute> ras, Object groupByValue) {
		
		//Long[] range = constituentDao.getConstituentIdRange();
		
	    for (RollupAttribute ra : ras) {
	    	
			//boolean isIterateConstituentStat = groupByValue == null && (ra.getRollupStatType().endsWith("_CONSTITUENT"));
	    	
	    	List<RollupSeriesXAttribute> rsxas = rollupService.selectRollupSeriesForAttribute(ra.getId());
	    	for (RollupSeriesXAttribute rsxa : rsxas) {
	    		RollupSeries rs = rollupSeriesDao.readRollupSeriesById(rsxa.getRollupSeriesId());
	    		List<RollupValue> rvs = rollupService.generateRollupValuesDateRanges(ra, rs);
	    		if (rvs.size()  > 0) {
	    			Date deleteStartDate =  rvs.get(0).getStartDate();
	    			Date deleteEndDate =  rvs.get(rvs.size()-1).getEndDate();
	    			if (!rs.getKeepUnmaintained()) {
	    				deleteStartDate = CustomField.PAST_DATE;
	    				deleteEndDate = CustomField.FUTURE_DATE;
	    			}
	    			rollupService.deleteRollupValuesForAttributeSeries(groupByValue, ra, rs, deleteStartDate, deleteEndDate);
	    		}
	    		for (RollupValue rv : rvs) {
//	    			if (isIterateConstituentStat) {
//		    			if (range[0] != null) for (long groupByRange1 = range[0]; groupByRange1 < range[1]; groupByRange1 += KEY_RANGE_INTERVAL) {
//		    				rollupService.insertRollupDimensionValues(groupByValue, ra, rs, rv.getStartDate(), rv.getEndDate(), groupByRange1, groupByRange1 + KEY_RANGE_INTERVAL - 1);
//		    			}
//	    			} else {
	    				rollupService.insertRollupDimensionValues(groupByValue, ra, rs, rv.getStartDate(), rv.getEndDate(), 0L, Long.MAX_VALUE);
//	    			}
	    		}
	    	}
	    }
	}
}