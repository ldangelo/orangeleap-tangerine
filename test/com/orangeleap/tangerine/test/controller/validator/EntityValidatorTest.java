package com.orangeleap.tangerine.test.controller.validator;

import com.orangeleap.tangerine.controller.validator.EntityValidator;
import com.orangeleap.tangerine.domain.AbstractEntity;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.test.dataprovider.EntityValidatorDataProvider;
import org.springframework.validation.Errors;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

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
    public void testValidateRequiredFieldsNoConditions(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
        Assert.assertTrue(errors.hasFieldErrors("amount"));
        Assert.assertTrue(errors.hasFieldErrors("address.addressLine1"));
        Assert.assertFalse(errors.hasFieldErrors("phone.number"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].amount"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].amount"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].amount"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[checkAccountNumber]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[checkRoutingNumber]"));
    }

    @Test(dataProvider = "setupEntityValidatorRequiredFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRequiredFieldsHasConditions(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
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
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[checkAccountNumber]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[checkRoutingNumber]"));
    }

	@Test(dataProvider = "setupEntityValidatorRequiredOtherFieldsNoConditionsGrid", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRequiredOtherFieldsNoConditionsGrid(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
	    invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
	    Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].motivationCode"));
	    Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].motivationCode"));
	    Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].motivationCode"));
	}

	@Test(dataProvider = "setupEntityValidatorRequiredOtherFieldsNoConditions", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRequiredOtherFieldsNoConditions(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
	    invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
		Assert.assertFalse(errors.hasFieldErrors("customFieldMap[abba]"));
		Assert.assertFalse(errors.hasFieldErrors("customFieldMap[dude]"));
		Assert.assertTrue(errors.hasFieldErrors("customFieldMap[mom]"));
	}

	@Test(dataProvider = "setupEntityValidatorRequiredAdditionalFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRequiredAdditionalFieldsHasConditions(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
	    invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
		Assert.assertTrue(errors.hasFieldErrors("customFieldMap[mom]"));
		Assert.assertTrue(errors.hasFieldErrors("customFieldMap[dad]"));
		Assert.assertFalse(errors.hasFieldErrors("customFieldMap[kid]"));
	}

	@Test(dataProvider = "setupEntityValidatorRequiredAdditionalGridFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRequiredAdditionalGridFieldsHasConditions(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
	    invokeValidateRequiredFields(entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
		Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[mom]"));
		Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[dad]"));
		Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[kid]"));
		Assert.assertTrue(errors.hasFieldErrors("distributionLines[1].customFieldMap[mom]"));
		Assert.assertTrue(errors.hasFieldErrors("distributionLines[1].customFieldMap[dad]"));
		Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[kid]"));
	}

    private Object invokeValidateRequiredFields(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldRequired> unresolvedRequiredFieldMap) throws Exception {
        EntityValidator entityValidator = new EntityValidator();
        Method method = entityValidator.getClass().getDeclaredMethod("validateRequiredFields", AbstractEntity.class,
                Errors.class, Map.class, Set.class, Map.class);
        method.setAccessible(true);
        return method.invoke(entityValidator, entity, errors, fieldValueMap, errorSet, unresolvedRequiredFieldMap);
    }

    @Test(dataProvider = "setupEntityValidatorRegexpFieldsNoConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRegexNoConditions(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        invokeValidateRegex(entity, errors, fieldValueMap, errorSet, unresolvedValidationMap);
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[companyCost]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[clientCost]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[checkAccountNumber]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[checkRoutingNumber]"));
    }

    @Test(dataProvider = "setupEntityValidatorRegexpFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
    public void testValidateRegexHasConditions(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        invokeValidateRegex(entity, errors, fieldValueMap, errorSet, unresolvedValidationMap);
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[companyCost]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[clientCost]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[employeeCost]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
        Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dueDate]"));
        Assert.assertTrue(errors.hasFieldErrors("customFieldMap[checkNumber]"));
        Assert.assertFalse(errors.hasFieldErrors("customFieldMap[checkNumber2]"));
    }

	@Test(dataProvider = "setupEntityValidatorRegexpOtherAdditionalFieldsNoConditions", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRegexpOtherAdditionalFieldsNoConditions(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
	    invokeValidateRegex(entity, errors, fieldValueMap, errorSet, unresolvedValidationMap);
	    Assert.assertTrue(errors.hasFieldErrors("customFieldMap[companyCost]"));
	    Assert.assertFalse(errors.hasFieldErrors("customFieldMap[clientCost]"));
	    Assert.assertTrue(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
	    Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
	    Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dueDate]"));
	}

	@Test(dataProvider = "setupEntityValidatorRegexpOtherAdditionalFieldsHasConditions", dataProviderClass = EntityValidatorDataProvider.class)
	public void testValidatorRegexpOtherAdditionalFieldsHasConditions(AbstractEntity entity, Errors errors,
	                                      Map<String, Object> fieldValueMap, Set<String> errorSet,
	                                      Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
	    invokeValidateRegex(entity, errors, fieldValueMap, errorSet, unresolvedValidationMap);
	    Assert.assertTrue(errors.hasFieldErrors("customFieldMap[clientCost]"));
	    Assert.assertTrue(errors.hasFieldErrors("customFieldMap[employeeCost]"));
		Assert.assertFalse(errors.hasFieldErrors("customFieldMap[moraleCost]"));
	    Assert.assertFalse(errors.hasFieldErrors("distributionLines[0].customFieldMap[dueDate]"));
	    Assert.assertFalse(errors.hasFieldErrors("distributionLines[1].customFieldMap[dueDate]"));
	    Assert.assertTrue(errors.hasFieldErrors("distributionLines[2].customFieldMap[dogDate]"));
	}

    private Object invokeValidateRegex(AbstractEntity entity, Errors errors,
                                          Map<String, Object> fieldValueMap, Set<String> errorSet,
                                          Map<String, FieldValidation> unresolvedValidationMap) throws Exception {
        EntityValidator entityValidator = new EntityValidator();
        Method method = entityValidator.getClass().getDeclaredMethod("validateRegex", AbstractEntity.class,
                Errors.class, Map.class, Set.class, Map.class);
        method.setAccessible(true);
        return method.invoke(entityValidator, entity, errors, fieldValueMap, errorSet, unresolvedValidationMap);
    }
}
