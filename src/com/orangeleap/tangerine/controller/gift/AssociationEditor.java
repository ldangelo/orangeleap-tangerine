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

package com.orangeleap.tangerine.controller.gift;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;

public class AssociationEditor extends PropertyEditorSupport {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    public AssociationEditor() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Set<String> idsStrings = StringUtils.commaDelimitedListToSet(text);
        if (idsStrings != null && idsStrings.isEmpty() == false) {
            List<Long> idsLongs = new ArrayList<Long>();
            for (String s : idsStrings) {
                if (NumberUtils.isDigits(s)) {
                    idsLongs.add(Long.parseLong(s));
                }
            }
            setValue(idsLongs);
        }
    }
}
