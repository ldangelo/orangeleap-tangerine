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

package com.orangeleap.tangerine.controller.lookup;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;

public class CodeValueComparator implements Comparator<CodeValue> {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    @Override
    public int compare(CodeValue cv1, CodeValue cv2) {
        if (cv1.getDisplayValue() == null) {
            return 1;
        }
        return cv1.getDisplayValue().compareToIgnoreCase(cv2.getDisplayValue());
    }
}
