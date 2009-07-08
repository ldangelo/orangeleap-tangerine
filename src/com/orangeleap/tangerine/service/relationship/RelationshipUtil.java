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

package com.orangeleap.tangerine.service.relationship;

import com.orangeleap.tangerine.util.StringConstants;

import java.util.ArrayList;
import java.util.List;

public class RelationshipUtil {
    public static List<Long> getIds(String fieldValue) {
        List<Long> ids = new ArrayList<Long>();
        if (fieldValue == null) {
            return ids;
        }
        String[] sids = fieldValue.split(StringConstants.CUSTOM_FIELD_SEPARATOR);
        for (String sid : sids) {
            if (sid.length() > 0) {
                Long id = new Long(sid);
                ids.add(id);
            }
        }
        return ids;
    }

    public static String getIdString(List<Long> ids) {
        StringBuilder sb = new StringBuilder();
        for (Long id : ids) {
            if (sb.length() > 0) {
                sb.append(StringConstants.CUSTOM_FIELD_SEPARATOR);
            }
            sb.append(id);
        }
        return sb.toString();
    }


}
