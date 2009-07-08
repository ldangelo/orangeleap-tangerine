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

package com.orangeleap.tangerine.dao.ibatis;

import com.orangeleap.tangerine.dao.FieldDao;
import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.logging.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.*;

/**
 * This class abstracts out the base methods used for working
 * with custom fields so that the logic can be reused by both the
 * CustomizableSqlMapClientTemplate and the IBatisCustomFieldDao.
 *
 * @version 1.0
 */
public class IBatisCustomFieldHelper {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    private SqlMapClientTemplate template;
    private FieldDao fieldDao;

    public IBatisCustomFieldHelper(SqlMapClientTemplate template, ApplicationContext applicationContext) {
        this.template = template;
        if (applicationContext != null) {
            fieldDao = (FieldDao) applicationContext.getBean("fieldDAO");
        }
    }

    /**
     * Read the custom fields for the specified entity type and ID. The combination
     * of these two values uniquely identify a custom field.
     *
     * @param entityId   the ID of the entity the custom field is associated with
     * @param entityType the type of the entity
     * @return the Map of CustomFields, keyed by name
     */
    @SuppressWarnings("unchecked")
    public Map<String, CustomField> readCustomFields(AbstractCustomizableEntity entity) {
        Map<String, CustomField> ret = AbstractCustomizableEntity.createCustomFieldMap(entity);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("entityId", entity.getId());
        params.put("entityType", entity.getType());
        params.put("asOfDate", new java.util.Date());

        List<Map> customFields = template.queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY_MAP", params);

        // group the custom fields by name. There could be multiple
        // rows for each combination of entity type, entity ID and field name,
        // which is the case when we normalize a comma-separated list of values
        Map<String, List<Map>> tempMap = new HashMap<String, List<Map>>();
        for (Map<String, Object> map : customFields) {
            String key = (String) map.get("name");
            if (tempMap.containsKey(key)) {
                tempMap.get(key).add(map);
            } else {
                List<Map> list = new ArrayList<Map>();
                list.add(map);
                tempMap.put(key, list);
            }
        }

        // Each key in the map, and its list, represents the contents of a single
        // custom field. We now flatten it out into a single entry
        for (String key : tempMap.keySet()) {

            List<Map> values = tempMap.get(key);

            CustomField customField = new CustomField();
            Map<String, Object> record = values.get(0);

            StringBuilder fieldValue = new StringBuilder();
            // use first item for the basics
            customField.setId((Long) record.get("id"));
            customField.setName(key);
            customField.setEntityId((Long) record.get("entityId"));
            customField.setEntityType((String) record.get("entityType"));
            fieldValue.append(record.get("value"));

            // now the fun; loop over values and append on to the string builder
            // to arrive at the final field value
            if (values.size() > 1) {
                // start at index 1, since we already grabbed zero above
                for (int i = 1; i < values.size(); i++) {
                    record = values.get(i);
                    fieldValue.append(StringConstants.CUSTOM_FIELD_SEPARATOR).append(record.get("value"));
                }
            }

            customField.setValue(fieldValue.toString());
            // put the final object into the map to be returned
            ret.put(key, customField);
        }

        return ret;
    }


    public void maintainCustomFields(AbstractCustomizableEntity entity) {

        Map<String, CustomField> customFields = entity.getCustomFieldMap();

        List<CustomField> newlist = new ArrayList<CustomField>();

        // Parses out comma separated fields
        for (String key : customFields.keySet()) {

            CustomField customField = customFields.get(key);

            String value = customField.getValue();

            // if value is null or zero-length on a new custom field,
            // don't even bother to save it.
            if (value == null || value.trim().equals(StringConstants.EMPTY)) {
                continue;
            }

            String[] values = value.split(StringConstants.CUSTOM_FIELD_SEPARATOR);
            Long firstValue = null;
            // run through each value and insert it with the correct sequence
            // number. If there is only one value (no separators), this still works just fine
            for (String splitValue : values) {

                String trimmedValue = splitValue.trim();
                if (!trimmedValue.equals(StringConstants.EMPTY)) {
                    CustomField cf = new CustomField();
                    cf.setEntityId(entity.getId());
                    cf.setEntityType(entity.getType());
                    cf.setName(customField.getName());
                    cf.setValue(trimmedValue);

                    newlist.add(cf);

                    // save the Id value from the element at Sequence 0, which we'll
                    // use for the final Id for the custom field
                    if (firstValue == null) {
                        firstValue = customField.getId();
                    }
                }
            }

            // if we actually saved a new custom field, update the ID
            // with the value from the row at sequence zero
            if (firstValue != null) {
                customField.setId(firstValue);
            }
        }

        saveNewCustomFieldList(entity, newlist);

    }

    @SuppressWarnings("unchecked")
    // Determine modifications to existing date-ranged fields which will reasonably correspond to new list for current date.
    private void saveNewCustomFieldList(AbstractCustomizableEntity entity, List<CustomField> currentNewCustomFields) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("entityId", entity.getId());
        params.put("entityType", entity.getType());

        List<CustomField> allOldCustomFields = template.queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY", params);

        params.put("asOfDate", new java.util.Date());

        List<CustomField> currentOldCustomFields = template.queryForList("SELECT_CUSTOM_FIELD_BY_ENTITY", params);

        List<CustomField> adds = subtract(currentNewCustomFields, currentOldCustomFields);
        List<CustomField> deletes = subtract(currentOldCustomFields, currentNewCustomFields);


        // Only need to maintain the adds and deletes. Keep the existing date ranges on the unchanged ones.
        for (CustomField cf : deletes) {
            deleteCustomField(cf, useDateRanges(cf));
        }

        for (CustomField cf : adds) {
            addCustomField(cf, allOldCustomFields, useDateRanges(cf));
        }

    }

    private boolean useDateRanges(CustomField cf) {
        return isConstituent(cf) && isReferenceType(getFieldDefinition(cf));
    }

    private boolean isConstituent(CustomField cf) {
        return cf.getEntityType().equals("constituent");
    }

    private FieldDefinition getFieldDefinition(CustomField cf) {
        if (fieldDao == null) {
            return new FieldDefinition();
        }
        return fieldDao.readFieldDefinition("constituent.customFieldMap[" + cf.getName() + "]");
    }

    private boolean isReferenceType(FieldDefinition fd) {
        return fd != null && (fd.getFieldType().equals(FieldType.QUERY_LOOKUP) || fd.getFieldType().equals(FieldType.MULTI_QUERY_LOOKUP));
    }


    // Return list of items in a that are not in b
    private List<CustomField> subtract(List<CustomField> a, List<CustomField> b) {
        List<CustomField> result = new ArrayList<CustomField>();
        for (CustomField acf : a) {
            boolean found = false;
            for (CustomField bcf : b) {
                if (bcf.getName().equals(acf.getName()) && bcf.getValue().equals(acf.getValue())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(acf);
            }
        }
        return result;
    }

    /**
     * Deletes all the custom fields associated with the underlying CustomizableAbstractEntity
     *
     * @param customFields
     */
    public void deleteCustomFields(AbstractCustomizableEntity entity) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("entityType", entity.getType());
        params.put("entityId", entity.getId());

        template.delete("DELETE_CUSTOM_FIELD", params);

    }

    private void deleteCustomField(CustomField customField, boolean useDateRanges) {
        Date endDate = addDay(stripTime(new java.util.Date()), -1);
        if (useDateRanges && customField.getStartDate().before(endDate)) {
            customField.setEndDate(endDate);
            template.update("UPDATE_CUSTOM_FIELD", customField);
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("entityId", customField.getEntityId());
            params.put("entityType", customField.getEntityType());
            params.put("id", customField.getId());
            template.delete("DELETE_CUSTOM_FIELD", params);
        }
    }

    private void addCustomField(CustomField customField, List<CustomField> existingFields, boolean useDateRanges) {
        if (useDateRanges) {
            // Need to fit in between existing date ranged values.
            customField.setStartDate(stripTime(new java.util.Date()));
            existingFields = filterByName(existingFields, customField);
            if (existingFields.size() > 0) {
                customField.setEndDate(addDay(getEarliestStartDateAfter(existingFields), -1));
            }
        }
        template.insert("INSERT_CUSTOM_FIELD", customField);
    }

    private Date getLatestEndDateBefore(List<CustomField> existingFields) {
        Date result = addDay(CustomField.PAST_DATE, -1);
        Date today = stripTime(new java.util.Date());
        for (CustomField cf : existingFields) {
            if (cf.getEndDate().after(result) && cf.getEndDate().before(today)) {
                result = cf.getEndDate();
            }
        }
        return result;
    }

    private Date getEarliestStartDateAfter(List<CustomField> existingFields) {
        Date result = addDay(CustomField.FUTURE_DATE, 1);
        Date today = stripTime(new java.util.Date());
        for (CustomField cf : existingFields) {
            if (cf.getStartDate().before(result) && cf.getStartDate().after(today)) {
                result = cf.getStartDate();
            }
        }
        return result;
    }

    private Date addDay(Date d, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, i);
        return cal.getTime();
    }

    private Date stripTime(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime();
    }

    private List<CustomField> filterByName(List<CustomField> list, CustomField matchingCf) {
        List<CustomField> result = new ArrayList<CustomField>();
        for (CustomField cf : list) {
            if (cf.getName().equals(matchingCf.getName())) {
                result.add(cf);
            }
        }
        return result;
    }


}
