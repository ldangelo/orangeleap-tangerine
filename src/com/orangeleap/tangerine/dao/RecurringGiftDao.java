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

package com.orangeleap.tangerine.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface RecurringGiftDao {

    public RecurringGift readRecurringGiftById(Long recurringGiftId);
    
    public List<RecurringGift> readRecurringGiftsByConstituentId(Long constituentId);
    
    public List<RecurringGift> readRecurringGifts(Date date, List<String> statuses, long offset, int limit);

    public long readRecurringGiftsCount(Date date, List<String> statuses);

    public RecurringGift maintainRecurringGift(RecurringGift rg);
    
    public void maintainRecurringGiftAmountPaidRemainingStatus(RecurringGift recurringGift);

    public void removeRecurringGift(RecurringGift rg);
    
    public List<RecurringGift> searchRecurringGifts(Map<String, Object> searchParams);

	public PaginatedResult readPaginatedRecurringGiftsByConstituentId(Long constituentId, SortInfo sortinfo);
	
	public List<DistributionLine> findDistributionLinesForRecurringGifts(List<String> recurringGiftIds);
	
	public BigDecimal readAmountPaidForRecurringGiftId(Long recurringGiftId);

    List<RecurringGift> readAllRecurringGiftsByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountByConstituentId(Long constituentId);

    Long readPaymentsAppliedToRecurringGiftId(Long recurringGiftId);
    
    RecurringGift readLargestRecurringGiftByConstituent(Long constituentId, Date fromDate, Date toDate);
    RecurringGift readFirstOrLastRecurringGiftByConstituent(Long constituentId, Date fromDate, Date toDate, boolean first);

}
