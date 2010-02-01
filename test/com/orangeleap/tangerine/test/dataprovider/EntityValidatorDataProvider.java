package com.orangeleap.tangerine.test.dataprovider;

import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.domain.customization.FieldCondition;
import com.orangeleap.tangerine.domain.customization.FieldDefinition;
import com.orangeleap.tangerine.domain.customization.FieldRequired;
import com.orangeleap.tangerine.domain.customization.FieldValidation;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.type.FieldType;
import com.orangeleap.tangerine.util.AES;
import com.orangeleap.tangerine.util.StringConstants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.validation.BindException;
import org.testng.annotations.DataProvider;

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
        gift.addCustomFieldValue("checkAccountNumber", AES.encrypt("1234567890"));

	    
        Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
	    FieldDefinition fieldDef = new FieldDefinition("amount");
	    fieldDef.setFieldType(FieldType.NUMBER);
	    typeMap.put("amount", fieldDef);

	    fieldDef = new FieldDefinition("address.addressLine1");
	    fieldDef.setFieldType(FieldType.TEXT);
	    typeMap.put("address.addressLine1", fieldDef);

	    fieldDef = new FieldDefinition("phone.number");
	    fieldDef.setFieldType(FieldType.TEXT);
	    typeMap.put("phone.number", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.amount");
	    fieldDef.setFieldType(FieldType.NUMBER);
	    typeMap.put("distributionLines.amount", fieldDef);

        fieldDef = new FieldDefinition("customFieldMap[checkAccountNumber]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkAccountNumber]", fieldDef);

        fieldDef = new FieldDefinition("customFieldMap[checkRoutingNumber]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkRoutingNumber]", fieldDef);
        gift.setFieldTypeMap(typeMap);


        Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
        unresolvedFieldMap.put("amount", new FieldRequired(true));
        unresolvedFieldMap.put("address.addressLine1", new FieldRequired(true));
        unresolvedFieldMap.put("phone.number", new FieldRequired(true));
        unresolvedFieldMap.put("distributionLines.amount", new FieldRequired(true));
        unresolvedFieldMap.put("customFieldMap[checkAccountNumber]", new FieldRequired(true));
        unresolvedFieldMap.put("customFieldMap[checkRoutingNumber]", new FieldRequired(true));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("amount", "Amount");
        fieldLabelMap.put("address.addressLine1", "Address Line 1");
        fieldLabelMap.put("phone.number", "Phone Number");
        fieldLabelMap.put("distributionLines.amount", "Distro Line Amount");
        fieldLabelMap.put("customFieldMap[checkAccountNumber]", "Check Account Number");
        fieldLabelMap.put("customFieldMap[checkRoutingNumber]", "Check Routing Number");
	    gift.setFieldLabelMap(fieldLabelMap);

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();
        
        return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

	@DataProvider(name = "setupEntityValidatorRequiredOtherFieldsNoConditionsGrid")
	public static Object[][] createEntityValidatorRequiredOtherFieldsNoConditionsGridParameters() {
	    Gift gift = new Gift();
	    gift.addDistributionLine(new DistributionLine(new BigDecimal(1), new BigDecimal(33), null, "A", null));
		gift.addDistributionLine(new DistributionLine(new BigDecimal(1), new BigDecimal(33), null, null, "123"));
		gift.addDistributionLine(new DistributionLine(new BigDecimal(1), new BigDecimal(33), null, null, null));


	    Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
		FieldDefinition fieldDef = new FieldDefinition("distributionLines.motivationCode");
		fieldDef.setFieldType(FieldType.CODE_OTHER);
		typeMap.put("distributionLines.motivationCode", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.other_motivationCode");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.other_motivationCode", fieldDef);
	    gift.setFieldTypeMap(typeMap);


	    Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
	    unresolvedFieldMap.put("distributionLines.motivationCode", new FieldRequired(true));

	    Map<String, String> fieldLabelMap = new HashMap<String, String>();
	    fieldLabelMap.put("distributionLines.motivationCode", "Motivation Code");
	    fieldLabelMap.put("distributionLines.other_motivationCode", "Other Motivation Code");
		gift.setFieldLabelMap(fieldLabelMap);

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}

	@DataProvider(name = "setupEntityValidatorRequiredOtherFieldsNoConditions")
	public static Object[][] createEntityValidatorRequiredOtherFieldsNoConditionsParameters() {
	    Gift gift = new Gift();
		gift.addCustomFieldValue("abba", "a");
		gift.addCustomFieldValue("other_abba", null);
		gift.addCustomFieldValue("dude", null);
		gift.addCustomFieldValue("other_dude", "123");
		gift.addCustomFieldValue("mom", null);
		gift.addCustomFieldValue("other_mom", null);


	    Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
		FieldDefinition fieldDef = new FieldDefinition("customFieldMap[abba]");
		fieldDef.setFieldType(FieldType.QUERY_LOOKUP_OTHER);
		typeMap.put("customFieldMap[abba]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[other_abba]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[other_abba]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[dude]");
		fieldDef.setFieldType(FieldType.QUERY_LOOKUP_OTHER);
		typeMap.put("customFieldMap[dude]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[other_dude]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[other_dude]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[mom]");
		fieldDef.setFieldType(FieldType.QUERY_LOOKUP_OTHER);
		typeMap.put("customFieldMap[mom]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[other_mom]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[other_mom]", fieldDef);
	    gift.setFieldTypeMap(typeMap);


	    Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();
	    unresolvedFieldMap.put("customFieldMap[abba]", new FieldRequired(true));
		unresolvedFieldMap.put("customFieldMap[dude]", new FieldRequired(true));
		unresolvedFieldMap.put("customFieldMap[mom]", new FieldRequired(true));

	    Map<String, String> fieldLabelMap = new HashMap<String, String>();
	    fieldLabelMap.put("customFieldMap[abba]", "Abba");
	    fieldLabelMap.put("customFieldMap[other_abba]", "Other Abba");
		fieldLabelMap.put("customFieldMap[dude]", "Dude");
		fieldLabelMap.put("customFieldMap[other_dude]", "Other Dude");
		fieldLabelMap.put("customFieldMap[mom]", "Mom");
		fieldLabelMap.put("customFieldMap[other_mom]", "Other Mom");
		gift.setFieldLabelMap(fieldLabelMap);

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}

    @DataProvider(name = "setupEntityValidatorRequiredFieldsHasConditions")
    public static Object[][] createEntityValidatorRequiredFieldsHasConditionsParameters() {
        Gift gift = new Gift();
        gift.setAddress(new Address());
        gift.setPhone(new Phone(1L, "972-933-2564"));
        gift.addDistributionLine(new DistributionLine());
        gift.addDistributionLine(new DistributionLine(new BigDecimal(15), new BigDecimal(100), null, "Mom", null));
        gift.addDistributionLine(new DistributionLine(null, null, null, null, "Daddio"));


	    Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
		FieldDefinition fieldDef = new FieldDefinition("amount");
		fieldDef.setFieldType(FieldType.NUMBER);
		typeMap.put("amount", fieldDef);

		fieldDef = new FieldDefinition("address.addressLine1");
		fieldDef.setFieldType(FieldType.TEXT);
		typeMap.put("address.addressLine1", fieldDef);

		fieldDef = new FieldDefinition("phone.number");
		fieldDef.setFieldType(FieldType.TEXT);
		typeMap.put("phone.number", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.amount");
		fieldDef.setFieldType(FieldType.NUMBER);
		typeMap.put("distributionLines.amount", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkAccountNumber]");
	    fieldDef.setFieldType(FieldType.ENCRYPTED);
	    typeMap.put("customFieldMap[checkAccountNumber]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkRoutingNumber]");
	    fieldDef.setFieldType(FieldType.ENCRYPTED);
	    typeMap.put("customFieldMap[checkRoutingNumber]", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.motivationCode");
	    fieldDef.setFieldType(FieldType.CODE_OTHER);
	    typeMap.put("distributionLines.motivationCode", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.other_motivationCode");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("distributionLines.other_motivationCode", fieldDef);
	    gift.setFieldTypeMap(typeMap);


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

        FieldCondition checksCondition = new FieldCondition(new FieldDefinition("customFieldMap[checkAccountNumber]"), null, null);
        List<FieldCondition> checksConditionsList = new ArrayList<FieldCondition>();
        checksConditionsList.add(checksCondition);
        unresolvedFieldMap.put("customFieldMap[checkRoutingNumber]", new FieldRequired(true, checksConditionsList));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("amount", "Amount");
        fieldLabelMap.put("address.addressLine1", "Address Line 1");
        fieldLabelMap.put("phone.number", "Phone Number");
        fieldLabelMap.put("distributionLines.amount", "Distro Line Amount");
        fieldLabelMap.put("distributionLines.motivationCode", "Motivation Code");
        fieldLabelMap.put("distributionLines.other_motivationCode", "Motivation Code");
        fieldLabelMap.put("customFieldMap[checkAccountNumber]", "Check Account Number");
        fieldLabelMap.put("customFieldMap[checkRoutingNumber]", "Check Routing Number");
	    gift.setFieldLabelMap(fieldLabelMap);

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

	@DataProvider(name = "setupEntityValidatorRequiredAdditionalGridFieldsHasConditions")
	public static Object[][] createEntityValidatorRequiredAdditionalGridFieldsHasConditionsParameters() {
	    Gift gift = new Gift();
		for (int x = 3; x < 5; x++) {
			DistributionLine line = new DistributionLine();
			line.addCustomFieldValue("abba", x + "56");
			line.addCustomFieldValue("additional_abba", "999" + StringConstants.CUSTOM_FIELD_SEPARATOR + "xyz");
			line.addCustomFieldValue("dude", "mud");
			line.addCustomFieldValue("additional_dude", "123" + StringConstants.CUSTOM_FIELD_SEPARATOR + x + "56");
			line.addCustomFieldValue("mom", null);
			line.addCustomFieldValue("additional_mom", null);
			line.addCustomFieldValue("dad", null);
			line.addCustomFieldValue("additional_dad", null);
			line.addCustomFieldValue("kid", null);
			line.addCustomFieldValue("additional_kid", null);

			gift.addDistributionLine(line);
		}

		Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
		FieldDefinition fieldDef = new FieldDefinition("distributionLines.customFieldMap[abba]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("distributionLines.customFieldMap[abba]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_abba]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.customFieldMap[additional_abba]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[dude]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("distributionLines.customFieldMap[dude]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_dude]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.customFieldMap[additional_dude]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[mom]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("distributionLines.customFieldMap[mom]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_mom]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.customFieldMap[additional_mom]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[dad]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("distributionLines.customFieldMap[dad]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_dad]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.customFieldMap[additional_dad]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[kid]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("distributionLines.customFieldMap[kid]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_kid]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("distributionLines.customFieldMap[additional_kid]", fieldDef);
		gift.setFieldTypeMap(typeMap);


		Map<String, String> fieldLabelMap = new HashMap<String, String>();
		fieldLabelMap.put("distributionLines.customFieldMap[abba]", "Abba");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_abba]", "Other Abba");
		fieldLabelMap.put("distributionLines.customFieldMap[dude]", "Dude");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_dude]", "Other Dude");
		fieldLabelMap.put("distributionLines.customFieldMap[mom]", "Mom");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_mom]", "Other Mom");
		fieldLabelMap.put("distributionLines.customFieldMap[dad]", "Dad");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_dad]", "Other Dad");
		fieldLabelMap.put("distributionLines.customFieldMap[kid]", "Kid");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_kid]", "Other Kid");
		gift.setFieldLabelMap(fieldLabelMap);


		Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();

	    FieldCondition condition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[abba]"), "456");
	    List<FieldCondition> conditionList = new ArrayList<FieldCondition>();
	    conditionList.add(condition);
	    unresolvedFieldMap.put("distributionLines.customFieldMap[mom]", new FieldRequired(true, conditionList));

		condition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[dude]"), "456");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("distributionLines.customFieldMap[dad]", new FieldRequired(true, conditionList));

		condition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[dude]"), "bub");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("distributionLines.customFieldMap[kid]", new FieldRequired(true, conditionList));

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}

	@DataProvider(name = "setupEntityValidatorRequiredAdditionalFieldsHasConditions")
	public static Object[][] createEntityValidatorRequiredAdditionalFieldsHasConditionsParameters() {
	    Gift gift = new Gift();
		gift.addCustomFieldValue("abba", "456");
		gift.addCustomFieldValue("additional_abba", "999" + StringConstants.CUSTOM_FIELD_SEPARATOR + "xyz");
		gift.addCustomFieldValue("dude", "mud");
		gift.addCustomFieldValue("additional_dude", "123" + StringConstants.CUSTOM_FIELD_SEPARATOR + "456");
		gift.addCustomFieldValue("mom", null);
		gift.addCustomFieldValue("additional_mom", null);
		gift.addCustomFieldValue("dad", null);
		gift.addCustomFieldValue("additional_dad", null);
		gift.addCustomFieldValue("kid", null);
		gift.addCustomFieldValue("additional_kid", null);


		Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
		FieldDefinition fieldDef = new FieldDefinition("customFieldMap[abba]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("customFieldMap[abba]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_abba]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[additional_abba]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[dude]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("customFieldMap[dude]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_dude]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[additional_dude]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[mom]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("customFieldMap[mom]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_mom]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[additional_mom]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[dad]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("customFieldMap[dad]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_dad]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[additional_dad]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[kid]");
		fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
		typeMap.put("customFieldMap[kid]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_kid]");
		fieldDef.setFieldType(FieldType.HIDDEN);
		typeMap.put("customFieldMap[additional_kid]", fieldDef);
		gift.setFieldTypeMap(typeMap);


		Map<String, String> fieldLabelMap = new HashMap<String, String>();
		fieldLabelMap.put("customFieldMap[abba]", "Abba");
		fieldLabelMap.put("customFieldMap[additional_abba]", "Other Abba");
		fieldLabelMap.put("customFieldMap[dude]", "Dude");
		fieldLabelMap.put("customFieldMap[additional_dude]", "Other Dude");
		fieldLabelMap.put("customFieldMap[mom]", "Mom");
		fieldLabelMap.put("customFieldMap[additional_mom]", "Other Mom");
		fieldLabelMap.put("customFieldMap[dad]", "Dad");
		fieldLabelMap.put("customFieldMap[additional_dad]", "Other Dad");
		fieldLabelMap.put("customFieldMap[kid]", "Kid");
		fieldLabelMap.put("customFieldMap[additional_kid]", "Other Kid");
		gift.setFieldLabelMap(fieldLabelMap);


		Map<String, FieldRequired> unresolvedFieldMap = new HashMap<String, FieldRequired>();

	    FieldCondition condition = new FieldCondition(new FieldDefinition("customFieldMap[abba]"), null, "456");
	    List<FieldCondition> conditionList = new ArrayList<FieldCondition>();
	    conditionList.add(condition);
	    unresolvedFieldMap.put("customFieldMap[mom]", new FieldRequired(true, conditionList));

		condition = new FieldCondition(new FieldDefinition("customFieldMap[dude]"), null, "456");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("customFieldMap[dad]", new FieldRequired(true, conditionList));

		condition = new FieldCondition(new FieldDefinition("customFieldMap[dude]"), null, "bub");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("customFieldMap[kid]", new FieldRequired(true, conditionList));

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}

    @DataProvider(name = "setupEntityValidatorRegexpFieldsNoConditions")
    public static Object[][] createEntityValidatorRegexpFieldsNoConditionsParameters() {
        Gift gift = new Gift();
        gift.addCustomFieldValue("companyCost", "abcd");
        gift.addCustomFieldValue("clientCost", "5000");
        gift.addCustomFieldValue("checkAccountNumber", AES.encrypt("abcdefghijk"));
        gift.addCustomFieldValue("checkRoutingNumber", AES.encrypt("9100234"));

        DistributionLine distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "asfasd");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "08-08-2008");
        gift.addDistributionLine(distroLine);

        distroLine = new DistributionLine();
        distroLine.addCustomFieldValue("dueDate", "08-08-09");
        gift.addDistributionLine(distroLine);


        Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
        FieldDefinition fieldDef = new FieldDefinition("customFieldMap[checkAccountNumber]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkAccountNumber]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkRoutingNumber]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkRoutingNumber]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[companyCost]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[companyCost]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[clientCost]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[clientCost]", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.customFieldMap[dueDate]");
        fieldDef.setFieldType(FieldType.DATE);
        typeMap.put("distributionLines.customFieldMap[dueDate]", fieldDef);
        gift.setFieldTypeMap(typeMap);


        Map<String, FieldValidation> unresolvedFieldMap = new HashMap<String, FieldValidation>();
        unresolvedFieldMap.put("customFieldMap[companyCost]", new FieldValidation("extensions:isDouble"));
        unresolvedFieldMap.put("customFieldMap[clientCost]", new FieldValidation("extensions:isDouble"));
        unresolvedFieldMap.put("distributionLines.customFieldMap[dueDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$"));
        unresolvedFieldMap.put("customFieldMap[checkAccountNumber]", new FieldValidation("^[0-9]*$"));
        unresolvedFieldMap.put("customFieldMap[checkRoutingNumber]", new FieldValidation("^[0-9]*$"));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
        fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
        fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");
        fieldLabelMap.put("customFieldMap[checkAccountNumber]", "Check Account Number");
        fieldLabelMap.put("customFieldMap[checkRoutingNumber]", "Check Routing Number");
	    gift.setFieldLabelMap(fieldLabelMap);

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

	@DataProvider(name = "setupEntityValidatorRegexpOtherAdditionalFieldsNoConditions")
	public static Object[][] createEntityValidatorRegexpOtherAdditionalFieldsNoConditionsParameters() {
	    Gift gift = new Gift();
	    gift.addCustomFieldValue("companyCost", "abcd");
	    gift.addCustomFieldValue("other_clientCost", "5000");

	    DistributionLine distroLine = new DistributionLine();
	    distroLine.addCustomFieldValue("dueDate", "asfasd");
	    gift.addDistributionLine(distroLine);

	    distroLine = new DistributionLine();
	    distroLine.addCustomFieldValue("additional_dueDate", "08-08-2008");
	    gift.addDistributionLine(distroLine);

		distroLine = new DistributionLine();
		distroLine.addCustomFieldValue("dueDate", "08-08-2008");
		distroLine.addCustomFieldValue("additional_dueDate", "asfasd");
		gift.addDistributionLine(distroLine);

	    Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
	    FieldDefinition fieldDef = new FieldDefinition("customFieldMap[companyCost]");
	    fieldDef.setFieldType(FieldType.CODE_OTHER);
	    typeMap.put("customFieldMap[companyCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[other_companyCost]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("customFieldMap[other_companyCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[clientCost]");
	    fieldDef.setFieldType(FieldType.CODE_OTHER);
	    typeMap.put("customFieldMap[clientCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[other_clientCost]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("customFieldMap[other_clientCost]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[dueDate]");
	    fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
	    typeMap.put("distributionLines.customFieldMap[dueDate]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_dueDate]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("distributionLines.customFieldMap[additional_dueDate]", fieldDef);
	    gift.setFieldTypeMap(typeMap);


	    Map<String, FieldValidation> unresolvedFieldMap = new HashMap<String, FieldValidation>();
	    unresolvedFieldMap.put("customFieldMap[companyCost]", new FieldValidation("extensions:isDouble"));
	    unresolvedFieldMap.put("customFieldMap[clientCost]", new FieldValidation("extensions:isDouble"));
	    unresolvedFieldMap.put("distributionLines.customFieldMap[dueDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$"));

	    Map<String, String> fieldLabelMap = new HashMap<String, String>();
	    fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
	    fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
	    fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");
		gift.setFieldLabelMap(fieldLabelMap);

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}

    @DataProvider(name = "setupEntityValidatorRegexpFieldsHasConditions")
    public static Object[][] createEntityValidatorRegexpFieldsHasConditionsParameters() {
        Gift gift = new Gift();
        gift.addCustomFieldValue("companyCost", "abcd");
        gift.addCustomFieldValue("clientCost", "#@$@#$");
        gift.addCustomFieldValue("employeeCost", "#@$@#$");
        gift.addCustomFieldValue("checkAccountNumber", AES.encrypt("999000"));
        gift.addCustomFieldValue("checkAccountNumber2", AES.encrypt("111333"));
        gift.addCustomFieldValue("checkNumber", "abba");
        gift.addCustomFieldValue("checkNumber2", "abba");

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


        Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
        FieldDefinition fieldDef = new FieldDefinition("customFieldMap[checkAccountNumber]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkAccountNumber]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkAccountNumber2]");
        fieldDef.setFieldType(FieldType.ENCRYPTED);
        typeMap.put("customFieldMap[checkAccountNumber2]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[companyCost]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[companyCost]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[clientCost]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[clientCost]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[employeeCost]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[employeeCost]", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.customFieldMap[dueDate]");
        fieldDef.setFieldType(FieldType.DATE);
        typeMap.put("distributionLines.customFieldMap[dueDate]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkNumber]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[checkNumber]", fieldDef);

	    fieldDef = new FieldDefinition("customFieldMap[checkNumber2]");
        fieldDef.setFieldType(FieldType.NUMBER);
        typeMap.put("customFieldMap[checkNumber2]", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.customFieldMap[bakery]");
        fieldDef.setFieldType(FieldType.TEXT);
        typeMap.put("distributionLines.customFieldMap[bakery]", fieldDef);

	    fieldDef = new FieldDefinition("distributionLines.customFieldMap[dueDate]");
        fieldDef.setFieldType(FieldType.TEXT);
        typeMap.put("distributionLines.customFieldMap[dueDate]", fieldDef);
        gift.setFieldTypeMap(typeMap);


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

        FieldCondition checksCondition1 = new FieldCondition(new FieldDefinition("customFieldMap[checkAccountNumber]"), null, "999000");
        List<FieldCondition> checksCondition1List = new ArrayList<FieldCondition>();
        checksCondition1List.add(checksCondition1);
        unresolvedFieldMap.put("customFieldMap[checkNumber]", new FieldValidation("^[0-9]*$", checksCondition1List));

        FieldCondition checksCondition2 = new FieldCondition(new FieldDefinition("customFieldMap[checkAccountNumber2]"), null, "999000");
        List<FieldCondition> checksCondition2List = new ArrayList<FieldCondition>();
        checksCondition2List.add(checksCondition2);
        unresolvedFieldMap.put("customFieldMap[checkNumber2]", new FieldValidation("^[0-9]*$", checksCondition2List));

        Map<String, String> fieldLabelMap = new HashMap<String, String>();
        fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
        fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
        fieldLabelMap.put("customFieldMap[employeeCost]", "Employee Cost");
        fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");
        fieldLabelMap.put("customFieldMap[checkAccountNumber]", "Check Account Number");
        fieldLabelMap.put("customFieldMap[checkAccountNumber2]", "Check Account Number 2");
        fieldLabelMap.put("customFieldMap[checkNumber]", "Check Number");
        fieldLabelMap.put("customFieldMap[checkNumber2]", "Check Number 2");
	    fieldLabelMap.put("distributionLines.customFieldMap[bakery]", "Bakery");
	    fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Due Date");
	    gift.setFieldLabelMap(fieldLabelMap);

        BindException errors = new BindException(gift, "gift");
        Map<String, Object> fieldValueMap = new HashMap<String, Object>();
        Set<String> errorSet = new HashSet<String>();

        return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
    }

	@DataProvider(name = "setupEntityValidatorRegexpOtherAdditionalFieldsHasConditions")
	public static Object[][] createEntityValidatorRegexpOtherAdditionalFieldsHasConditionsParameters() {
	    Gift gift = new Gift();
	    gift.addCustomFieldValue("companyCost", "abcd");
		gift.addCustomFieldValue("additional_companyCost", "xyz");
	    gift.addCustomFieldValue("clientCost", "#@$@#$");
	    gift.addCustomFieldValue("employeeCost", "#@$@#$");
		gift.addCustomFieldValue("moraleCost", "#@$@#$");

	    DistributionLine distroLine = new DistributionLine();
	    distroLine.addCustomFieldValue("bakery", "Wheat");
	    distroLine.addCustomFieldValue("dueDate", "asfasd");
	    gift.addDistributionLine(distroLine);

	    distroLine = new DistributionLine();
	    distroLine.addCustomFieldValue("bakery", "Bairds");
		distroLine.addCustomFieldValue("additional_bakery", "Orowheat");
	    distroLine.addCustomFieldValue("other_dueDate", "08-08-2008");
	    gift.addDistributionLine(distroLine);

	    distroLine = new DistributionLine();
	    distroLine.addCustomFieldValue("bakery", "Mutton");
		distroLine.addCustomFieldValue("dogDate", "blarg");
	    distroLine.addCustomFieldValue("additional_dogDate", "08-08-09");
	    gift.addDistributionLine(distroLine);


	    Map<String, FieldDefinition> typeMap = new HashMap<String, FieldDefinition>();
	    FieldDefinition fieldDef = new FieldDefinition("customFieldMap[companyCost]");
	    fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
	    typeMap.put("customFieldMap[companyCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[additional_companyCost]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("customFieldMap[additional_companyCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[clientCost]");
	    fieldDef.setFieldType(FieldType.NUMBER);
	    typeMap.put("customFieldMap[clientCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[employeeCost]");
	    fieldDef.setFieldType(FieldType.NUMBER);
	    typeMap.put("customFieldMap[employeeCost]", fieldDef);

		fieldDef = new FieldDefinition("customFieldMap[moraleCost]");
	    fieldDef.setFieldType(FieldType.NUMBER);
	    typeMap.put("customFieldMap[moraleCost]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[dueDate]");
	    fieldDef.setFieldType(FieldType.CODE_OTHER);
	    typeMap.put("distributionLines.customFieldMap[dueDate]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[other_dueDate]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("distributionLines.customFieldMap[other_dueDate]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[dogDate]");
	    fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
	    typeMap.put("distributionLines.customFieldMap[dogDate]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_dogDate]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("distributionLines.customFieldMap[additional_dogDate]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[bakery]");
	    fieldDef.setFieldType(FieldType.MULTI_PICKLIST_ADDITIONAL);
	    typeMap.put("distributionLines.customFieldMap[bakery]", fieldDef);

		fieldDef = new FieldDefinition("distributionLines.customFieldMap[additional_bakery]");
	    fieldDef.setFieldType(FieldType.HIDDEN);
	    typeMap.put("distributionLines.customFieldMap[additional_bakery]", fieldDef);
	    gift.setFieldTypeMap(typeMap);


		Map<String, FieldValidation> unresolvedFieldMap = new HashMap<String, FieldValidation>();
	    FieldCondition condition = new FieldCondition(new FieldDefinition("customFieldMap[companyCost]"), null, "abcd");
	    List<FieldCondition> conditionList = new ArrayList<FieldCondition>();
	    conditionList.add(condition);
	    unresolvedFieldMap.put("customFieldMap[clientCost]", new FieldValidation("extensions:isDouble", conditionList));

	    condition = new FieldCondition(new FieldDefinition("customFieldMap[companyCost]"), null, "xyz");
	    conditionList = new ArrayList<FieldCondition>();
	    conditionList.add(condition);
	    unresolvedFieldMap.put("customFieldMap[employeeCost]", new FieldValidation("extensions:isDouble", conditionList));

		condition = new FieldCondition(new FieldDefinition("customFieldMap[companyCost]"), null, "mommma");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("customFieldMap[moraleCost]", new FieldValidation("extensions:isDouble", conditionList));

	    condition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[bakery]"), "Orowheat");
	    conditionList = new ArrayList<FieldCondition>();
	    conditionList.add(condition);
	    unresolvedFieldMap.put("distributionLines.customFieldMap[dueDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$", conditionList));

		condition = new FieldCondition(new FieldDefinition("distributionLines"), new FieldDefinition("customFieldMap[bakery]"), "Mutton");
		conditionList = new ArrayList<FieldCondition>();
		conditionList.add(condition);
		unresolvedFieldMap.put("distributionLines.customFieldMap[dogDate]", new FieldValidation("^\\d{2}\\-\\d{2}-\\d{4}$", conditionList));


	    Map<String, String> fieldLabelMap = new HashMap<String, String>();
	    fieldLabelMap.put("customFieldMap[companyCost]", "Company Cost");
		fieldLabelMap.put("customFieldMap[additional_companyCost]", "Additional Company Cost");
	    fieldLabelMap.put("customFieldMap[clientCost]", "Client Cost");
	    fieldLabelMap.put("customFieldMap[employeeCost]", "Employee Cost");
		fieldLabelMap.put("customFieldMap[moraleCost]", "Morale Cost");
	    fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Distro Line Due Date");
		fieldLabelMap.put("distributionLines.customFieldMap[bakery]", "Bakery");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_bakery]", "Additional Bakery");
		fieldLabelMap.put("distributionLines.customFieldMap[dueDate]", "Due Date");
		fieldLabelMap.put("distributionLines.customFieldMap[other_dueDate]", "Other Due Date");
		fieldLabelMap.put("distributionLines.customFieldMap[dogDate]", "Dog Date");
		fieldLabelMap.put("distributionLines.customFieldMap[additional_dogDate]", "Additional Dog Date");
		gift.setFieldLabelMap(fieldLabelMap);

	    BindException errors = new BindException(gift, "gift");
	    Map<String, Object> fieldValueMap = new HashMap<String, Object>();
	    Set<String> errorSet = new HashSet<String>();

	    return new Object[][] { new Object[] { gift, errors, fieldValueMap, errorSet, unresolvedFieldMap } };
	}
}
