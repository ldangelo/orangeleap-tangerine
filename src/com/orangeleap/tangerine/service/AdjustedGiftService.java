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

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.validation.BindException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Set;

public interface AdjustedGiftService {

    AdjustedGift readAdjustedGiftByIdCreateIfNull(Constituent constituent, String adjustedGiftId, String originalGiftId);
    
    public AdjustedGift createdDefaultAdjustedGift(Long originalGiftId);

    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId);

    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId);

    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift) throws BindException;

	AdjustedGift editAdjustedGift(AdjustedGift adjustedGift) throws BindException;

    AdjustedGift editAdjustedGift(AdjustedGift adjustedGift, boolean doValidateDistributionLines) throws BindException;

    public BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId);

    public BigDecimal findCurrentTotalAdjustedAmount(List<AdjustedGift> adjustedGifts);

    BigDecimal findCurrentTotalAdjustedAmount(Long originalGiftId, Long adjustedGiftIdToOmit);

    public boolean isAdjustedAmountEqualGiftAmount(Gift gift);

    public boolean isAdjustedAmountEqualGiftAmount(AdjustedGift adjustedGift);

    Map<Long, Long> countAdjustedGiftsByOriginalGiftId(final List<Gift> gifts);

    List<AdjustedGift> readAllAdjustedGiftsByConstituentGiftId(Long constituentId, Long giftId, SortInfo sort, Locale locale);

    int readCountByConstituentGiftId(Long constituentId, Long giftId);

    BigDecimal readTotalAdjustedAmountByConstituentId(Long constituentId);

	BigDecimal findCurrentTotalPaidAdjustedAmount(List<AdjustedGift> adjustedGifts);

	BigDecimal findCurrentTotalPaidAdjustedAmount(Long originalGiftId);

    List<AdjustedGift> readAdjustedGiftsBySegmentationReportIds(Set<Long> reportIds, SortInfo sort, Locale locale);

    int readCountAdjustedGiftsBySegmentationReportIds(Set<Long> reportIds);
}
