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

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;


import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;
import org.springframework.util.StringUtils;

import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class PostBatchReviewSetItem implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long postBatchId;
    private Long entityId;
    private Date createDate;
    private Date updateDate;

    public PostBatchReviewSetItem() { }


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


    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
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


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PostBatchReviewSetItem)) {
            return false;
        }
        PostBatchReviewSetItem a = (PostBatchReviewSetItem) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb
        .append(id, a.getId())
        .append(postBatchId, a.getPostBatchId())
        .append(entityId, a.getEntityId())
		;
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb
        .append(""+id)
        .append(""+postBatchId)
        .append(""+entityId)
		;
        return hcb.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
        .append(super.toString())
        .append("id", ""+id)
        .append("postBatchId", ""+postBatchId)
        .append("entityId", ""+entityId)
        .toString();
    }

}