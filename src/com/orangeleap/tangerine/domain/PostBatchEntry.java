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

import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * A mapping of the segmentation/gift/pledge IDs associated with a batch
 */
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class PostBatchEntry implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long postBatchId;
    private Long segmentationId;
    private Long constituentId;
    private Long giftId;
    private Long adjustedGiftId;
    private Long pledgeId;
    private Date createDate;
    private Date updateDate;
    private List<String> errors = new ArrayList<String>();

    public PostBatchEntry() { }

    public PostBatchEntry(Long segmentationId) {
        this.segmentationId = segmentationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostBatchId() {
        return postBatchId;
    }

    public void setPostBatchId(Long postBatchId) {
        this.postBatchId = postBatchId;
    }

    public Long getSegmentationId() {
        return segmentationId;
    }

    public void setSegmentationId(Long segmentationId) {
        this.segmentationId = segmentationId;
    }

    public Long getConstituentId() {
        return constituentId;
    }

    public void setConstituentId(Long constituentId) {
        this.constituentId = constituentId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getAdjustedGiftId() {
        return adjustedGiftId;
    }

    public void setAdjustedGiftId(Long adjustedGiftId) {
        this.adjustedGiftId = adjustedGiftId;
    }

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void clearErrors() {
        if (errors != null) {
            errors.clear();
        }
    }

    public void addError(String error) {
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(error);
    }
}