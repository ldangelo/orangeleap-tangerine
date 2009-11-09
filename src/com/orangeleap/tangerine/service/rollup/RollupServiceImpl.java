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

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

import com.orangeleap.tangerine.dao.RollupAttributeDao;
import com.orangeleap.tangerine.dao.RollupSeriesDao;
import com.orangeleap.tangerine.dao.RollupSeriesXAttributeDao;
import com.orangeleap.tangerine.dao.RollupValueDao;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
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
    
    
	@Override
    public void validateCubeSize(RollupSeries rollupSeries, RollupAttribute rollupAttribute) {
    	// TODO Determine if parameter combinations (such as daily x constituent) are invalid. 
		// TODO Prob not do basic cardinality checks (will not work for large number of obsolete designation codes)
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
    public List<RollupAttribute> getAllConstituentRollupAttributes() {
		return rollupAttributeDao.readAllConstituentRollupAttributes(); 
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
    public void deleteRollupSeriesById(Long id) { // check site
	}
	
	@Override
    public void deleteRollupAttributeById(Long id) { // check site
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
    public void updateRollupsForGift(Gift gift) {
		//TODO
	}
	
	@Override
    public void updateRollupsForConstituent(Constituent constituent) {
		//TODO
	}

	@Override
    public List<RollupValue> readRollupValuesByAttributeAndConstituentId(Long attributeId, Long constituentId) {
		return rollupValueDao.readRollupValuesByAttributeAndConstituentId(attributeId, constituentId);
	}
    

}