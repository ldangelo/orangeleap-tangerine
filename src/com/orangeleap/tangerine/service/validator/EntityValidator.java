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

package com.orangeleap.tangerine.service.validator;

import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.CommunicationHistory;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.EmailAware;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.CustomField;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.paymentInfo.AbstractPaymentInfoEntity;
import com.orangeleap.tangerine.domain.paymentInfo.GiftInKind;
import com.orangeleap.tangerine.service.SiteService;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.type.PageType;
import com.orangeleap.tangerine.util.AES;
import com.orangeleap.tangerine.util.OLLogger;
import com.orangeleap.tangerine.util.StringConstants;
import com.orangeleap.tangerine.util.TangerineUserHelper;
import com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.AbstractFieldHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

public class EntityValidator implements Validator {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());
    
    private static final String EXTENSIONS = "extensions:";

    @Resource(name="tangerineUserHelper")
    protected TangerineUserHelper tangerineUserHelper;
    
    @Resource(name="addressValidator")
    protected AddressValidator addressValidator;
    
    @Resource(name="phoneValidator")
    protected PhoneValidator phoneValidator;
    
    @Resource(name="emailValidator")
    protected com.orangeleap.tangerine.service.validator.EmailValidator emailValidator;
    
    @Resource(name="paymentSourceValidator")
    protected PaymentSourceValidator paymentSourceValidator;
    
    @Resource(name="siteService")
    protected SiteService siteService;

    protected PageType pageType;

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean supports(Class clazz) {
        return 
        Constituent.class.equals(clazz) 
        || AbstractPaymentInfoEntity.class.isAssignableFrom(clazz)
        || CommunicationHistory.class.equals(clazz) 
        || GiftInKind.class.equals(clazz) 
        || Address.class.equals(clazz) 
        || Email.class.equals(clazz) 
        || Phone.class.equals(clazz) 
        || PaymentSource.class.equals(clazz);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validate:");
        }
        validateSubEntities(target, errors);

        Set<String> errorSet = new HashSet<String>();

        if (errors.hasErrors()) {
            List<FieldError> e = errors.getFieldErrors();
            for (FieldError error : e) {
                errorSet.add(error.getField());
            }
        }
        
        AbstractEntity entity = (AbstractEntity) target;

        // used as a cache to prevent having to use reflection if the value has already been read
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();

        // used to know that a field already has an error, so don't add another

        // first, validate required fields
        if (!entity.isSuppressValidationForRequired()) {
            Map<String, FieldRequired> unresolvedRequiredFieldMap = siteService.readRequiredFields(pageType, tangerineUserHelper.lookupUserRoles());
            validateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
        }

        // next, validate custom validation (regex)  
        Map<String, FieldValidation> validationMap = siteService.readFieldValidations(pageType, tangerineUserHelper.lookupUserRoles());
        validateRegex(entity, errors, fieldValueMap, errorSet, validationMap);
    }
    
    protected void validateSubEntities(Object target, Errors errors) {
        if (logger.isTraceEnabled()) {
            logger.trace("validateSubEntities:");
        }
        if (target instanceof PaymentSourceAware) {
            PaymentSourceAware obj = (PaymentSourceAware) target;
			if (isNew(obj.getPaymentSource())) {
				paymentSourceValidator.validatePaymentSource(target, errors);
			}
        }
        if (target instanceof AddressAware) {
            AddressAware obj = (AddressAware) target;
	        if (isNew(obj.getAddress())) {
                addressValidator.validateAddress(target, errors);
            }
        }

        if (target instanceof PhoneAware) {
            PhoneAware obj = (PhoneAware) target;
	        if (isNew(obj.getPhone())) {
                phoneValidator.validatePhone(target, errors);
            }
        }

        if (target instanceof EmailAware) {
            EmailAware obj = (EmailAware) target;
	        if (isNew(obj.getEmail())) {
                emailValidator.validateEmail(target, errors);
            }
        }
    }

    /**
     * Resolve the field names.  For collection objects, resolve their indexed field name (i.e,
     * distributionLines[0].amount
     * @param entity
     * @param unresolvedFieldMap
     * @return resolved field names
     */
    @SuppressWarnings("unchecked")
    protected Map resolveFieldNames(AbstractEntity entity, Map unresolvedFieldMap) {
        Map resolvedFieldMap = new ListOrderedMap();
        if (unresolvedFieldMap != null) {
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            for (Object keyObj : unresolvedFieldMap.keySet()) {
                String key = keyObj.toString();
                if (key.indexOf('.') > 0) {
                    // see if this is a collection
                    int dotIndex = key.indexOf('.');
                    String propertyName = key.substring(0, dotIndex);
                    if (bw.isReadableProperty(propertyName)) {
                        if (bw.getPropertyValue(propertyName) instanceof Collection) {
                            Collection coll = (Collection) bw.getPropertyValue(propertyName);

                            Object mapValue = unresolvedFieldMap.get(key);
                            for (int x = 0; x < coll.size(); x++) {
                                StringBuilder newKey = new StringBuilder(propertyName);
                                newKey.append("[").append(x).append("]");
                                newKey.append(key.substring(dotIndex));
                                resolvedFieldMap.put(newKey.toString(), mapValue);
                            }
                        }
                        else {
                            resolvedFieldMap.put(key, unresolvedFieldMap.get(key));
                        }
                    }
                }
                else if (bw.isReadableProperty(key)) {
                    resolvedFieldMap.put(key, unresolvedFieldMap.get(key));
                }
            }
        }
        return resolvedFieldMap;
    }

    @SuppressWarnings("unchecked")
    protected void validateRequiredFields(AbstractEntity entity, Errors errors, 
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) {
        Map<String, FieldRequired> requiredFieldMap = resolveFieldNames(entity, unresolvedRequiredFieldMap);
        if (requiredFieldMap != null && !requiredFieldMap.isEmpty()) {
            for (Map.Entry<String, FieldRequired> fieldRequiredEntry : requiredFieldMap.entrySet()) {
                String key = fieldRequiredEntry.getKey();
                FieldRequired fieldRequired = fieldRequiredEntry.getValue();
                List<FieldCondition> conditions = fieldRequired.getFieldConditions();
                boolean conditionsMet = this.areFieldConditionsMet(conditions, key, entity, fieldValueMap);

                if (!conditionsMet) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation doesn't apply: conditions not met");
                    }
                    continue;
                }
                if (errorSet.contains(key)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation: error already exists so don't add another one");
                    }
                    continue;
                }
                String propertyValueAsString = getPropertyValueAsString(key, entity, fieldValueMap);
                boolean required = fieldRequired.isRequired();

                if ((required && StringUtils.isEmpty(propertyValueAsString)) && !errorSet.contains(key)) {
	                String errorKey = removeGridIndex(key);
                    errors.rejectValue(key, "fieldRequiredFailure", new String[] { entity.getFieldLabelMap().get(errorKey) }, "no message provided for the validation error: fieldRequiredFailure");
                    errorSet.add(key);
                }
            }
        }
    }
    
    protected boolean areFieldConditionsMet(List<FieldCondition> conditions, String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        boolean conditionsMet = true;
        if (conditions != null) {
            for (FieldCondition fieldCondition : conditions) {
                logger.trace("areFieldConditionsMet: key = " + key + ", condition - dependent = " + fieldCondition.getDependentFieldDefinition().getFieldName() + ", dependent secondary = " + (fieldCondition.getDependentSecondaryFieldDefinition() == null ? "null" : fieldCondition.getDependentSecondaryFieldDefinition().getFieldName())
                        + ", dependent value = " + fieldCondition.getValue());
                String gridIndex = findGridIndex(key);

                StringBuilder dependentKey = new StringBuilder(fieldCondition.getDependentFieldDefinition().getFieldName()); // the dependentKey DOES include the gridIndex, if any
                if (gridIndex != null) {
                    dependentKey.append(gridIndex);
                }
                if (fieldCondition.getDependentSecondaryFieldDefinition() != null) {
                    dependentKey.append(".").append(fieldCondition.getDependentSecondaryFieldDefinition().getFieldName());
                }
                Object dependentPropertyValue = getPropertyValue(dependentKey.toString(), entity, fieldValueMap);
	            dependentPropertyValue = checkOtherAdditionalValues(entity, dependentPropertyValue,
			            dependentKey.toString(), fieldValueMap);

                if (fieldCondition.getValue() == null) {
                    if (dependentPropertyValue != null) {
                        conditionsMet = false;
                    }
                }
                else {
                    List<String> conditionValues = Arrays.asList(fieldCondition.getValue().split("\\|"));
                    if (dependentPropertyValue == null || ( ! "!null".equals(fieldCondition.getValue()) && ! containsConditionValue(conditionValues, dependentPropertyValue)) ) {
                        conditionsMet = false;
                    }
                }
            }
        }
        return conditionsMet;
    }

    @SuppressWarnings("unchecked")
    protected void validateRegex(AbstractEntity entity, Errors errors, Map<String, Object> fieldValueMap,
                                 Set<String> errorSet, Map<String, FieldValidation> unresolvedValidationMap) {
        Map<String, FieldValidation> validationMap = resolveFieldNames(entity, unresolvedValidationMap);
        if (validationMap != null && !validationMap.isEmpty()) {
            for (Map.Entry<String, FieldValidation> validationEntry : validationMap.entrySet()) {
                String key = validationEntry.getKey();
                FieldValidation validation = validationEntry.getValue();
                List<FieldCondition> conditions = validation.getFieldConditions();
                boolean conditionsMet = this.areFieldConditionsMet(conditions, key, entity, fieldValueMap);
                if (!conditionsMet) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation doesn't apply:  conditions not met");
                    }
                    continue;
                }
                if (errorSet.contains(key)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(key + " validation:  error already exists so don't add another one");
                    }
                    continue;
                }
                String propertyValueAsString = getPropertyValueAsString(key, entity, fieldValueMap);
                String regex = validation.getRegex();
                boolean valid = false;

                if (propertyValueAsString.isEmpty()) {
                	// 'required' is validated in validateRequiredFields()
                	valid = true;
                }
                else {
	                if (propertyValueAsString.indexOf(StringConstants.CUSTOM_FIELD_SEPARATOR) > -1) {
		                String[] theseValues = propertyValueAsString.split(StringConstants.CUSTOM_FIELD_SEPARATOR);
		                for (String thisVal : theseValues) {
			                valid = checkRegExvalid(thisVal, regex);
			                if ( ! valid) {
				                propertyValueAsString = thisVal; // thisVal is the one value in the concatenated string that is not valid
				                break;
			                }
		                }
	                }
	                else {
		                valid = checkRegExvalid(propertyValueAsString, regex);
	                }
                }
                
                if ( ! valid && ! errorSet.contains(key)) {
                    String errorKey = removeGridIndex(key);

                    propertyValueAsString = entity.isEncryptedFieldType(key) ? AES.mask(propertyValueAsString) : propertyValueAsString; // For encrypted values, make sure to not display the decrypted value but the mask instead

                    errors.rejectValue(key, "fieldValidationFailure", new String[] { entity.getFieldLabelMap().get(errorKey),
                            propertyValueAsString }, "no message provided for the validation error: fieldValidationFailure");
                }
            }
        }
    }

	private boolean checkRegExvalid(String value, String regex) {
		boolean valid;
		if (regex.startsWith(EXTENSIONS)) {
			valid = new ExtendedValidationSupport().validate(value, regex.substring(EXTENSIONS.length()));
		}
		else {
			valid = value.matches(regex);
		}
		return valid;
	}

    protected Object getPropertyValue(String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        Object propertyValue = fieldValueMap.get(key);
        if (propertyValue == null) {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(entity);
            if (beanWrapper.isReadableProperty(key)) {
                propertyValue = beanWrapper.getPropertyValue(key);
                if (propertyValue instanceof CustomField) {
                    propertyValue = ((CustomField) propertyValue).getValue();
                }
                if (entity.isEncryptedFieldType(key) && propertyValue != null) {
                    try {
                        propertyValue = AES.decrypt(propertyValue.toString());
                    }
                    catch (Exception ex) {
                        logger.warn("getPropertyValue: could not decrypt propertyName = " + key + " value = " + AES.mask(propertyValue.toString()));
                    }
                }
                fieldValueMap.put(key, propertyValue);
            }
        }
        return propertyValue;
    }

    protected String getPropertyValueAsString(String key, AbstractEntity entity, Map<String, Object> fieldValueMap) {
        Object propertyValue = getPropertyValue(key, entity, fieldValueMap);
		propertyValue = checkOtherAdditionalValues(entity, propertyValue, key, fieldValueMap);
        return propertyValue == null ? StringConstants.EMPTY : propertyValue.toString();
    }

	protected boolean isNew(AbstractEntity entity) {
		return entity != null && entity.isNew(); 
	}

    protected String findGridIndex(String fieldName) {
		Matcher matcher = Pattern.compile("^.+(\\[\\d+\\]).+$").matcher(fieldName);
		int start = 0;
		String s = null;
		if (matcher != null) {
		    while (matcher.find(start)) {
		        s = matcher.group(1);
		        start = matcher.end();
		    }
		}
		return s;
	}

	protected String removeGridIndex(String key) {
		return key.replaceAll("\\[\\d+\\]", StringConstants.EMPTY);
	}

	private Object checkOtherAdditionalValues(AbstractEntity entity, Object propertyValue, String key, Map<String, Object> fieldValueMap) {
		String gridIndex = findGridIndex(key);
		String fieldTypeKey = removeGridIndex(key); // the fieldTypeKey doesn't include the gridIndex, if any
		FieldDefinition fieldDef = entity.getFieldTypeMap().get(fieldTypeKey);
		if (fieldDef != null) {
			FieldType thisFieldType = entity.getFieldTypeMap().get(fieldTypeKey).getFieldType();

			/* Use the 'other' value if the propertyValue is null or empty string */
			if (FieldType.supportsOtherValue(thisFieldType) &&
					(propertyValue == null || StringUtils.isEmpty(propertyValue.toString())) ) {

				String otherKey = AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.OTHER_PREFIX, fieldTypeKey);

				if (gridIndex != null) {
					// put back the grid index to get the other/additional property value
					otherKey = otherKey.replaceFirst("\\.", gridIndex + ".");
				}
				propertyValue = getPropertyValue(otherKey, entity, fieldValueMap);
			}
			/* Append the 'additional' value to the propertyValue (if not null or empty string) */
			else if (FieldType.supportsAdditionalValues(thisFieldType)) {

				String additionalKey = AbstractFieldHandler.resolveUnescapedPrefixedFieldName(StringConstants.ADDITIONAL_PREFIX, fieldTypeKey);

				if (gridIndex != null) {
					// put back the grid index to get the other/additional property value
					additionalKey = additionalKey.replaceFirst("\\.", gridIndex + ".");
				}
				Object additionalValue = getPropertyValue(additionalKey, entity, fieldValueMap);
				if (propertyValue != null && ! StringUtils.isEmpty(propertyValue.toString()) &&
						additionalValue != null && ! StringUtils.isEmpty(additionalValue.toString())) {
					propertyValue = new StringBuilder(propertyValue.toString()).append(StringConstants.CUSTOM_FIELD_SEPARATOR).append(additionalValue).toString();
				}
				else if ( additionalValue != null && ! StringUtils.isEmpty(additionalValue.toString()) &&
							(propertyValue == null || StringUtils.isEmpty(propertyValue.toString())) ) {
					propertyValue = additionalValue;
				}
			}
		}
		return propertyValue;
	}

	private boolean containsConditionValue(List<String> conditionValues, Object dependentPropertyValue) {
		boolean hasConditionValue = false;
		if (dependentPropertyValue != null) {
			if (dependentPropertyValue.toString().indexOf(StringConstants.CUSTOM_FIELD_SEPARATOR) > -1) {
				String[] allVals = dependentPropertyValue.toString().split(StringConstants.CUSTOM_FIELD_SEPARATOR);
				for (String thisValue : allVals) {
					if (conditionValues.contains(thisValue)) {
						hasConditionValue = true;
						break;
					}
				}
			}
			else {
				hasConditionValue = conditionValues.contains(dependentPropertyValue.toString());
			}
		}
		return hasConditionValue;
	}
}
