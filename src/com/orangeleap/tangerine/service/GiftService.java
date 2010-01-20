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
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.web.common.PaginatedResult;
import com.orangeleap.tangerine.web.common.SortInfo;
import org.springframework.validation.BindException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public interface GiftService {

    public Gift maintainGift(Gift gift) throws BindException;

    public Gift editGift(Gift gift) throws BindException;

    Gift editGift(Gift gift, boolean doValidateDistributionLines) throws BindException;

    public Gift readGiftById(Long giftId);

    List<Gift> readGiftsByIds(Set<Long> giftIds);

    List<Gift> readLimitedGiftsByIds(Set<Long> giftIds, SortInfo sortInfo, Locale locale);

    public Gift readGiftByIdCreateIfNull(Constituent constituent, String giftId);

    public List<Gift> readMonetaryGifts(Constituent constituent);

    public List<Gift> readMonetaryGifts(Long constituentId);

    public List<Gift> searchGifts(Map<String, Object> params);

    List<Gift> searchGifts(Map<String, Object> params, SortInfo sort, Locale locale);

    public Gift createDefaultGift(Constituent constituent);

    public double analyzeMajorDonor(Long constituentId, Date beginDate, Date currentDate);

    public List<Gift> readMonetaryGiftsByConstituentId(Long constituentId);

    public List<Gift> readAllGiftsBySiteName();

    public List<Gift> readAllGiftsByDateRange(Date fromDate, Date toDate);

    public PaginatedResult readPaginatedGiftList(Long constituentId, SortInfo sortinfo);

    public List<DistributionLine> combineGiftCommitmentDistributionLines(List<DistributionLine> giftDistributionLines, List<DistributionLine> commitmentLines, DistributionLine defaultDistributionLine, BigDecimal amount, int numCommitments, Constituent constituent, boolean isPledge);

    public void checkAssociatedPledgeIds(Gift gift);

    public void checkAssociatedRecurringGiftIds(Gift gift);

    List<Gift> readGiftsBySegmentationReportIds(Set<Long> reportIds, SortInfo sort, Locale locale);

    int readCountGiftsBySegmentationReportIds(Set<Long> reportIds);

    List<Gift> readAllGiftsBySegmentationReportIds(final Set<Long> reportIds);

    Gift reprocessGift(Gift gift) throws BindException;

    Map<String, Object> readNumGiftsTotalAmount(Long constituentId);

    List<Gift> readAllGiftsByConstituentId(Long constituentId, SortInfo sort, Locale locale);

    int readCountByConstituentId(Long constituentId);
    
    void updateAdjustedAmount(Gift gift);

	List<Gift> readGiftDistroLinesByConstituentId(Long constituentId, String constituentReferenceCustomField, SortInfo sort, Locale locale);

	int readGiftDistroLinesCountByConstituentId(Long constituentId, String constituentReferenceCustomField);

	void reprocessGifts();
	
}
