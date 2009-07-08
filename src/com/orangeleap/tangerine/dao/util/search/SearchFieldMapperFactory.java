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

package com.orangeleap.tangerine.dao.util.search;

import com.orangeleap.tangerine.type.EntityType;

public class SearchFieldMapperFactory {
    public SearchFieldMapper getMapper(EntityType entitytype) {

        if (entitytype == EntityType.constituent) {
            return new ConstituentSearchFieldMapper();
        }
        if (entitytype == EntityType.gift) {
            return new GiftSearchFieldMapper();
        }
        if (entitytype == EntityType.pledge) {
            return new PledgeSearchFieldMapper();
        }
        if (entitytype == EntityType.recurringGift) {
            return new RecurringGiftSearchFieldMapper();
        }

        throw new RuntimeException("Invalid entity.");
    }
}
