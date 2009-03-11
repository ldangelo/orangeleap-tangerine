package com.orangeleap.tangerine.dao;

import java.util.List;

import com.orangeleap.tangerine.domain.communication.AbstractCommunicationEntity;

public interface CommunicationDao<T extends AbstractCommunicationEntity> {

    public T maintainEntity(T entity);

    public List<T> readByConstituentId(Long constituentId);

    public T readById(Long id);

    public List<T> readActiveByConstituentId(Long constituentId);

    public void inactivateEntities();

}
