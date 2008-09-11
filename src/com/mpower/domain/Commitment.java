package com.mpower.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.annotation.AutoPopulate;
import com.mpower.domain.listener.TemporalTimestampListener;
import com.mpower.type.CommitmentStatusType;
import com.mpower.util.CommitmentCustomFieldMap;

@Entity
@EntityListeners(value = { TemporalTimestampListener.class })
@Table(name = "COMMITMENT")
public class Commitment implements Customizable, Viewable, Serializable {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    @Transient
    private final Log logger = LogFactory.getLog(getClass());

    @Id
    @GeneratedValue
    @Column(name = "COMMITMENT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @OneToMany(mappedBy = "commitment")
    private List<Gift> gifts;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "AMOUNT_PER_GIFT")
    private BigDecimal amountPerGift;

    @Column(name = "NUMBER_OF_GIFTS")
    private Integer numberOfGifts;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @Column(name = "CREDIT_CARD_TYPE")
    private String creditCardType;

    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;

    @Column(name = "CREDIT_CARD_EXPIRATION_DATE")
    private Date creditCardExpirationDate;

    @Column(name = "CHECK_NUMBER")
    private Integer checkNumber;

    @Column(name = "ACH_TYPE")
    private String achType;

    @Column(name = "ACH_ROUTING_NUMBER")
    private String achRoutingNumber;

    @Column(name = "ACH_ACCOUNT_NUMBER")
    private String achAccountNumber;

    @Column(name = "COMMITMENT_CODE")
    private String commitmentCode;

    @Column(name = "PROJECT_CODE")
    private String projectCode;

    @Column(name = "MOTIVATION_CODE")
    private String motivationCode;

    @OneToMany(mappedBy = "commitment", cascade = CascadeType.ALL)
    private List<CommitmentCustomField> commitmentCustomFields;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "STATUS_TYPE")
    @Enumerated(EnumType.STRING)
    private CommitmentStatusType statusType;

    @Column(name = "CREATE_DATE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date createDate;

    @Column(name = "UPDATE_DATE", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @AutoPopulate
    private Date updateDate;

    @Column(name = "DEDUCTIBLE")
    private boolean deductible = false;

    @Column(name = "AUTO_PAY")
    private boolean autoPay = false;

    @Column(name = "NOTES")
    private String notes;

    @Transient
    private Integer creditCardExpirationMonth;

    @Transient
    private Integer creditCardExpirationYear;

    @Transient
    private Map<String, CustomField> customFieldMap = null;

    @Transient
    private Set<String> requiredFields = null;

    @Transient
    private Map<String, String> validationMap = null;

    @Transient
    private Map<String, String> fieldLabelMap = null;

    @Transient
    private Map<String, Object> fieldValueMap = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public BigDecimal getAmountPerGift() {
        return amountPerGift;
    }

    public void setAmountPerGift(BigDecimal amountPerGift) {
        this.amountPerGift = amountPerGift;
    }

    public Integer getNumberOfGifts() {
        return numberOfGifts;
    }

    public void setNumberOfGifts(Integer numberOfGifts) {
        this.numberOfGifts = numberOfGifts;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public Date getCreditCardExpirationDate() {
        return creditCardExpirationDate;
    }

    public void setCreditCardExpirationDate(Date creditCardExpirationDate) {
        this.creditCardExpirationDate = creditCardExpirationDate;
    }

    public Integer getCreditCardExpirationMonth() {
        if (creditCardExpirationDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditCardExpirationDate);
            creditCardExpirationMonth = calendar.get(Calendar.MONTH) + 1;
        }
        return creditCardExpirationMonth;
    }

    public void setCreditCardExpirationMonth(Integer creditCardExpirationMonth) {
        setExpirationDate(creditCardExpirationMonth, null);
        this.creditCardExpirationMonth = creditCardExpirationMonth;
    }

    public Integer getCreditCardExpirationYear() {
        if (creditCardExpirationDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(creditCardExpirationDate);
            creditCardExpirationYear = calendar.get(Calendar.YEAR);
        }
        return creditCardExpirationYear;
    }

    public void setCreditCardExpirationYear(Integer creditCardExpirationYear) {
        setExpirationDate(null, creditCardExpirationYear);
        this.creditCardExpirationYear = creditCardExpirationYear;
    }

    private void setExpirationDate(Integer month, Integer year) {
        Calendar calendar = Calendar.getInstance();
        if (creditCardExpirationDate != null) {
            calendar.setTime(creditCardExpirationDate);
        }
        if (month != null) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        if (year != null) {
            calendar.set(Calendar.YEAR, year);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR, calendar.getMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        creditCardExpirationDate = calendar.getTime();
        this.creditCardExpirationDate = calendar.getTime();
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getAchType() {
        return achType;
    }

    public void setAchType(String achType) {
        this.achType = achType;
    }

    public String getAchRoutingNumber() {
        return achRoutingNumber;
    }

    public void setAchRoutingNumber(String achRoutingNumber) {
        this.achRoutingNumber = achRoutingNumber;
    }

    public String getAchAccountNumber() {
        return achAccountNumber;
    }

    public void setAchAccountNumber(String achAccountNumber) {
        this.achAccountNumber = achAccountNumber;
    }

    public List<String> getExpirationMonthList() {
        List<String> monthList = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            String month = String.valueOf(i);
            if (month.length() == 1) {
                month = "0" + month;
            }
            monthList.add(month);
        }
        return monthList;
    }

    public List<String> getExpirationYearList() {
        List<String> yearList = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            yearList.add(String.valueOf(year + i));
        }
        return yearList;
    }

    public List<CommitmentCustomField> getCustomFields() {
        if (commitmentCustomFields == null) {
            commitmentCustomFields = new ArrayList<CommitmentCustomField>();
        }
        return commitmentCustomFields;
    }

    @SuppressWarnings("unchecked")
    public Map<String, CustomField> getCustomFieldMap() {
        if (customFieldMap == null) {
            customFieldMap = CommitmentCustomFieldMap.buildCustomFieldMap(getCustomFields(), this);
        }
        return customFieldMap;
    }

    @Override
    public Set<String> getRequiredFields() {
        return requiredFields;
    }

    @Override
    public void setRequiredFields(Set<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    @Override
    public Map<String, String> getValidationMap() {
        return validationMap;
    }

    @Override
    public void setValidationMap(Map<String, String> validationMap) {
        this.validationMap = validationMap;
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
    public Site getSite() {
        return person != null ? person.getSite() : null;
    }

    public String getCommitmentCode() {
        return commitmentCode;
    }

    public void setCommitmentCode(String commitmentCode) {
        this.commitmentCode = commitmentCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getMotivationCode() {
        return motivationCode;
    }

    public void setMotivationCode(String motivationCode) {
        this.motivationCode = motivationCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CommitmentStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(CommitmentStatusType statusType) {
        this.statusType = statusType;
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

    public boolean isDeductible() {
        return deductible;
    }

    public void setDeductible(boolean deductible) {
        this.deductible = deductible;
    }

    public boolean isAutoPay() {
        return autoPay;
    }

    public void setAutoPay(boolean autoPay) {
        this.autoPay = autoPay;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
