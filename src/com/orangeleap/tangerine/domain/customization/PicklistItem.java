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
import com.orangeleap.tangerine.domain.Auditable;
import com.orangeleap.tangerine.domain.GeneratedId;
import org.springframework.util.StringUtils;

import java.io.Serializable;

public class PicklistItem extends AbstractCustomizableEntity implements Auditable, GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private String itemName;
    private String defaultDisplayValue;
    private String longDescription;
    private String referenceValue;
    private String suppressReferenceValue;
    private Integer itemOrder;
    private boolean inactive = false;
    private boolean readOnly = false;
    private Long picklistId;
    private Auditable originalObject;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDefaultDisplayValue() {
        return defaultDisplayValue;
    }

    public void setDefaultDisplayValue(String defaultDisplayValue) {
        this.defaultDisplayValue = defaultDisplayValue;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer position) {
        this.itemOrder = position;
    }

    public Long getPicklistId() {
        return picklistId;
    }

    public void setPicklistId(Long picklistId) {
        this.picklistId = picklistId;
    }

    public void setOriginalObject(Auditable originalObject) {
        this.originalObject = originalObject;
    }

    public Auditable getOriginalObject() {
        return originalObject;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setSuppressReferenceValue(String suppressReferenceValue) {
        this.suppressReferenceValue = suppressReferenceValue;
    }

    public String getSuppressReferenceValue() {
        return suppressReferenceValue;
    }

    public String getValue() {
        return itemName;
    }

    public String getDisplayValue() {
        return defaultDisplayValue;
    }

    public String getValueDescription() {
        StringBuilder sb = new StringBuilder(defaultDisplayValue);
        if (StringUtils.hasText(longDescription)) {
            sb.append(" - ").append(longDescription);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.itemName + ":" + this.itemOrder;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    public String getAuditShortDesc() {
        return getItemName();
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

	public String resolveDisplayValue() {
		if (StringUtils.hasText(longDescription)) {
			return longDescription;
		}
		return defaultDisplayValue;
	}
}
