package com.mpower.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpower.domain.model.communication.Address;
import com.mpower.domain.model.communication.Email;
import com.mpower.domain.model.communication.Phone;
import com.mpower.domain.model.paymentInfo.Commitment;
import com.mpower.domain.model.paymentInfo.Gift;
import com.mpower.util.StringConstants;

public class Person extends AbstractCustomizableEntity {// SiteAware, Customizable, Viewable, Serializable { // TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    public static final String INDIVIDUAL = "individual";
    public static final String ORGANIZATION = "organization";

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
    private String maritalStatus = StringConstants.UNKNOWN;
    private String preferredPhoneType;
    private List<Address> addresses;
    private List<Email> emails;
    private List<Phone> phones;
    private List<Gift> gifts;
    private List<Commitment> commitments;
    private boolean majorDonor = false;
    private boolean lapsedDonor = false;
    private String constituentIndividualRoles = StringConstants.EMPTY;
    private String constituentOrganizationRoles = StringConstants.EMPTY;
    private String loginId;
    private Date createDate;
    private Date updateDate;
    private final Map<String, List<Address>> addressMap = null;
    private final Map<String, List<Email>> emailMap = null;
    private final Map<String, List<Phone>> phoneMap = null;


    public Person() { }

    @Override
    public String toString() {
        return getDisplayValue();
    }

    public boolean isOrganization() {
        return getConstituentType().equals(ORGANIZATION);
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
            sb.append(lastName == null ? "" : lastName).append(", ");
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

    public Long getAccountNumber() {
        return id;
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

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<Address>> getAddressMap() {
        if (addressMap == null) {
            // TODO: put back for IBatis
            // addressMap = AddressMap.buildAddressMap(getAddresses(), this);
        }
        return addressMap;
    }

    public List<Email> getEmails() {
        if (emails == null) {
            emails = new ArrayList<Email>();
        }
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<Email>> getEmailMap() {
        if (emailMap == null) {
            // TODO: put back for IBatis
            // emailMap = EmailMap.buildEmailMap(getEmails(), this);
        }
        return emailMap;
    }

    public List<Phone> getPhones() {
        if (phones == null) {
            phones = new ArrayList<Phone>();
        }
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<Phone>> getPhoneMap() {
        if (phoneMap == null) {
            // TODO: put back for IBatis
            // phoneMap = PhoneMap.buildPhoneMap(getPhones(), this);
        }
        return phoneMap;
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

    public List<Commitment> getCommitments() {
        return commitments;
    }

    public void setCommitments(List<Commitment> commitments) {
        this.commitments = commitments;
    }

    public boolean isMajorDonor() {
        return majorDonor;
    }

    public void setMajorDonor(boolean majorDonor) {
        this.majorDonor = majorDonor;
    }

    public boolean isLapsedDonor() {
        return lapsedDonor;
    }

    public void setLapsedDonor(boolean lapsedDonor) {
        this.lapsedDonor = lapsedDonor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    // TODO: put back for IBatis
    // @Override
    public Person getPerson() {
        return this;
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
        this.constituentIndividualRoles = constituentIndividualRoles;
    }

    public String getConstituentIndividualRoles() {
        return constituentIndividualRoles;
    }

    public void setConstituentOrganizationRoles(String constituentOrganizationRoles) {
        this.constituentOrganizationRoles = constituentOrganizationRoles;
    }

    public String getConstituentOrganizationRoles() {
        return constituentOrganizationRoles;
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
            if (constituentOrganizationRoles != null && constituentOrganizationRoles.length() > 0) {
                constituentAttributes = constituentAttributes + "," + constituentOrganizationRoles;
            }
        } 
        else {
            constituentAttributes = INDIVIDUAL;
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
}
