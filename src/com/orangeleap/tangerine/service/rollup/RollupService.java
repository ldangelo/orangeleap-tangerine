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

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupValue;
import com.orangeleap.tangerine.type.GiftType;

public interface RollupService {
	
    public boolean validateCubeSize(RollupSeries rollupSeries, RollupAttribute rollupAttribute);

    public List<RollupSeries> getAllRollupSeries();
    public List<RollupAttribute> getAllRollupAttributes();
    public List<RollupAttribute> readAllRollupAttributesByType(String entityType);

    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries);
    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute);

    public void deleteRollupSeriesById(Long id);
    public void deleteRollupAttributeById(Long id);

    public List<RollupSeriesXAttribute> selectRollupSeriesForAttribute(Long attributeId);
    public void maintainRollupSeriesForAttribute(Long attributeId, List<RollupSeriesXAttribute> rollupSeriesXAttributes);

	public List<RollupValue> generateRollupValuesDateRanges(RollupAttribute ra, RollupSeries rs);

    public List<RollupValue> readRollupValuesByAttributeSeriesAndConstituentId(Long attributeId, Long seriesId, Long constituentId);
    public Map<RollupAttribute, Map<RollupSeries, List<RollupValue>>> readGiftViewRollupValuesByConstituentId(Long constituentId);

	public void deleteRollupValuesForAttributeSeries(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date deleteStartDate, Date deleteEndDate);
	public void insertRollupDimensionValues(Object groupByValue, RollupAttribute ra, RollupSeries rs, Date deleteStartDate, Date deleteEndDate);

    public Gift readGiftViewFirstOrLastByConstituentId(Long constituentId, GiftType giftType, String giftStatus, boolean first);
	public Gift readGiftViewLargestByConstituentId(Long constituentId, GiftType giftType, String giftStatus);
	public GiftInKind readGiftInKindViewFirstOrLastByConstituentId(Long constituentId, boolean first);
	public GiftInKind readGiftInKindViewLargestByConstituentId(Long constituentId);
    public Gift readIndirectGiftViewFirstOrLastByConstituentId(Long constituentId, GiftType giftType, String giftStatus, boolean first, String constituentReferenceCustomField);
	public Gift readIndirectGiftViewLargestByConstituentId(Long constituentId, GiftType giftType, String giftStatus, String constituentReferenceCustomField);

	
}
