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

import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class PostBatch extends AbstractCustomizableEntity  {

    private static final long serialVersionUID = 1L;

    public PostBatch() { }

    public PostBatch(String batchDesc, String batchType) {
        this();
        this.batchDesc = batchDesc;
        this.batchType = batchType;
    }

    private String batchDesc;
    /** batchType maps to 'entity' */
    private String batchType;
    private Long batchCreatedById;
    private Date batchCreatedDate;
    private boolean executed;
    private Long executedById;
    private String executedByUser;
    private Date executedDate;
    private boolean posted;
    private Long postedById;
    private Date postedDate;
	private boolean forTouchPoints;
	private boolean anErrorBatch;
	/** A pointer to a batch that contains the errored rows that used to be in this batch, if any */
    private Long errorBatchId;
	private String siteName;
	private boolean currentlyExecuting;
	private Date createDate;
    private Date updateDate;

    private List<PostBatchEntry> postBatchEntries = new ArrayList<PostBatchEntry>();
    private Map<String, String> updateFields = new TreeMap<String, String>();
    private List<String> updateErrors = new ArrayList<String>();

    public String getBatchDesc() {
        return batchDesc;
    }

    public void setBatchDesc(String batchDesc) {
        this.batchDesc = batchDesc;
    }

    public String getBatchType() {
        return batchType;
    }

    public void setBatchType(String batchType) {
        this.batchType = batchType;
    }

    public Long getBatchCreatedById() {
        return batchCreatedById;
    }

    public void setBatchCreatedById(Long batchCreatedById) {
        this.batchCreatedById = batchCreatedById;
    }

    public Date getBatchCreatedDate() {
        return batchCreatedDate;
    }

    public void setBatchCreatedDate(Date batchCreatedDate) {
        this.batchCreatedDate = batchCreatedDate;
    }

    public void setExecutionFields(Long executedById) {
        setExecuted(true);
        setExecutedDate(new Date());
        setExecutedById(executedById);
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public Long getExecutedById() {
        return executedById;
    }

    public void setExecutedById(Long executedById) {
        this.executedById = executedById;
    }

    public String getExecutedByUser() {
        return executedByUser;
    }

    public void setExecutedByUser(String executedByUser) {
        this.executedByUser = executedByUser;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public void setPostedFields(Long postedById) {
        setPosted(true);
        setPostedDate(new Date());
        setPostedById(postedById);
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public Long getPostedById() {
        return postedById;
    }

    public void setPostedById(Long postedById) {
        this.postedById = postedById;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

	public boolean isForTouchPoints() {
		return forTouchPoints;
	}

	public void setForTouchPoints(boolean forTouchPoints) {
		this.forTouchPoints = forTouchPoints;
	}

	public boolean isAnErrorBatch() {
        return anErrorBatch;
    }

    public void setAnErrorBatch(boolean anErrorBatch) {
        this.anErrorBatch = anErrorBatch;
    }

    public Long getErrorBatchId() {
        return errorBatchId;
    }

    public void setErrorBatchId(Long errorBatchId) {
        this.errorBatchId = errorBatchId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public boolean isCurrentlyExecuting() {
        return currentlyExecuting;
    }

    public void setCurrentlyExecuting(boolean currentlyExecuting) {
        this.currentlyExecuting = currentlyExecuting;
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

    public List<PostBatchEntry> getPostBatchEntries() {
        return postBatchEntries;
    }

    public void setPostBatchEntries(List<PostBatchEntry> postBatchEntries) {
        this.postBatchEntries = postBatchEntries;
    }

    public void clearPostBatchEntries() {
        if (postBatchEntries != null) {
            postBatchEntries.clear();
        }
    }

    public void addPostBatchEntry(PostBatchEntry postBatchEntry) {
        if (postBatchEntries == null) {
            postBatchEntries = new ArrayList<PostBatchEntry>();
        }
        postBatchEntries.add(postBatchEntry);
    }

    @SuppressWarnings("unchecked")
    public void clearAddAllPostBatchEntriesForSegmentations(Set<Long> pickedSegmentationIds) {
        clearPostBatchEntries();
        if (pickedSegmentationIds != null) {
            for (Long thisSegmentationId : pickedSegmentationIds) {
                addPostBatchEntry(new PostBatchEntry(thisSegmentationId));
            }
        }
    }

    public Set<Long> getEntrySegmentationIds() {
        return getEntryByIdNames("segmentationId");
    }

    public Set<Long> getEntryGiftIds() {
        return getEntryByIdNames(StringConstants.GIFT_ID);
    }

    public Set<Long> getEntryAdjustedGiftIds() {
        return getEntryByIdNames(StringConstants.ADJUSTED_GIFT_ID);
    }

    public Set<Long> getEntryPledgeIds() {
        return getEntryByIdNames(StringConstants.PLEDGE_ID);
    }

	public Set<Long> getEntryConstituentIds() {
	    return getEntryByIdNames(StringConstants.CONSTITUENT_ID);
	}

    private Set<Long> getEntryByIdNames(String idName) {
        final Set<Long> ids = new TreeSet<Long>();
        if (postBatchEntries != null) {
            for (PostBatchEntry thisSeg : postBatchEntries) {
                BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(thisSeg);
                if (bw.isReadableProperty(idName)) {
                    Object id = bw.getPropertyValue(idName);
                    if (id != null) {
                        ids.add((Long) id);
                    }
                }
            }
        }
        return ids;
    }

    public Map<String, String> getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(Map<String, String> updateFields) {
        this.updateFields = updateFields;
    }

    public void clearUpdateFields() {
        if (updateFields != null) {
            updateFields.clear();
        }
    }

    public void addUpdateField(String key, String value) {
        if (updateFields == null) {
            updateFields = new TreeMap<String, String>();
        }
        updateFields.put(key, value);
    }

    public void removeUpdateField(String key) {
        if (updateFields != null) {
            updateFields.remove(key);
        }
    }

    public String getUpdateFieldValue(String key) {
        return this.updateFields.get(key);
    }

    public List<String> getUpdateErrors() {
        return updateErrors;
    }

    public void setUpdateErrors(List<String> updateErrors) {
        this.updateErrors = updateErrors;                                                                                          
    }

    public void clearUpdateErrors() {
        if (updateErrors != null) {
            updateErrors.clear();
        }
    }

    public void addUpdateError(String error) {
        if (updateErrors == null) {
            updateErrors = new ArrayList<String>();
        }
        updateErrors.add(error);
    }
}
