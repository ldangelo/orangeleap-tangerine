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

import com.orangeleap.tangerine.domain.paymentInfo.AdjustedGift;

import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Set;
import java.math.BigDecimal;

public interface AdjustedGiftDao {

    public AdjustedGift readAdjustedGiftById(Long adjustedGiftId);

    List<AdjustedGift> readAdjustedGiftsByIds(final Set<Long> adjustedGiftIds);

    List<AdjustedGift> readLimitedAdjustedGiftsByIds(Set<Long> adjustedGiftIds, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    public List<AdjustedGift> readAdjustedGiftsForOriginalGiftId(Long originalGiftId);

    public AdjustedGift maintainAdjustedGift(AdjustedGift adjustedGift);

    Map<Long, Long> countAdjustedGiftsByOriginalGiftId(final List<Long> originalGiftIds);

    List<AdjustedGift> readAllAdjustedGiftsByConstituentGiftId(Long constituentId, Long giftId, String sortPropertyName, String direction,
                                                               int start, int limit, Locale locale);

    int readCountByConstituentGiftId(Long constituentId, Long giftId);

    BigDecimal readTotalAdjustedAmountByConstituentId(Long constituentId);

    List<AdjustedGift> readAdjustedGiftsBySegmentationReportIds(Set<Long> reportIds, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountAdjustedGiftsBySegmentationReportIds(Set<Long> reportIds);

    List<AdjustedGift> readAllAdjustedGiftsBySegmentationReportIds(final Set<Long> reportIds);

	public Map<Long, Long> countAdjustedGiftDistroLinesByOriginalGiftId(
			List<Long> giftIds, String constituentReferenceCustomField);

	public List<AdjustedGift> readAllAdjustedGiftDistroLinesByConstituentGiftId(
			Long constituentId, long giftId, String constituentReferenceCustomField, String sort, String dir,
			int start, int limit, Locale locale);

	public int readAdjustedGiftDistroLinesCountByConstituentGiftId(
			Long constituentId, long giftId,
			String constituentReferenceCustomField);
}
