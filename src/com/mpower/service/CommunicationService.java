package com.mpower.service;

import java.util.List;
import java.util.Map;

import com.mpower.domain.model.communication.AbstractCommunicationEntity;

public interface CommunicationService<T extends AbstractCommunicationEntity> {

    public T save(T entity);

    public List<T> readByConstituentId(Long constituentId);

    public List<T> filterValid(Long constituentId);

    public T readById(Long entityId);
    
    public T readByIdCreateIfNull(String entityId, Long constituentId);

    public void findReferenceDataByConstituentId(Map<String, Object> refData, Long constituentId, String entitiesKey, String activeEntitiesKey, String activeMailEntitiesKey);

    public void inactivateEntities();
}