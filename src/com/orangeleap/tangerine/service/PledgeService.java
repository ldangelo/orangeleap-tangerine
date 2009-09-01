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
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Locale;

public interface PledgeService extends CommitmentService<Pledge> {

    public Pledge maintainPledge(Pledge pledge) throws BindException;

    public Pledge editPledge(Pledge pledge) throws BindException;

    public Pledge readPledgeById(Long pledgeId);

    public Pledge readPledgeByIdCreateIfNull(String pledgeId, Constituent constituent);

    public Pledge createDefaultPledge(Constituent constituent);

    public List<Pledge> readPledgesForConstituent(Constituent constituent);

    public List<Pledge> readPledgesForConstituent(Long constituentId);

    public PaginatedResult readPaginatedPledgesByConstituentId(Long constituentId, SortInfo sortinfo);

    public List<Pledge> searchPledges(Map<String, Object> params);

    public boolean arePaymentsAppliedToPledge(Pledge pledge);

    public Map<String, List<Pledge>> findNotCancelledPledges(Long constituentId, String selectedPledgeIds);

    public List<DistributionLine> findDistributionLinesForPledges(Set<String> pledgeIds);

    public boolean canApplyPayment(Pledge pledge);

    public void updatePledgeForGift(Gift gift);

    public void updatePledgeForAdjustedGift(AdjustedGift adjustedGift);

    public void extendPaymentSchedule(Pledge pledge);

    public ScheduledItem getNextPaymentToRun(Pledge pledge);

    List<Pledge> readAllPledgesByConstituentId(Long constituentId, SortInfo sort, Locale locale);

    int readCountByConstituentId(Long constituentId);
}
