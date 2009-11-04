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

import java.util.List;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.rollup.RollupAttribute;
import com.orangeleap.tangerine.domain.rollup.RollupSeries;
import com.orangeleap.tangerine.domain.rollup.RollupSeriesXAttribute;

public interface RollupService {
	
    public void validateCubeSize(RollupSeries rollupSeries, RollupAttribute rollupAttribute);

    public List<RollupSeries> getAllRollupSeries();
    public List<RollupAttribute> getAllRollupAttributes();

    public RollupSeries maintainRollupSeries(RollupSeries rollupSeries);
    public RollupAttribute maintainRollupAttribute(RollupAttribute rollupAttribute);

    public void deleteRollupSeriesById(Long id);
    public void deleteRollupAttributeById(Long id);

    public void maintainRollupSeriesForAttribute(List<RollupSeriesXAttribute> rollupSeriesXAttributes);

    public void updateRollupsForGift(Gift gift);
    public void updateRollupsForConstituent(Constituent constituent);
    
}