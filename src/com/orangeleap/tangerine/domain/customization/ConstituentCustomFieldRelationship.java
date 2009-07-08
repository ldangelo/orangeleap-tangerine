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

package com.orangeleap.tangerine.domain.customization;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;

import java.util.Date;

/**
 * Holds custom fields for an instance of a relationship-type custom field row.
 */
public class ConstituentCustomFieldRelationship extends AbstractCustomizableEntity {

    private static final long serialVersionUID = 1L;

    private Long constituentId;
    private String customFieldValue;
    private Date customFieldStartDate;
    private String masterFieldDefinitionId;
    private String siteName;


    public ConstituentCustomFieldRelationship() {
        super();
    }

    public void setConstituentId(Long constituentId) {
        this.constituentId = constituentId;
    }

    public Long getConstituentId() {
        return constituentId;
    }

    public void setMasterFieldDefinitionId(String masterFieldDefinitionId) {
        this.masterFieldDefinitionId = masterFieldDefinitionId;
    }

    public String getMasterFieldDefinitionId() {
        return masterFieldDefinitionId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setCustomFieldValue(String customFieldValue) {
        this.customFieldValue = customFieldValue;
    }

    public String getCustomFieldValue() {
        return customFieldValue;
    }

    public void setCustomFieldStartDate(Date customFieldStartDate) {
        this.customFieldStartDate = customFieldStartDate;
    }

    public Date getCustomFieldStartDate() {
        return customFieldStartDate;
    }


}


