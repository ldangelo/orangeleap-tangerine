package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.communication.AbstractCommunicationEntity;

public interface CommunicationDao<T extends AbstractCommunicationEntity> {

    public T maintainEntity(T entity);

    public List<T> readByConstituentId(Long constituentId);

    public T readById(Long id);

    public List<T> readActiveByConstituentId(Long constituentId);

    public void inactivateEntities();

}
