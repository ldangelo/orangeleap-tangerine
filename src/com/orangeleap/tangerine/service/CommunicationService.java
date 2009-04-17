package com.orangeleap.tangerine.service;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;

public interface CommunicationService<T extends AbstractCommunicationEntity> {

    public T alreadyExists(T entity);
    
    public T save(T entity);
    
    public T saveOnlyIfNew(T entity);

    public List<T> readByConstituentId(Long constituentId);

    public List<T> filterValid(Long constituentId);
    
    public List<T> filterValid(List<T> entities);

    public T readById(Long entityId);
    
    public T readByIdCreateIfNull(String entityId, Long constituentId);

    public void findReferenceDataByConstituentId(Map<String, Object> refData, Long constituentId, String entitiesKey, String activeEntitiesKey, String activeMailEntitiesKey);

    public T filterByPrimary(List<T> entities, Long constituentId);

    public T getPrimary(Long constituentId);

    public void inactivateEntities();
}