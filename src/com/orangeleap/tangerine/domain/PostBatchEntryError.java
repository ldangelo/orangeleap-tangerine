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

import java.io.Serializable;

public class PostBatchEntryError implements GeneratedId, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long postBatchEntryId;
    private String errorMsg;

    public PostBatchEntryError() { }

    public PostBatchEntryError(Long postBatchEntryId, String errorMsg) {
        this.postBatchEntryId = postBatchEntryId;
        this.errorMsg = errorMsg;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostBatchEntryId() {
        return postBatchEntryId;
    }

    public void setPostBatchEntryId(Long postBatchEntryId) {
        this.postBatchEntryId = postBatchEntryId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
