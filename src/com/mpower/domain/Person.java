package com.mpower.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.util.AddressMap;
import com.mpower.util.EmailMap;
import com.mpower.util.PersonCustomFieldMap;
import com.mpower.util.PhoneMap;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "PERSON")
public class Person implements SiteAware, Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "PERSON_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SITE_NAME")
    private Site site;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SPOUSE_NAME")
    private String spouseName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "SUFFIX")
    private String suffix;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus = "Unknown";

    @Column(name = "SPOUSE_FIRST_NAME")
    private String spouseFirstName;

    @OneToOne
    @JoinColumn(name = "SPOUSE", referencedColumnName = "PERSON_ID")
    private Person spouse;

    @Column(name = "ORGANIZATION_NAME")
    private String organizationName;

    @Column(name = "BIRTHDATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "ANNIVERSARY")
    @Temporal(TemporalType.DATE)
    private Date anniversary;

    @Column(name = "PREFERRED_PHONE_TYPE")
    private String preferredPhoneType;

    @OneToMany(mappedBy = "person")
    private List<Address> addresses;

    @OneToMany(mappedBy = "person")
    private List<Email> emails;

    @OneToMany(mappedBy = "person")
    private List<Phone> phones;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonCustomField> personCustomFields;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Gift> gifts;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Commitment> commitments;

    @Column(name = "MAJOR_DONOR")
    private boolean majorDonor = false;

    @Column(name = "LAPSED_DONOR")
    private boolean lapsedDonor = false;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    @Transient
    private Map<String, Address> addressMap = null;

    @Transient
    private Map<String, Email> emailMap = null;

    @Transient
    private Map<String, Phone> phoneMap = null;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    public String getDisplayValue() {
        return firstName + " " + lastName;
    }

    public String getEntityName() {
        return "person";
    }

    public Long getId() {
        return id;
    }

    public Long getAccountNumber() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getAnniversary() {
        return anniversary;
    }

    public void setAnniversary(Date anniversary) {
        this.anniversary = anniversary;
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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    public List<Address> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<Address>();
        }
        return addresses;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Address> getAddressMap() {
        if (addressMap == null) {
            addressMap = AddressMap.buildAddressMap(getAddresses(), this);
        }
        return addressMap;
    }

    public List<Email> getEmails() {
        if (emails == null) {
            emails = new ArrayList<Email>();
        }
        return emails;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Email> getEmailMap() {
        if (emailMap == null) {
            emailMap = EmailMap.buildEmailMap(getEmails(), this);
        }
        return emailMap;
    }

    public List<Phone> getPhones() {
        if (phones == null) {
            phones = new ArrayList<Phone>();
        }
        return phones;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Phone> getPhoneMap() {
        if (phoneMap == null) {
            phoneMap = PhoneMap.buildPhoneMap(getPhones(), this);
        }
        return phoneMap;
    }

    public List<PersonCustomField> getPersonCustomFields() {
        if (personCustomFields == null) {
            personCustomFields = new ArrayList<PersonCustomField>();
        }
        return personCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = PersonCustomFieldMap.buildCustomFieldMap(getPersonCustomFields(), this);
        }
        return customFieldMap;
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

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
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

    @Override
    public Map<String, String> getFieldLabelMap() {
        return fieldLabelMap;
    }

    @Override
    public void setFieldLabelMap(Map<String, String> fieldLabelMap) {
        this.fieldLabelMap = fieldLabelMap;
    }

    @Override
    public Map<String, Object> getFieldValueMap() {
        return fieldValueMap;
    }

    @Override
    public void setFieldValueMap(Map<String, Object> fieldValueMap) {
        this.fieldValueMap = fieldValueMap;
    }

    @Override
    public Person getPerson() {
        return this;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }
}
