package com.mpower.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.interfaces.CommunicationDao;
import com.mpower.domain.model.AbstractEntity;
import com.mpower.domain.model.communication.AbstractCommunicationEntity;
import com.mpower.domain.util.SeasonalDateSpan;
import com.mpower.service.AuditService;
import com.mpower.service.CloneService;
import com.mpower.service.CommunicationService;
import com.mpower.service.InactivateService;
import com.mpower.type.ActivationType;

public abstract class AbstractCommunicationService<T extends AbstractCommunicationEntity> extends AbstractTangerineService implements CommunicationService<T>, InactivateService, CloneService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    @Resource(name = "auditService")
    private AuditService auditService;

    protected abstract CommunicationDao<T> getDao();

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public T save(T entity) {
        if (logger.isDebugEnabled()) {
            logger.debug("save: entity = " + entity);
        }
        boolean found = false;
        if (entity.getId() == null) {
            List<T> entityList = readByConstituentId(entity.getPersonId());
            for (T a : entityList) {
                if (entity.equals(a)) {
                    found = true;
                    Long id = a.getId();
                    try {
                        BeanUtils.copyProperties(a, entity);
                        a.setId(id);
                    } 
                    catch (Exception e) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("Exception copying properties", e);
                        }
                    }
                    entity = a;
                }
            }
        }
        if (!found) {
            entity = getDao().maintainEntity(entity);
            if (entity.isInactive()) {
                auditService.auditObjectInactive(entity);
            } 
            else {
                auditService.auditObject(entity);
            }
        }
        return entity;
    }

    @Override
    public List<T> readByConstituentId(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("readByConstituentId: constituentId = " + constituentId);
        }
        return getDao().readByConstituentId(constituentId);
    }

    @Override
    public List<T> filterValid(Long constituentId) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterValid: constituentId = " + constituentId);
        }
        return filterValidEntities(readByConstituentId(constituentId));
    }

    @Override
    public T read(Long entityId) {
        if (logger.isDebugEnabled()) {
            logger.debug("read: entityId = " + entityId);
        }
        return getDao().readById(entityId);
    }

    @Override
    public List<T> readCurrent(Long constituentId, boolean mailOnly) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCurrent: constituentId = " + constituentId + " mailOnly = " + mailOnly);
        }
        return filterByActivationType(getDao().readActiveByConstituentId(constituentId), mailOnly);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void inactivateEntities() {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate:");
        }
        getDao().inactivateEntities();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void inactivate(Long id) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivate: id = " + id);
        }
        T entity = read(id);
        entity.setInactive(true);
        this.save(entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AbstractEntity clone(AbstractEntity oldEntity) {
        if (logger.isDebugEnabled()) {
            logger.debug("clone: oldEntity = " + oldEntity);
        }
        T entity = (T) oldEntity;
        if (entity.getId() != null) {
            T original = this.read(entity.getId());

            try {
                entity = (T)BeanUtils.cloneBean(original);
                entity.resetIdToNull();
            }
            catch (Exception e) {
                if (logger.isErrorEnabled()) {
                    logger.error("clone: Exception occurred", e);
                }
            }
        }
        return entity;
    }
   
    /**
     * Method that can be overridden in unit tests
     * @return today's date
     */
    protected Date getNowDate() {
        return Calendar.getInstance().getTime();
    }
    
    protected List<T> filterValidEntities(final List<T> entities) {
        List<T> filteredEntities = new ArrayList<T>();
        for (T entity : entities) {
            if (entity.isValid()) {
                filteredEntities.add(entity);
            }
        }
        return filteredEntities;
    }
    
    protected List<T> filterByActivationType(final List<T> entities, final boolean mailOnly) {
        if (logger.isDebugEnabled()) {
            logger.debug("filterByActivationType: entities = " + entities + " mailOnly = " + mailOnly);
        }
        // create an ordered map of "status" -> (ordered map of "activationType" -> entity)
        Map<ActivationType, LinkedHashMap<String, List<T>>> statusMap = new LinkedHashMap<ActivationType, LinkedHashMap<String, List<T>>>();
        Set<String> communicationTypeSet = new LinkedHashSet<String>(); // store types of entities
        Date nowDate = getNowDate();

        for (T entity : entities) {
            if (ActivationType.temporary.equals(entity.getActivationStatus())) {
                if (entity.getTemporaryStartDate() != null && entity.getTemporaryEndDate() != null && 
                    !nowDate.before(entity.getTemporaryStartDate()) && !nowDate.after(entity.getTemporaryEndDate())) {
                    addToMap(statusMap, communicationTypeSet, ActivationType.temporary, entity);
                }
            } 
            else if (ActivationType.seasonal.equals(entity.getActivationStatus())) {
                SeasonalDateSpan dateSpan = new SeasonalDateSpan(entity.getSeasonalStartDate(), entity.getSeasonalEndDate());
                if (dateSpan.contains(nowDate)) {
                    addToMap(statusMap, communicationTypeSet, ActivationType.seasonal, entity);
                }
            } 
            else if (ActivationType.permanent.equals(entity.getActivationStatus())) {
                if (entity.getEffectiveDate() == null || !entity.getEffectiveDate().after(nowDate)) {
                    addToMap(statusMap, communicationTypeSet, ActivationType.permanent, entity);
                }
            }
        }
        return createAppendedList(statusMap, communicationTypeSet, mailOnly);
    }
    
    private List<T> createAppendedList(Map<ActivationType, LinkedHashMap<String, List<T>>> statusMap, Set<String> communicationTypeSet, boolean mailOnly) {
        List<T> newList = new ArrayList<T>();
        for (String communicationType : communicationTypeSet) {
            LinkedHashMap<String, List<T>> tempMap = statusMap.get(ActivationType.temporary);
            LinkedHashMap<String, List<T>> seasonalMap = statusMap.get(ActivationType.seasonal);
            LinkedHashMap<String, List<T>> permanentMap = statusMap.get(ActivationType.permanent);
            
            if (tempMap != null && tempMap.get(communicationType) != null) {
                newList.addAll(tempMap.get(communicationType));
            } 
            else if (seasonalMap != null && seasonalMap.get(communicationType) != null) {
                newList.addAll(seasonalMap.get(communicationType));
            } 
            else if (permanentMap != null && permanentMap.get(communicationType) != null) {
                newList.addAll(permanentMap.get(communicationType));
            }
        }
        // If want only those accepting mail, then filter out no mail addresses
        for (Iterator<T> iterator = newList.iterator(); iterator.hasNext();) {
            T entity = iterator.next();
            if (mailOnly && !entity.isReceiveMail()) {
                iterator.remove();
            }
        }
        return newList;
    }

    protected void addToMap(Map<ActivationType, LinkedHashMap<String, List<T>>> statusMap, Set<String> communicationTypeSet, ActivationType actType, T entity) {
        LinkedHashMap<String, List<T>> communicationTypeMap = statusMap.get(actType);
        if (communicationTypeMap == null) {
            communicationTypeMap = new LinkedHashMap<String, List<T>>();
        }
        List<T> aList = communicationTypeMap.get(entity.getCommunicationType());
        if (aList == null) {
            aList = new ArrayList<T>();
        }
        aList.add(entity);
        communicationTypeSet.add(entity.getCommunicationType());
        communicationTypeMap.put(entity.getCommunicationType(), aList);
        statusMap.put(actType, communicationTypeMap);
    }
}
