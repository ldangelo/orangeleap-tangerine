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
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

public interface PledgeDao {

    public Pledge maintainPledge(Pledge pledge);

    public Pledge readPledgeById(Long pledgeId);

    public List<Pledge> readPledgesByConstituentId(Long constituentId);
    
    public List<Pledge> readPledges(Date date, List<String> statuses, long offset, int limit);

    public long readPledgesCount(Date date, List<String> statuses);

    public List<Pledge> findNotCancelledPledges(Long constituentId);

    public List<DistributionLine> findDistributionLinesForPledges(List<String> pledgeIds);
    
    public List<Pledge> searchPledges(Map<String, Object> params);

	public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo);
	
	public BigDecimal readAmountPaidForPledgeId(Long pledgeId);
	
	public Long readPaymentsAppliedToPledgeId(Long pledgeId);
	
	public void maintainPledgeAmountPaidRemainingStatus(Pledge pledge);

    List<Pledge> readAllPledgesByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountByConstituentId(Long constituentId);
    
	Pledge readLargestPledgeByConstituent(Long constituentId, Date fromDate, Date toDate);
	Pledge readFirstOrLastPledgeByConstituent(Long constituentId, Date fromDate, Date toDate, boolean first);

}
