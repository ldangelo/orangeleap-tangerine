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

import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.Map;

public interface CommunicationService<T extends AbstractCommunicationEntity> {

    public T alreadyExists(T entity);

    public T save(T entity) throws BindException;

    public List<T> readByConstituentId(Long constituentId);

    public List<T> filterValid(Long constituentId);

    public List<T> filterValid(List<T> entities);

    public T readById(Long entityId);

    public T readByIdCreateIfNull(String entityId, Long constituentId);

    public void findReferenceDataByConstituentId(Map<String, Object> refData, Long constituentId, String entitiesKey, String activeEntitiesKey, String activeMailEntitiesKey);

    public T filterByPrimary(List<T> entities, Long constituentId);

    public T getPrimary(Long constituentId);

    public void inactivateEntities();

    public void maintainResetReceiveCorrespondence(Long constituentId);

    public void resetReceiveCorrespondence(T entity);

    boolean isCurrent(final T entity);

    int readCountByConstituentId(Long constituentId);
}