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

import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectedIds {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private List<String> names;
    private List<String> ids;
    private String idToCheck;

    @SuppressWarnings("unchecked")
    public SelectedIds(final String idsString) {
        if (idsString == null) {
            this.ids = UnmodifiableList.decorate(new ArrayList<String>(0));
            this.names = UnmodifiableList.decorate(new ArrayList<String>(0));
        } else {
            String[] nameIdPair = StringUtils.delimitedListToStringArray(idsString, "^");
            if (nameIdPair == null || nameIdPair.length == 0) {
                this.ids = UnmodifiableList.decorate(new ArrayList<String>(0));
                this.names = UnmodifiableList.decorate(new ArrayList<String>(0));
            } else {
                this.names = new ArrayList<String>();
                this.ids = new ArrayList<String>();
                for (String nameId : nameIdPair) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("SelectedIds: adding " + nameId);
                    }
                    String[] pair = nameId.split("\\|");
                    if (pair != null && pair.length == 2) {
                        names.add(pair[0]);
                        ids.add(pair[1]);
                    }
                }
                this.names = UnmodifiableList.decorate(this.names);
                this.ids = UnmodifiableList.decorate(this.ids);
            }
        }
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIdToCheck(String idToCheck) {
        this.idToCheck = idToCheck;
    }

    public boolean isCheckSelectedId() {
        return getIds().contains(idToCheck);
    }

    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(this.ids);
    }
}
