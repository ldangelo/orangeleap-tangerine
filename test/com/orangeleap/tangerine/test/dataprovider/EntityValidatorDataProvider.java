package com.orangeleap.tangerine.test.dataprovider;

import org.testng.annotations.DataProvider;
import org.springframework.validation.BindException;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;

import java.util.*;
import java.math.BigDecimal;

public class EntityValidatorDataProvider {

    @DataProvider(name = "setupEntityValidatorResolvedFields")
    public static Object[][] createEntityValidatorResolveFieldsParameters() {
        Gift gift = new Gift();
        gift.setAddress(new Address());
        gift.addDistributionLine(new DistributionLine());
        gift.addDistributionLine(new DistributionLine());
        gift.addDistributionLine(new DistributionLine());

        Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
        unresolvedFieldMap.put("amount", new FieldRequired());
        unresolvedFieldMap.put("address.addressLine1", new FieldRequired());
        unresolvedFieldMap.put("phone.number", new FieldRequired());
        unresolvedFieldMap.put("distributionLines.amount", new FieldRequired());
        return new Object[][] { new Object[] { gift, unresolvedFieldMap } };
    }

    @DataProvider(name = "setupEntityValidatorRequiredFieldsNoConditions")
    public static Object[][] createEntityValidatorRequiredFieldsNoConditionsParameters() {
        Gift gift = new Gift();
        gift.setAddress(new Address());
        gift.setPhone(new Phone(1L, "972-933-2564"));
        gift.addDistributionLine(new DistributionLine());
        gift.addDistributionLine(new DistributionLine(new BigDecimal(15)));
        gift.addDistributionLine(new DistributionLine());

        Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
        unresolvedFieldMap.put("amount", new FieldRequired(true));
        unresolvedFieldMap.put("address.addressLine1", new FieldRequired(true));
        unresolvedFieldMap.put("phone.number", new FieldRequired(true));
        unresolvedFieldMap.put("distributionLines.amount", new FieldRequired(true));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("amount", "Amount");
        fieldLabelMap.put("address.addressLine1", "Address Line 1");
        fieldLabelMap.put("phone.number", "Phone Number");
        fieldLabelMap.put("distributionLines.amount", "Distro Line Amount");

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();
        
        return new Object[][] { new Object[] { gift, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

    @DataProvider(name = "setupEntityValidatorRequiredFieldsHasConditions")
    public static Object[][] createEntityValidatorRequiredFieldsHasConditionsParameters() {
        Gift gift = new Gift();
        gift.setAddress(new Address());
        gift.setPhone(new Phone(1L, "972-933-2564"));
        gift.addDistributionLine(new DistributionLine());
        gift.addDistributionLine(new DistributionLine(new BigDecimal(15), new BigDecimal(100), null, "Mom", null));
        gift.addDistributionLine(new DistributionLine(null, null, null, null, "Daddio"));

        Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
        unresolvedFieldMap.put("amount", new FieldRequired(true));
        unresolvedFieldMap.put("address.addressLine1", new FieldRequired(true));
        unresolvedFieldMap.put("phone.number", new FieldRequired(true));
        unresolvedFieldMap.put("distributionLines.amount", new FieldRequired(true));

        FieldCondition motivationCondition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("other_motivationCode"), null);
        List<FieldCondition> motivationConditionsList = new ArrayList<FieldCondition>();
        motivationConditionsList.add(motivationCondition);
        unresolvedFieldMap.put("distributionLines.motivationCode", new FieldRequired(true, motivationConditionsList));

        FieldCondition otherMotivationCondition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("motivationCode"), null);
        List<FieldCondition> otherMotivationConditionsList = new ArrayList<FieldCondition>();
        otherMotivationConditionsList.add(otherMotivationCondition);
        unresolvedFieldMap.put("distributionLines.other_motivationCode", new FieldRequired(true, otherMotivationConditionsList));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("amount", "Amount");
        fieldLabelMap.put("address.addressLine1", "Address Line 1");
        fieldLabelMap.put("phone.number", "Phone Number");
        fieldLabelMap.put("distributionLines.amount", "Distro Line Amount");
        fieldLabelMap.put("distributionLines.motivationCode", "Motivation Code");
        fieldLabelMap.put("distributionLines.other_motivationCode", "Motivation Code");

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

    @DataProvider(name = "setupEntityValidatorRegexpFieldsNoConditions")
    public static Object[][] createEntityValidatorRegexpFieldsNoConditionsParameters() {
        Gift gift = new Gift();
        gift.addCustomFieldValue("companyCost", "abcd");
        gift.addCustomFieldValue("clientCost", "5000");

        DistributionLine distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "asfasd");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "08-08-2008");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "08-08-09");
        gift.addDistributionLine(distroLine);

        Map<String, FieldValidation> unresolvedFieldMap = new HashMap<String, FieldValidation>();
        unresolvedFieldMap.put("customFieldMap[companyCost]", new FieldValidation("extensions:isDouble"));
        unresolvedFieldMap.put("customFieldMap[clientCost]", new FieldValidation("extensions:isDouble"));
        unresolvedFieldMap.put("distributionLines.customFieldMap[dueDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$"));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
        fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
        fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

    @DataProvider(name = "setupEntityValidatorRegexpFieldsHasConditions")
    public static Object[][] createEntityValidatorRegexpFieldsHasConditionsParameters() {
        Gift gift = new Gift();
        gift.addCustomFieldValue("companyCost", "abcd");
        gift.addCustomFieldValue("clientCost", "#@$@#$");
        gift.addCustomFieldValue("employeeCost", "#@$@#$");

        DistributionLine distroLine = new DistributionLine(new BigDecimal("15"));
        distroLine.addCustomFieldValue("bakery", "Bairds");
        distroLine.addCustomFieldValue("dueDate", "asfasd");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine(new BigDecimal("15"));
        distroLine.addCustomFieldValue("bakery", "Orowheat");
        distroLine.addCustomFieldValue("dueDate", "08-08-2008");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine(new BigDecimal("0"));
        distroLine.addCustomFieldValue("bakery", "Orowheat");
        distroLine.addCustomFieldValue("dueDate", "08-08-09");
        gift.addDistributionLine(distroLine);

        Map<String, FieldValidation> unresolvedFieldMap = new HashMap<String, FieldValidation>();
        unresolvedFieldMap.put("customFieldMap[companyCost]", new FieldValidation("extensions:isDouble"));

        FieldCondition clientCostCondition = new FieldCondition(new FieldDefinition("customFieldMap[companyCost]"), null, "abcd");
        List<FieldCondition> clientCostConditionsList = new ArrayList<FieldCondition>();
        clientCostConditionsList.add(clientCostCondition);
        unresolvedFieldMap.put("customFieldMap[clientCost]", new FieldValidation("extensions:isDouble", clientCostConditionsList));

        FieldCondition employeeCostCondition = new FieldCondition(new FieldDefinition("customFieldMap[companyCost]"), null, "15");
        List<FieldCondition> employeeCostConditionsList = new ArrayList<FieldCondition>();
        employeeCostConditionsList.add(employeeCostCondition);
        unresolvedFieldMap.put("customFieldMap[employeeCost]", new FieldValidation("extensions:isDouble", employeeCostConditionsList));

        FieldCondition dueDateCondition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[bakery]"), "Orowheat");
        List<FieldCondition> dueDateConditionsList = new ArrayList<FieldCondition>();
        dueDateConditionsList.add(dueDateCondition);
        unresolvedFieldMap.put("distributionLines.customFieldMap[dueDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$", dueDateConditionsList));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
        fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
        fieldLabelMap.put("customFieldMap[employeeCost]", "Employee Cost");
        fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldLabelMap, fieldValueMap, errorSet, unresolvedFieldMap } };
    }
}
