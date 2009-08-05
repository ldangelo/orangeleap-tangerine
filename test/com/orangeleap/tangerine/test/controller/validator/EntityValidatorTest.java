package com.orangeleap.tangerine.test.controller.validator;

import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.EntityValidatorDataProvider;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.service.impl.AuditServiceImpl;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Method;

public class EntityValidatorTest extends BaseTest {

    @Test(dataProvider = "setupEntityValidatorResolvedFields", dataProviderClass = EntityValidatorDataProvider.class)
    public void testResolveFieldNames(AbstractEntity entity, Map unresolvedFieldMap) throws Exception {
        Map resolvedFieldMap = (Map) invokeResolveFieldNames(entity, unresolvedFieldMap);
        Assert.assertNotNull(resolvedFieldMap);
        Assert.assertFalse(resolvedFieldMap.isEmpty());

        Assert.assertTrue(resolvedFieldMap.containsKey("amount"));
        Assert.assertTrue(resolvedFieldMap.containsKey("address.addressLine1"));
        Assert.assertTrue(resolvedFieldMap.containsKey("phone.number"));
        Assert.assertTrue(resolvedFieldMap.containsKey("distributionLines[0].amount"));
        Assert.assertTrue(resolvedFieldMap.containsKey("distributionLines[1].amount"));
        Assert.assertTrue(resolvedFieldMap.containsKey("distributionLines[2].amount"));
    }

    private Object invokeResolveFieldNames(AbstractEntity entity, Map unresolvedFieldMap) throws Exception {
        EntityValidator entityValidator = new EntityValidator();
        Method method = entityValidator.getClass().getDeclaredMethod("resolveFieldNames", AbstractEntity.class, Map.class);
        method.setAccessible(true);
        return method.invoke(entityValidator, entity, unresolvedFieldMap);
    }

    @Test(dataProvider = "setupEntityValidatorRequiredFieldsNoConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRequiredFieldsNoConditions(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        invokeValidateRequiredFields(entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
        Assert.assertTrue(errors.hasFieldErrors("amount"));
        Assert.assertTrue(errors.hasFieldErrors("address.addressLine1"));
        Assert.assertFalse(errors.hasFieldErrors("phone.number"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].amount"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].amount"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].amount"));
    }

    @Test(dataProvider = "setupEntityValidatorRequiredFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRequiredFieldsHasConditions(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        invokeValidateRequiredFields(entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
        Assert.assertTrue(errors.hasFieldErrors("amount"));
        Assert.assertTrue(errors.hasFieldErrors("address.addressLine1"));
        Assert.assertFalse(errors.hasFieldErrors("phone.number"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].amount"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].motivationCode"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].other_motivationCode"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].amount"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].motivationCode"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].other_motivationCode"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].amount"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[2].motivationCode"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[2].other_motivationCode"));
    }

    private Object invokeValidateRequiredFields(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        EntityValidator entityValidator = new EntityValidator();
        Method method = entityValidator.getClass().getDeclaredMethod("validateRequiredFields", AbstractEntity.class,
                Errors.class, Map.class, Map.class, Set.class, Map.class);
        method.setAccessible(true);
        return method.invoke(entityValidator, entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
    }

    @Test(dataProvider = "setupEntityValidatorRegexpFieldsNoConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRegexNoConditions(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        invokeValidateRegex(entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedValidationMap);
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[companyCost]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[clientCost]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dueDate]"));
    }

    @Test(dataProvider = "setupEntityValidatorRegexpFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRegexHasConditions(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        invokeValidateRegex(entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedValidationMap);
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[companyCost]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[clientCost]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[employeeCost]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dueDate]"));
    }

    private Object invokeValidateRegex(AbstractEntity entity, Errors errors, Map<String, String> fieldLabelMap,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        EntityValidator entityValidator = new EntityValidator();
        Method method = entityValidator.getClass().getDeclaredMethod("validateRegex", AbstractEntity.class,
                Errors.class, Map.class, Map.class, Set.class, Map.class);
        method.setAccessible(true);
        return method.invoke(entityValidator, entity, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedValidationMap);
    }
}
