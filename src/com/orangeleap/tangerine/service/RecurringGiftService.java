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

package com.orangeleap.tangerine.service;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.ScheduledItem;
import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.validation.BindException;

import java.util.*;

public interface RecurringGiftService extends CommitmentService<RecurringGift> {
    
    public RecurringGift readRecurringGiftById(Long recurringGiftId);
    
    public RecurringGift readRecurringGiftByIdCreateIfNull(String recurringGiftId, Constituent constituent);
    
    public RecurringGift createDefaultRecurringGift(Constituent constituent);

    public RecurringGift maintainRecurringGift(RecurringGift recurringGift) throws BindException;

    public RecurringGift editRecurringGift(RecurringGift recurringGift) throws BindException;
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Constituent constituent);
    
    public List<RecurringGift> readRecurringGiftsForConstituent(Long constituentId);
    
	public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo);
    
    public void processRecurringGift(RecurringGift recurringGift, ScheduledItem scheduledItem);
    
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> params);
    
    public Map<String, List<RecurringGift>> findGiftAppliableRecurringGiftsForConstituent(Long constituentId, String selectedRecurringGiftIds);
    
    public List<RecurringGift> filterApplicableRecurringGiftsForConstituent(List<RecurringGift> gifts, Date nowDt);
    
    public List<DistributionLine> findDistributionLinesForRecurringGifts(Set<String> recurringGiftIds);

    public boolean canApplyPayment(RecurringGift recurringGift); 
    
    public void updateRecurringGiftForGift(Gift gift);
    
    public void updateRecurringGiftForAdjustedGift(AdjustedGift adjustedGift);
    
    public void setRecurringGiftStatus(RecurringGift recurringGift);
    
    public ScheduledItem getNextPaymentToRun(RecurringGift recurringGift);
    
    public void extendPaymentSchedule(RecurringGift recurringGift);

    List<RecurringGift> readAllRecurringGiftsByConstituentId(Long constituentId, SortInfo sort, Locale locale);

    int readCountByConstituentId(Long constituentId);
}
