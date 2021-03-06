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

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.Set;

public interface GiftDao {

    public Gift maintainGift(Gift gift);

    public Gift readGiftById(Long giftId);

    List<Gift> readGiftsByIds(Set<Long> giftIds);

    List<Gift> readLimitedGiftsByIds(Set<Long> giftIds, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId);

    List<Gift> searchGifts(Map<String, Object> parameters, String sortPropertyName, String direction, int start, int limit, Locale locale);
    
    public List<Gift> searchGifts(Map<String, Object> params);

    public List<Gift> readAllGiftsBySite();

    public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate);

	public Gift readFirstOrLastGiftByConstituent(Long constituentId, Date fromDate, Date toDate, GiftType giftType, String giftStatus, boolean first);

	public Gift readLargestGiftByConstituent(Long constituentId, Date fromDate, Date toDate, GiftType giftType, String giftStatus);

    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

    public List<Constituent> analyzeLapsedDonor(Date beginDate, Date currentDate);

    List<Gift> readGiftsBySegmentationReportIds(Set<Long> reportIds, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountGiftsBySegmentationReportIds(Set<Long> reportIds);

    List<Gift> readAllGiftsBySegmentationReportIds(final Set<Long> reportIds);

    public PaginatedResult readPaginatedGiftListByConstituentId(Long constituentId, SortInfo sortinfo);

    List<Gift> readAllGiftsByConstituentId(Long constituentId, String sortPropertyName, String direction,
                                                         int start, int limit, Locale locale);

    int readCountByConstituentId(Long constituentId);

	public List<Gift> readGiftDistroLinesByConstituentId(Long constituentId, String constituentReferenceCustomField, String sort, String dir,
			int start, int limit, Locale locale);

	int readGiftDistroLinesCountByConstituentId(Long constituentId, String constituentReferenceCustomField);

	Gift readLargestIndirectGiftByConstituent(Long constituentId,
			Date fromDate, Date toDate, GiftType giftType, String giftStatus,
			String constituentReferenceCustomField);

	Gift readFirstOrLastIndirectGiftByConstituent(Long constituentId,
			Date fromDate, Date toDate, GiftType giftType, String giftStatus,
			boolean first, String constituentReferenceCustomField);
	
    PaginatedResult readGiftsToReprocess(SortInfo sortinfo);


}
