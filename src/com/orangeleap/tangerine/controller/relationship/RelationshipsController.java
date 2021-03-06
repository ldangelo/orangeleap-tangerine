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

package com.orangeleap.tangerine.controller.relationship;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.customization.*;
import com.orangeleap.tangerine.service.ConstituentCustomFieldRelationshipService;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.CustomFieldRelationshipService;
import com.orangeleap.tangerine.service.RelationshipService;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineMessageAccessor;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;

public class RelationshipsController extends SimpleFormController {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Resource(name = "relationshipService")
    private RelationshipService relationshipService;

    @Resource(name = "customFieldRelationshipService")
    private CustomFieldRelationshipService customFieldRelationshipService;

    @Resource(name = "constituentCustomFieldRelationshipService")
    private ConstituentCustomFieldRelationshipService constituentCustomFieldRelationshipService;

    @SuppressWarnings("unchecked")
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = relationshipService.readRelationshipFieldDefinitions(request.getParameter(StringConstants.CONSTITUENT_ID));
        Constituent constituent = (Constituent) returnMap.get(StringConstants.CONSTITUENT);
        List<FieldDefinition> fields = (List<FieldDefinition>) returnMap.get(StringConstants.FIELDS);
        List<String> attrs = Arrays.asList(constituent.getConstituentAttributes().split(StringConstants.CUSTOM_FIELD_SEPARATOR));

        List<FieldRelationshipForm> relationships = new ArrayList<FieldRelationshipForm>();
        for (FieldDefinition thisField : fields) {
        	if ( ! relationshipApplies(attrs, thisField)) {
		        continue;
	        }
        	
            FieldRelationshipForm fieldRelationshipForm = new FieldRelationshipForm();
            relationships.add(fieldRelationshipForm);
            fieldRelationshipForm.setFieldLabel(thisField.getDefaultLabel());
            fieldRelationshipForm.setFieldName(thisField.getCustomFieldName());
            fieldRelationshipForm.setFieldDefinitionId(thisField.getId());
            fieldRelationshipForm.setRelationshipType(relationshipService.isIndividualOrganizationRelationship(thisField.getId()));
            String masterFieldDefinitionId = customFieldRelationshipService.getMasterFieldDefinitionId(thisField.getId());
            fieldRelationshipForm.setMasterFieldDefinitionId(masterFieldDefinitionId);

            if (masterFieldDefinitionId != null) {
                CustomFieldRelationship customFieldRelationship = customFieldRelationshipService.readByFieldDefinitionId(masterFieldDefinitionId);
                if (customFieldRelationship != null && ! customFieldRelationship.getCustomFieldMap().isEmpty()) {
                    fieldRelationshipForm.setHasRelationshipCustomizations(true);

                    Iterator<CustomField> it = customFieldRelationship.getCustomFieldMap().values().iterator();
                    Map<String, Object> defaultRelationshipCustomizations = new TreeMap<String, Object>();
                    while (it.hasNext()) {
                        CustomField cf = it.next();
                        String name = cf.getName();
                        String defaultValue = cf.getValue();
                        if (StringConstants.BLANK_CUSTOM_FIELD_VALUE.equalsIgnoreCase(defaultValue)) {
                            defaultValue = StringConstants.EMPTY;
                        }
                        defaultRelationshipCustomizations.put(name, defaultValue);
                    }
                    fieldRelationshipForm.setDefaultRelationshipCustomizations(defaultRelationshipCustomizations);
                }
                else {
                    fieldRelationshipForm.setHasRelationshipCustomizations(false);
                }
            }

            getCustomFieldRelationshipForField(thisField, constituent, fieldRelationshipForm, masterFieldDefinitionId);
        }
        request.setAttribute(StringConstants.CONSTITUENT, constituent);

        return relationships;
    }
    
    private boolean relationshipApplies(List<String> constituentattrs, FieldDefinition fieldDefinition) {
    	if (fieldDefinition.getEntityAttributes() == null) return true;
        List<String> fieldattrs = Arrays.asList(fieldDefinition.getEntityAttributes().split(","));
        for (String fieldattr: fieldattrs) {
        	if (constituentattrs.contains(fieldattr)) return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Long constituentId = Long.valueOf(request.getParameter(StringConstants.CONSTITUENT_ID));
        List<FieldRelationshipForm> relationships = (List<FieldRelationshipForm>) command;

        Map<String, String> validationErrors = new LinkedHashMap<String, String>();

        Map<String, List<RelationshipCustomField>> customFieldMap = getCustomFieldParameters(request, validationErrors);

        for (FieldRelationshipForm fieldRelationshipForm : relationships) {
            String fieldName = fieldRelationshipForm.getFieldName();

            List<RelationshipCustomField> newRelationshipCustomFields = customFieldMap.get(fieldName);
            findRelationshipCustomizations(fieldRelationshipForm, request, newRelationshipCustomFields);

            List<CustomField> existingCustomFields = relationshipService.readCustomFieldsByConstituentAndFieldName(constituentId, fieldName);

            Map<String, String> fieldValidationErrors = relationshipService.validateConstituentRelationshipCustomFields(constituentId, newRelationshipCustomFields, fieldRelationshipForm.getFieldDefinitionId());
            if (fieldValidationErrors != null && ! fieldValidationErrors.isEmpty()) {
                validationErrors.putAll(fieldValidationErrors);
            }
            else {
                relationshipService.maintainRelationshipCustomFields(constituentId, fieldRelationshipForm.getFieldDefinitionId(), existingCustomFields, newRelationshipCustomFields, fieldRelationshipForm.getMasterFieldDefinitionId());
            }
        }
        if (validationErrors.isEmpty()) {
            return new ModelAndView(getSuccessView() + "?" + StringConstants.CONSTITUENT_ID + "=" + constituentId + "&" + StringConstants.SAVED_EQUALS_TRUE);
        }
        else {
            validationErrors = repopulateRelationshipsFromRequest(customFieldMap, relationships, validationErrors);
            ModelAndView mav = showForm(request, response, errors);
            mav.addObject("validationErrors", validationErrors);
            mav.addObject("relationships", relationships);
            mav.addObject(StringConstants.CONSTITUENT, constituentService.readConstituentById(constituentId));
            mav.addObject(StringConstants.CONSTITUENT_ID, constituentId);
            return mav;
        }
    }

    /**
     * For a POST request, find all request parameters
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, List<RelationshipCustomField>> getCustomFieldParameters(HttpServletRequest request, Map<String, String> validationErrors) {
        Long constituentId = Long.valueOf(request.getParameter(StringConstants.CONSTITUENT_ID));
        Enumeration e = request.getParameterNames();

        Map<String, List<RelationshipCustomField>> relationshipCustomFieldMap = new HashMap<String, List<RelationshipCustomField>>();
        while (e.hasMoreElements()) {
            String fieldName = (String) e.nextElement();
            if (fieldName.startsWith(StringConstants.FLD_VAL)) {
                String customFieldValue = request.getParameter(fieldName);

                if (StringUtils.hasText(customFieldValue)) {
                    String customFieldName = fieldName.replaceFirst(StringConstants.FLD_VAL + "\\d+-", StringConstants.EMPTY);
                    CustomField newCustomField = new CustomField();

                    String startDate = request.getParameter(fieldName.replaceFirst(StringConstants.FLD_VAL, StringConstants.START_DT));
                    try {
                        if (StringUtils.hasText(startDate)) {
                            newCustomField.setDisplayStartDate(startDate);
                        }
                    }
                    catch (Exception stEx) {
                        // ignore, set to default date
                    }
                    String endDate = request.getParameter(fieldName.replaceFirst(StringConstants.FLD_VAL, StringConstants.END_DT));
                    try {
                        if (StringUtils.hasText(endDate)) {
                            newCustomField.setDisplayEndDate(endDate);
                        }
                    }
                    catch (Exception enEx) {
                        // ignore, set to default date
                    }

                    newCustomField.setName(customFieldName);
                    newCustomField.setValue(customFieldValue);
                    newCustomField.setEntityId(constituentId);
                    newCustomField.setEntityType(StringConstants.CONSTITUENT);

                    String customFieldId = request.getParameter(fieldName.replaceFirst(StringConstants.FLD_VAL, StringConstants.CUSTOM_FLD_ID));
                    if (NumberUtils.isDigits(customFieldId)) {
                        newCustomField.setId(Long.parseLong(customFieldId));
                    }

                    RelationshipCustomField relationshipCustomField = new RelationshipCustomField();
                    relationshipCustomField.setCustomField(newCustomField);
                    int index = getIndex(fieldName);
                    relationshipCustomField.setIndex(index);

                    List<RelationshipCustomField> relationshipCustomFieldList = relationshipCustomFieldMap.get(customFieldName);
                    if (relationshipCustomFieldList == null) {
                        relationshipCustomFieldList = new ArrayList<RelationshipCustomField>();
                        relationshipCustomFieldMap.put(customFieldName, relationshipCustomFieldList);
                    }

                    relationshipCustomFieldList.add(relationshipCustomField);
                }
            }
        }
        return relationshipCustomFieldMap;
    }

    private int getIndex(String fieldName) {
        Matcher matcher = java.util.regex.Pattern.compile(StringConstants.FLD_VAL + "(\\d+)-.+").matcher(fieldName);

        int start = 0;
        String s = null;
        if (matcher != null) {
            while (matcher.find(start)) {
                s = matcher.group(1);
                start = matcher.end();
            }
        }
        return Integer.parseInt(s);
    }

    private void findRelationshipCustomizations(FieldRelationshipForm fieldRelationshipForm, HttpServletRequest request, List<RelationshipCustomField> newRelationshipCustomFields) {
        if (fieldRelationshipForm.getDefaultRelationshipCustomizations() != null && fieldRelationshipForm.getDefaultRelationshipCustomizations().isEmpty() == false) {
            if (newRelationshipCustomFields != null) {
                for (RelationshipCustomField thisRelationshipCustomField : newRelationshipCustomFields) {
                    int index = thisRelationshipCustomField.getIndex();

                    for (String customizableFieldName : fieldRelationshipForm.getDefaultRelationshipCustomizations().keySet()) {
                        String customizedRelationshipName = new StringBuilder(fieldRelationshipForm.getFieldName()).append("-").append(index).append("-").append(customizableFieldName).toString();
                        String customizedRelationshipValue = request.getParameter(customizedRelationshipName);

                        thisRelationshipCustomField.addRelationshipCustomization(customizableFieldName, customizedRelationshipValue);
                    }
                }
            }
        }
    }

    /**
     * Get the custom fields for a defined relationship for a GET request
     *
     * @param thisField
     * @param constituent
     * @param fieldRelationshipForm
     * @param masterFieldDefinitionId
     */
    private void getCustomFieldRelationshipForField(FieldDefinition thisField, Constituent constituent, FieldRelationshipForm fieldRelationshipForm, String masterFieldDefinitionId) {
        List<CustomField> customFields = relationshipService.findCustomFieldsForRelationship(constituent, thisField);

        if (customFields != null) {
            List<CustomFieldRelationshipForm> customFieldRelationshipList = new ArrayList<CustomFieldRelationshipForm>();

            for (CustomField thisCustomField : customFields) {
                CustomFieldRelationshipForm customFieldRelationshipForm = new CustomFieldRelationshipForm();
                customFieldRelationshipForm.setFieldDefinitionId(thisField.getId());
                customFieldRelationshipForm.setFieldName(thisField.getCustomFieldName());
                customFieldRelationshipForm.setCustomFieldId(thisCustomField.getId());
                customFieldRelationshipForm.setFieldValue(thisCustomField.getValue());
                customFieldRelationshipForm.setStartDate(thisCustomField.getStartDate());
                customFieldRelationshipForm.setEndDate(thisCustomField.getEndDate());
                customFieldRelationshipForm.setDisplayStartDate(thisCustomField.getDisplayStartDate());
                customFieldRelationshipForm.setDisplayEndDate(thisCustomField.getDisplayEndDate());
                customFieldRelationshipForm.setConstituentName(relationshipService.resolveConstituentRelationship(thisCustomField));

                if (fieldRelationshipForm.isHasRelationshipCustomizations() && masterFieldDefinitionId != null) {
                    ConstituentCustomFieldRelationship constituentCustomFieldRelationship = constituentCustomFieldRelationshipService.findConstituentCustomFieldRelationships(constituent.getId(),
                            masterFieldDefinitionId, thisCustomField.getValue(), thisCustomField.getStartDate(), fieldRelationshipForm.getDefaultRelationshipCustomizations());
                    if (constituentCustomFieldRelationship == null || constituentCustomFieldRelationship.getCustomFieldMap() == null || constituentCustomFieldRelationship.getCustomFieldMap().isEmpty()) {
                        customFieldRelationshipForm.setRelationshipCustomizations(fieldRelationshipForm.getDefaultRelationshipCustomizations());
                    } else {
                        customFieldRelationshipForm.setRelationshipCustomizationsFromCustomFields(fieldRelationshipForm.getDefaultRelationshipCustomizations(), constituentCustomFieldRelationship.getCustomFieldMap());
                    }
                }

                customFieldRelationshipList.add(customFieldRelationshipForm);
            }
            sortByStartDate(customFieldRelationshipList);

            /* Add a dummy customFieldRelationshipForm */
            addDummyCustomFieldRelationshipForm(customFieldRelationshipList, fieldRelationshipForm);

            fieldRelationshipForm.setCustomFields(customFieldRelationshipList);
        }
    }

    private void addDummyCustomFieldRelationshipForm(List<CustomFieldRelationshipForm> customFieldRelationshipList, FieldRelationshipForm fieldRelationshipForm) {
        CustomFieldRelationshipForm dummyCustomFieldRelationshipForm = new CustomFieldRelationshipForm();
        dummyCustomFieldRelationshipForm.setFieldName(fieldRelationshipForm.getFieldName());
        dummyCustomFieldRelationshipForm.setRelationshipCustomizations(fieldRelationshipForm.getDefaultRelationshipCustomizations());
        customFieldRelationshipList.add(dummyCustomFieldRelationshipForm);
    }

    /**
     * If a validation error occurs, replace the relationships form values with the ones from the request parameters.
     * Also replace validation error message keys with the actual validation error messages.
     *
     * @param relationshipCustomFieldMapParameters
     *                            from request parameters for a POST
     * @param relationships       the form for a GET
     * @param validationErrorKeys
     */
    private Map<String, String> repopulateRelationshipsFromRequest(Map<String, List<RelationshipCustomField>> relationshipCustomFieldMapParameters, List<FieldRelationshipForm> relationships,
                                                                   Map<String, String> validationErrorKeys) {
        Map<String, String> validationErrorMessages = new HashMap<String, String>();
        for (FieldRelationshipForm fieldRelationshipForm : relationships) {
            String fieldName = fieldRelationshipForm.getFieldName();

            List<RelationshipCustomField> newRelationshipCustomFields = relationshipCustomFieldMapParameters.get(fieldName);
            List<CustomFieldRelationshipForm> customFieldRelationshipList = new ArrayList<CustomFieldRelationshipForm>();
            if (newRelationshipCustomFields != null) {
                for (RelationshipCustomField newRelationshipCtmFld : newRelationshipCustomFields) {
                    CustomFieldRelationshipForm customFieldRelationshipForm = new CustomFieldRelationshipForm();
                    customFieldRelationshipForm.setFieldDefinitionId(fieldRelationshipForm.getFieldDefinitionId());
                    customFieldRelationshipForm.setFieldName(newRelationshipCtmFld.getCustomField().getName());
                    customFieldRelationshipForm.setCustomFieldId(newRelationshipCtmFld.getCustomField().getId());
                    customFieldRelationshipForm.setFieldValue(newRelationshipCtmFld.getCustomField().getValue());
                    customFieldRelationshipForm.setStartDate(newRelationshipCtmFld.getCustomField().getStartDate());
                    customFieldRelationshipForm.setEndDate(newRelationshipCtmFld.getCustomField().getEndDate());
                    customFieldRelationshipForm.setDisplayStartDate(newRelationshipCtmFld.getCustomField().getDisplayStartDate());
                    customFieldRelationshipForm.setDisplayEndDate(newRelationshipCtmFld.getCustomField().getDisplayEndDate());
                    String constituentName = relationshipService.resolveConstituentRelationship(newRelationshipCtmFld.getCustomField());
                    customFieldRelationshipForm.setConstituentName(constituentName);
                    customFieldRelationshipForm.setRelationshipCustomizations(newRelationshipCtmFld.getRelationshipCustomizations());

                    customFieldRelationshipList.add(customFieldRelationshipForm);

                    String custFldNameVal = new StringBuilder(newRelationshipCtmFld.getCustomField().getName()).append("-").append(newRelationshipCtmFld.getCustomField().getValue()).toString();
                    String messageKey = validationErrorKeys.get(custFldNameVal);
                    if ("errorSelfReferenceRelationship".equals(messageKey)) {
                        validationErrorMessages.put(custFldNameVal, TangerineMessageAccessor.getMessage(messageKey, new String[]{fieldRelationshipForm.getFieldLabel(),
                                constituentName, constituentName}));
                    }

                    custFldNameVal = new StringBuilder(newRelationshipCtmFld.getCustomField().getName()).append("-").append(newRelationshipCtmFld.getCustomField().getStartDate()).toString();
                    messageKey = validationErrorKeys.get(custFldNameVal);
                    if ("errorDateRangesSingleValueRelationship".equals(messageKey) || "errorDateRangesCorrespondingRelationship".equals(messageKey)) {
                        validationErrorMessages.put(custFldNameVal, TangerineMessageAccessor.getMessage(messageKey, new String[]{fieldRelationshipForm.getFieldLabel(),
                                constituentName}));
                    }

                    custFldNameVal = new StringBuilder(newRelationshipCtmFld.getCustomField().getName()).append("-").append(newRelationshipCtmFld.getCustomField().getEndDate()).toString();
                    messageKey = validationErrorKeys.get(custFldNameVal);
                    if ("errorDateRangesSingleValueRelationship".equals(messageKey) || "errorDateRangesCorrespondingRelationship".equals(messageKey)) {
                        validationErrorMessages.put(custFldNameVal, TangerineMessageAccessor.getMessage(messageKey, new String[]{fieldRelationshipForm.getFieldLabel(),
                                constituentName}));
                    }
                }
            }
            sortByStartDate(customFieldRelationshipList);

            addDummyCustomFieldRelationshipForm(customFieldRelationshipList, fieldRelationshipForm);
            fieldRelationshipForm.setCustomFields(customFieldRelationshipList);
        }
        return validationErrorMessages;
    }

    private void sortByStartDate(List<CustomFieldRelationshipForm> list) {
        Collections.sort(list, new Comparator<CustomFieldRelationshipForm>() {
            @Override
            public int compare(CustomFieldRelationshipForm o1, CustomFieldRelationshipForm o2) {
                int result;
                if (o1.getStartDate() == null && o2.getStartDate() != null) {
                    result = 1;
                } else if (o1.getStartDate() == null && o2.getStartDate() == null) {
                    result = 0;
                } else if (o2.getStartDate() == null && o1.getStartDate() != null) {
                    result = -1;
                } else {
                    result = o1.getStartDate().compareTo(o2.getStartDate());
                    if (result == 0) {
                        result = o1.getConstituentName().compareTo(o2.getConstituentName());
                    }
                }
                return result;
            }
        });
    }
}