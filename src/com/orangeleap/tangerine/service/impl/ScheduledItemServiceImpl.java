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

package com.orangeleap.tangerine.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import com.orangeleap.tangerine.dao.ScheduledItemDao;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.Schedulable;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.service.ScheduledItemService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.TangerineUserHelper;

@Service("scheduledItemService")
@Transactional(propagation = Propagation.REQUIRED)
public class ScheduledItemServiceImpl extends AbstractTangerineService implements ScheduledItemService {
	

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "scheduledItemDAO")
    private ScheduledItemDao scheduledItemDao;

    @Resource(name = "tangerineUserHelper")
    private TangerineUserHelper tangerineUserHelper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {BindException.class})
    public ScheduledItem maintainScheduledItem(ScheduledItem scheduledItem) {
        Long lookupUserId = tangerineUserHelper.lookupUserId();
        if (lookupUserId != null) {
        	scheduledItem.setModifiedBy(tangerineUserHelper.lookupUserId());
        }
    	return scheduledItemDao.maintainScheduledItem(scheduledItem);
    }

    @Override
    public ScheduledItem readScheduledItemById(Long scheduledItemId) {
    	return scheduledItemDao.readScheduledItemById(scheduledItemId);
    }

    @Override
    public List<ScheduledItem> readScheduledItemsBySourceEntityId(Schedulable scheduleable) {
    	return scheduledItemDao.readScheduledItemsBySourceEntityId(scheduleable.getType(), scheduleable.getId());
    }

    @Override
    public ScheduledItem getDefaultScheduledItem(AbstractEntity entity) {
    	ScheduledItem item = new ScheduledItem();
    	item.setSourceEntity(entity.getType());
    	item.setSourceEntityId(entity.getId());
    	return item;
    }
    
    // Creates (if previousSchedulable == null) or modifies (if previousSchedulable!=null) 
    // list of associated scheduled items based on schedulable criteria and any existing scheduled item ORIGINAL_SCHEDULED_DATEs.
    // For a schedulable with no (indefinite) end date, updates schedule thru specified toDate.
    // Will change ORIGINAL_SCHEDULE_DATEs and/or add new ones.
    // Does not change any items with null ORIGINAL_SCHEDULE dates (manually added scheduled items).
    // Also changes ACTUAL_SCHEDULE_DATE only if it previously matched ORIGINAL_SCHEDULE_DATE (if it doesn't match, 
    // the individual scheduled item was previously changed manually).
    public List<ScheduledItem> updateSchedule(Schedulable previousSchedulable, Schedulable updatedSchedulable, Date toDate) {
    	List<ScheduledItem> list = new ArrayList<ScheduledItem>();
    	return list;
    }

}
