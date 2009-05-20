package com.orangeleap.tangerine.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.orangeleap.tangerine.domain.communication.AbstractCommunicatorEntity;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.domain.paymentInfo.Pledge;
import com.orangeleap.tangerine.domain.paymentInfo.RecurringGift;
import com.orangeleap.tangerine.util.StringConstants;

public class Person extends AbstractCommunicatorEntity {

    private static final long serialVersionUID = 1L;

    public static final String INDIVIDUAL = "individual";
    public static final String ORGANIZATION = "organization";
    public static final String FORMAL_SALUTATION = "formalSalutation";
    public static final String INFORMAL_SALUTATION = "informalSalutation";
    public static final String HEAD_OF_HOUSEHOLD_SALUTATION = "headOfHouseholdSalutation";
    public static final String DONOR_PROFILES = "donorProfiles";
    
    public static final String CONSTITUENT_INDIVIDUAL_ROLES = "constituentIndividualRoles";
    public static final String CONSTITUENT_ORGANIZATION_ROLES = "constituentOrganizationRoles";
    public static final String INDIVIDUAL_SPOUSE = "individual.spouse";
    public static final String INDIVIDUAL_BIRTH_DATE = "individual.birthDate";
    public static final String INDIVIDUAL_DECEASED = "individual.deceased";
    public static final String INDIVIDUAL_GENDER = "individual.gender";
    public static final String INDIVIDUAL_RACE = "individual.race";
    public static final String INDIVIDUAL_MILITARY = "individual.military";
    
    public static final String ORGANIZATION_WEBSITE = "organization.website";
    public static final String ORGANIZATION_TAX_ID = "organization.taxid";
    public static final String ORGANIZATION_ELIGIBILITY = "organization.eligibility";
    public static final String ORGANIZATION_MINIMUM_GIFT_MATCH = "organization.minimumGiftMatch";
    public static final String ORGANIZATION_MAXIMUM_GIFT_MATCH = "organization.maximumGiftMatch";
    public static final String ORGANIZATION_MATCHING = "organization.matching";
    public static final String ORGANIZATION_PERCENT_MATCH = "organization.percentMatch";
    public static final String ORGANIZATION_MAXIMUM_ANNUAL_LIMIT = "organization.maximumAnnualLimit";
    public static final String ORGANIZATION_TOTAL_EMPLOYEE_PER_YEAR = "organization.totalPerEmployeePerYear";
    public static final String ORGANIZATION_ELIGIBLE_FUNDS = "organization.eligibleFunds";
    public static final String ORGANIZATION_ADDITIONAL_ELIGIBLE_FUNDS = "organization.additional_eligibleFunds";
    public static final String ORGANIZATION_ELIGIBLE_ORGANIZATIONS = "organization.eligibleOrganizations";
    public static final String ORGANIZATION_ADDITIONAL_ELIGIBLE_ORGANIZATIONS = "organization.additional_eligibleOrganizations";
    public static final String ORGANIZATION_INELIGIBLE_ORGANIZATIONS = "organization.ineligibleOrganizations";
    public static final String ORGANIZATION_ADDITIONAL_INELIGIBLE_ORGANIZATIONS = "organization.additional_ineligibleOrganizations";
    public static final String ORGANIZATION_ONLINE_MATCHING_GIFT_FORM = "organization.onlineMatchingGiftForm";
    public static final String ORGANIZATION_PROGRAM_START_MONTH = "organization.programStartMonth";
    public static final String ORGANIZATION_PROGRAM_CONTACT = "organization.programContact";
    public static final String ORGANIZATION_OTHER_PROGRAM_CONTACT = "organization.other_programContact";
    public static final String ORGANIZATION_PROCEDURE_FOR_REQUESTING_MATCH = "organization.procedureForRequestingMatch";

    public static final String INDIVIDUAL_ORGANIZATIONS = "individual.organizations";
    public static final String INDIVIDUAL_EMPLOYMENT_TITLE = "individual.employmentTitle";
    public static final String INDIVIDUAL_HEAD_OF_HOUSEHOLD = "individual.headofhousehold";
    public static final String HEAD_OF_HOUSEHOLD_HOUSEHOLD_MEMBERS = "headofhousehold.householdMembers";
    public static final String INDIVIDUAL_ACCOUNT_MANAGER_FOR = "individual.accountManagerFor";
    public static final String INDIVIDUAL_PRIMARY_CONTACT_FOR = "individual.primaryContactFor";
    public static final String INDIVIDUAL_BILLING_CONTACT_FOR = "individual.billingContactFor";
    public static final String INDIVIDUAL_SALES_CONTACT_FOR = "individual.salesContactFor";
    public static final String INDIVIDUAL_PUBLIC_RELATIONS_CONTACT_FOR = "individual.publicRelationsContactFor";
    public static final String INDIVIDUAL_PROGRAM_CONTACT_FOR = "individual.programContactFor";
    public static final String INDIVIDUAL_PARENTS = "individual.parents";
    public static final String INDIVIDUAL_CHILDREN = "individual.children";
    public static final String INDIVIDUAL_SIBLINGS = "individual.siblings";
    public static final String INDIVIDUAL_FRIENDS = "individual.friends";
    
    public static final String ORGANIZATION_EMPLOYEES = "organization.employees";
    public static final String ORGANIZATION_PARENT = "organization.parent";
    public static final String ORGANIZATION_SUBSIDIARY_LIST = "organization.subsidiaryList";
    public static final String ORGANIZATION_PRIMARY_CONTACTS = "organization.primaryContacts";
    public static final String ORGANIZATION_BILLING_CONTACTS = "organization.billingContacts";
    public static final String ORGANIZATION_SALES_CONTACTS = "organization.salesContacts";
    public static final String ORGANIZATION_PUBLIC_RELATIONS_CONTACTS = "organization.publicRelationsContacts";
    
    private Site site;
    private String constituentType = INDIVIDUAL;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffix;
    private String recognitionName;
    private String organizationName;
    private String legalName;
    private String ncaisCode;
    private String maritalStatus = "Unknown";
    private String preferredPhoneType;
    private String loginId;
    private String accountNumber;
    private Boolean byPassDuplicateDetection = false;
    
    private List<Gift> gifts;
    private List<RecurringGift> recurringGifts;
    private List<Pledge> pledges;
    
    public Person() { }

    public Person(Long id, Site site) { 
        this();
        this.id = id;
        this.site = site;
    }
    
    @Override
    public String toString() {
        return getDisplayValue();
    }

    @Override
    // TODO: remove this overridden method when this class is renamed to "Constituent"
    public String getType() {
        return "person";
    }

    public String getDisplayValue() {
        if (isOrganization()) {
            return organizationName;
        } else {
            return createName(true);
        }
    }

    public String getFirstLast() {
        StringBuilder sb = new StringBuilder();
        if (isOrganization()) {
            sb.append(organizationName);
        } 
        else {
            if (firstName != null) {
                sb.append(firstName).append(" ");
            }
            if (lastName != null) {
                sb.append(lastName);
            }
        }
        return sb.toString();
    }

    public String getFullName() {
        if (isOrganization()) {
            return organizationName;
        } 
        else {
            return createName(false);
        }
    }

    public String createName(boolean lastFirst) {
        StringBuilder sb = new StringBuilder();
        if (lastFirst) {
            if (lastName != null) {
                sb.append(lastName).append(", ");
            }
        }
        sb.append(firstName == null ? "" : firstName);
        if (middleName != null && middleName.length() > 0) {
            sb.append(" ").append(middleName);
        }
        if (!lastFirst) {
            sb.append(" ").append(lastName == null ? "" : lastName);
        }
        if (suffix != null && suffix.length() > 0) {
            sb.append(", ").append(suffix);
        }
        return sb.toString();
    }

    public String getEntityName() {
        return "person";
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredPhoneType() {
        return preferredPhoneType;
    }

    public void setPreferredPhoneType(String preferredPhoneType) {
        this.preferredPhoneType = preferredPhoneType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public List<RecurringGift> getRecurringGifts() {
        return recurringGifts;
    }

    public void setRecurringGifts(List<RecurringGift> recurringGifts) {
        this.recurringGifts = recurringGifts;
    }

    public List<Pledge> getPledges() {
        return pledges;
    }

    public void setPledges(List<Pledge> pledges) {
        this.pledges = pledges;
    }

    public boolean isMajorDonor() {
        return isSpecificDonor("majorDonor");
    }

    public boolean isLapsedDonor() {
        return isSpecificDonor("lapsedDonor");
    }

    private boolean isSpecificDonor(String donorName) {
        boolean isSpecificDonor = false;
        String donorProfiles = getCustomFieldValue(DONOR_PROFILES);
        if (donorProfiles != null && donorProfiles.indexOf(donorName) > -1) {
            isSpecificDonor = true;
        }
        return isSpecificDonor;
    }

    public void setNcaisCode(String ncaisCode) {
        this.ncaisCode = ncaisCode;
    }

    public String getNcaisCode() {
        return ncaisCode;
    }

    public void setRecognitionName(String recognitionName) {
        this.recognitionName = recognitionName;
    }

    public String getRecognitionName() {
        return recognitionName;
    }

    public void setConstituentType(String constituentType) {
        this.constituentType = constituentType;
    }

    public String getConstituentType() {
        if (constituentType == null) {
            return INDIVIDUAL;
        }
        return constituentType;
    }

    public void setConstituentIndividualRoles(String constituentIndividualRoles) {
    	setCustomFieldValue(CONSTITUENT_INDIVIDUAL_ROLES, constituentIndividualRoles);
    }

    public String getConstituentIndividualRoles() {
        String result = getCustomFieldValue(CONSTITUENT_INDIVIDUAL_ROLES);
        if (result == null) {
            return "";
        } else {
            return result;
        }
    }

    public void setConstituentOrganizationRoles(String constituentOrganizationRoles) {
    	setCustomFieldValue(CONSTITUENT_ORGANIZATION_ROLES, constituentOrganizationRoles);
    }

    public String getConstituentOrganizationRoles() {
    	String result = getCustomFieldValue(CONSTITUENT_ORGANIZATION_ROLES);
        if (result == null) {
            return "";
        } else {
            return result;
        }
    }

    public void removeConstituentIndividualRoles(String role) {
   	String existingValue = getConstituentIndividualRoles();
    	
    	//
    	// if the custom field value does not contain the value
    	// we are removing simply return
    	if (existingValue.contains(role) == false) {
            return;
        }
    	
    	//
    	// if the existing value is equal to the value we are removing
    	// then set the field value to an empty string (remove it)
    	if (existingValue.contains(",") == false) {
    		if (existingValue.compareTo(role) == 0) {
                setConstituentIndividualRoles("");
            }
    	} else {
    		//
    		// if the existing value is a comma separated list of values
    		// then find the value we are removing in the string and remove it
    		// then reset the field value
    		String[] values = existingValue.split(",");
    		
    		StringBuilder sb = new StringBuilder();
    		for (int i = 0; i < values.length; i++) {
    			if (values[i].equals(role) == false) {
                    sb.append(values[i]);
                }
    			
    			if (i != (values.length - 1)) {
                    sb.append(",");
                }
    		}
    		
    		setConstituentIndividualRoles(sb.toString());
    	}
    }
    
    public void addConstituentOrganizationRoles(String role) {
    	String existingValue = getConstituentOrganizationRoles();
        if (existingValue == null) {
            setConstituentOrganizationRoles(role);
        }
        else {
            setConstituentOrganizationRoles(existingValue + "," + role); 
        }
    	
    }
    public void addConstituentIndividualRoles(String role) {
    	String existingValue = getConstituentIndividualRoles();
        if (existingValue == null) {
            setConstituentIndividualRoles(role);
        }
        else {
            setConstituentIndividualRoles(existingValue + "," + role); 
        }
    }
    
    public void addConstituentRole(String constituentRole) {
        if (constituentRole == null || constituentRole.equals(ORGANIZATION) || constituentRole.equals(INDIVIDUAL)) {
            return;
        }
        if (isOrganization()) {
            setConstituentOrganizationRoles(addToList(getConstituentOrganizationRoles(), constituentRole));
        } 
        else {
            setConstituentIndividualRoles(addToList(getConstituentIndividualRoles(), constituentRole));
        }
    }

    private String addToList(String list, String s) {
        if (list == null) {
            list = "";
        }
        if (!list.contains(s)) {
            if (list.length() > 0) {
                list += ",";
            }
            list += s;
        }
        return list;
    }

    public void setConstituentAttributes(String constituentAttributes) {
        // noop - read only field for Spring MVC
    }

    public String getConstituentAttributes() {
        String constituentAttributes;
        if (isOrganization()) {
            constituentAttributes = ORGANIZATION;
            String constituentOrganizationRoles = this.getConstituentOrganizationRoles();
            if (constituentOrganizationRoles != null && constituentOrganizationRoles.length() > 0) {
                constituentAttributes = constituentAttributes + "," + constituentOrganizationRoles;
            }
        } 
        else {
            constituentAttributes = INDIVIDUAL;
            String constituentIndividualRoles = this.getConstituentIndividualRoles();
            if (constituentIndividualRoles != null && constituentIndividualRoles.length() > 0) {
                constituentAttributes = constituentAttributes + "," + constituentIndividualRoles;
            }
        }
        return constituentAttributes;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public boolean isOrganization() {
        return ORGANIZATION.equals(getConstituentType());
    }

    public boolean isIndividual() {
        return INDIVIDUAL.equals(getConstituentType());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    @Override
    public String getAuditShortDesc() {
    	return getFullName();
    }


    @Override
    public void setDefaults() {
        super.setDefaults();
        if (isOrganization()) {
            setDefaultCustomFieldValue(ORGANIZATION_ELIGIBILITY, StringConstants.UNKNOWN_LOWER_CASE);
        }
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (isOrganization()) {
            clearIndividual();
            setConstituentOrganizationRoles(StringUtils.trimToEmpty(getConstituentOrganizationRoles()).toLowerCase());
            if (StringUtils.isBlank(getLegalName())) {
                setLegalName(getOrganizationName());
            }
        }
        else if (isIndividual()) {
            clearOrganization();
            setConstituentIndividualRoles(StringUtils.trimToEmpty(getConstituentIndividualRoles()).toLowerCase());
            if (StringUtils.isBlank(getCustomFieldValue(HEAD_OF_HOUSEHOLD_SALUTATION))) {
                setCustomFieldValue(HEAD_OF_HOUSEHOLD_SALUTATION, getFirstLast());
            }
        }
        if (StringUtils.isBlank(getRecognitionName())) {
            if (isIndividual()) { 
                setRecognitionName(createName(false));
            }
            else if (isOrganization()) {
                setRecognitionName(getOrganizationName());
            }
        }
        if (StringUtils.isBlank(getCustomFieldValue(FORMAL_SALUTATION))) {
            if (isOrganization()) {
                setCustomFieldValue(FORMAL_SALUTATION, legalName);
            }
            else if (isIndividual()) {
                StringBuilder sb = new StringBuilder();
                if (StringUtils.isBlank(title) == false) {
                    sb.append(title).append(" ");
                }
                sb.append(getFirstLast());
                setCustomFieldValue(FORMAL_SALUTATION, sb.toString());
            }
        }
        if (StringUtils.isBlank(getCustomFieldValue(INFORMAL_SALUTATION))) {
            if (isOrganization()) {
                setCustomFieldValue(INFORMAL_SALUTATION, organizationName);
            }
            else if (isIndividual()) {
                setCustomFieldValue(INFORMAL_SALUTATION, getFirstLast());
            }
        }
        setConstituentType(StringUtils.trimToEmpty(getConstituentType()).toLowerCase());
    }
    
    public void clearIndividual() {
        setTitle(null);
        setSuffix(null);
        setFirstName(null);
        setLastName(null);
        setMiddleName(null);
        setMaritalStatus(null);
        removeCustomField(CONSTITUENT_INDIVIDUAL_ROLES);
        removeCustomField(HEAD_OF_HOUSEHOLD_SALUTATION);
        removeCustomField(INDIVIDUAL_BIRTH_DATE);
        removeCustomField(INDIVIDUAL_DECEASED);
        removeCustomField(INDIVIDUAL_GENDER);
        removeCustomField(INDIVIDUAL_MILITARY);
        removeCustomField(INDIVIDUAL_RACE);
        removeCustomField(INDIVIDUAL_SPOUSE);
        
        removeCustomField(INDIVIDUAL_ORGANIZATIONS);
        removeCustomField(INDIVIDUAL_EMPLOYMENT_TITLE);
        removeCustomField(INDIVIDUAL_HEAD_OF_HOUSEHOLD);
        removeCustomField(HEAD_OF_HOUSEHOLD_HOUSEHOLD_MEMBERS);
        removeCustomField(INDIVIDUAL_ACCOUNT_MANAGER_FOR);
        removeCustomField(INDIVIDUAL_PRIMARY_CONTACT_FOR);
        removeCustomField(INDIVIDUAL_BILLING_CONTACT_FOR);
        removeCustomField(INDIVIDUAL_SALES_CONTACT_FOR);
        removeCustomField(INDIVIDUAL_PUBLIC_RELATIONS_CONTACT_FOR);
        removeCustomField(INDIVIDUAL_PROGRAM_CONTACT_FOR);
        removeCustomField(INDIVIDUAL_PARENTS);
        removeCustomField(INDIVIDUAL_CHILDREN);
        removeCustomField(INDIVIDUAL_SIBLINGS);
        removeCustomField(INDIVIDUAL_FRIENDS);
    }
    
    public void clearOrganization() {
        setOrganizationName(null);
        setLegalName(null);
        setNcaisCode(null);
        removeCustomField(CONSTITUENT_ORGANIZATION_ROLES);
        removeCustomField(ORGANIZATION_ADDITIONAL_ELIGIBLE_FUNDS);
        removeCustomField(ORGANIZATION_ADDITIONAL_ELIGIBLE_ORGANIZATIONS);
        removeCustomField(ORGANIZATION_ADDITIONAL_INELIGIBLE_ORGANIZATIONS);
        removeCustomField(ORGANIZATION_ELIGIBILITY);
        removeCustomField(ORGANIZATION_ELIGIBLE_FUNDS);
        removeCustomField(ORGANIZATION_ELIGIBLE_ORGANIZATIONS);
        removeCustomField(ORGANIZATION_INELIGIBLE_ORGANIZATIONS);
        removeCustomField(ORGANIZATION_MATCHING);
        removeCustomField(ORGANIZATION_MAXIMUM_ANNUAL_LIMIT);
        removeCustomField(ORGANIZATION_MAXIMUM_GIFT_MATCH);
        removeCustomField(ORGANIZATION_MINIMUM_GIFT_MATCH);
        removeCustomField(ORGANIZATION_ONLINE_MATCHING_GIFT_FORM);
        removeCustomField(ORGANIZATION_OTHER_PROGRAM_CONTACT);
        removeCustomField(ORGANIZATION_PERCENT_MATCH);
        removeCustomField(ORGANIZATION_PROCEDURE_FOR_REQUESTING_MATCH);
        removeCustomField(ORGANIZATION_PROGRAM_CONTACT);
        removeCustomField(ORGANIZATION_TAX_ID);
        removeCustomField(ORGANIZATION_TOTAL_EMPLOYEE_PER_YEAR);
        removeCustomField(ORGANIZATION_WEBSITE);
        removeCustomField(ORGANIZATION_PROGRAM_START_MONTH);

        removeCustomField(ORGANIZATION_EMPLOYEES);
        removeCustomField(ORGANIZATION_PARENT);
        removeCustomField(ORGANIZATION_SUBSIDIARY_LIST);
        removeCustomField(ORGANIZATION_PRIMARY_CONTACTS);
        removeCustomField(ORGANIZATION_BILLING_CONTACTS);
        removeCustomField(ORGANIZATION_SALES_CONTACTS);
        removeCustomField(ORGANIZATION_PUBLIC_RELATIONS_CONTACTS);
    }

	public Boolean getByPassDuplicateDetection() {
		return byPassDuplicateDetection;
	}

	public void setByPassDuplicateDetection(Boolean byPassDuplicateDetection) {
		this.byPassDuplicateDetection = byPassDuplicateDetection;
	}
}
