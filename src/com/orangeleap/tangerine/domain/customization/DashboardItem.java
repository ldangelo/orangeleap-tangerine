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

import java.io.Serializable;
import java.util.List;

import com.orangeleap.tangerine.domain.GeneratedId;

public class DashboardItem implements GeneratedId, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String type = "Guru";
    private String title;
    private String url;
    private Integer order;
    private String roles;
    private String siteName;
    private List<DashboardItemDataset> datasets;
    private List<String> availableRoles;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoles() {
        return roles;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setDatasets(List<DashboardItemDataset> datasets) {
        this.datasets = datasets;
    }

    public List<DashboardItemDataset> getDatasets() {
        return datasets;
    }

	public void setAvailableRoles(List<String> availableRoles) {
		this.availableRoles = availableRoles;
	}

	public List<String> getAvailableRoles() {
		return availableRoles;
	}


}
