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

package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.type.CacheGroupType;

import java.io.Serializable;

public class CacheGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    public CacheGroup() {
        super();
    }

    public CacheGroup(CacheGroupType id) {
        super();
        this.setId(id);
    }

    private CacheGroupType id;
    private java.util.Date updateDate;

    public void setId(CacheGroupType id) {
        this.id = id;
    }

    public CacheGroupType getId() {
        return id;
    }

    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    public String toString() {
        return id + " " + updateDate;
    }


}