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

package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.Audit;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.web.common.SortInfo;
import com.orangeleap.tangerine.web.common.PaginatedResult;

public interface AuditService {
    public List<Audit> auditObject(Object object);

    public List<Audit> auditObject(Object object, Constituent constituent);
    
    public List<Audit> auditObject(Object object, Long userId);

    public List<Audit> allAuditHistoryForSite();

    public PaginatedResult allAuditHistoryForSite(SortInfo sortInfo);

    public List<Audit> auditHistoryForEntity(String entityTypeDisplay, Long objectId);

    public PaginatedResult auditHistoryForEntity(String entityTypeDisplay, Long objectId, SortInfo sortInfo);

    public List<Audit> auditHistoryForConstituent(Long constituentId);

    public PaginatedResult auditHistoryForConstituent(Long constituentId, SortInfo sortInfo);

    public Audit auditObjectInactive(Object object);

    public Audit auditObjectInactive(Object object, Constituent constituent);

    public Audit auditObjectInactive(Object object, Long userId);
}
