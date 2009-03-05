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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.model.communication.AbstractCommunicationEntity;
import com.mpower.domain.util.SeasonalDateSpan;
import com.mpower.type.ActivationType;

public abstract class AbstractCommunicationService<T extends AbstractCommunicationEntity> {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Method that can be overridden in unit tests
     * @return today's date
     */
    protected Date getNowDate() {
        return Calendar.getInstance().getTime();
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
                if (!nowDate.before(entity.getTemporaryStartDate()) && !nowDate.after(entity.getTemporaryEndDate())) {
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
