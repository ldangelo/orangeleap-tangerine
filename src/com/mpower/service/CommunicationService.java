package com.mpower.service;

import java.util.List;

import com.mpower.domain.model.communication.AbstractCommunicationEntity;

public interface CommunicationService<T extends AbstractCommunicationEntity> {

    public T save(T entity);

    public List<T> readByConstituentId(Long constituentId);

    public List<T> filterValid(Long constituentId);

    public T read(Long entityId);

    public List<T> readCurrent(Long constituentId, boolean receiveCorrespondence);

    public void inactivateEntities();
}