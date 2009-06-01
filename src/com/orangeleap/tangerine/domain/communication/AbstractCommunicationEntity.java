package com.orangeleap.tangerine.domain.communication;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlType;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Creatable;
import com.orangeleap.tangerine.domain.Inactivatible;
import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;
@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings("serial")
public abstract class AbstractCommunicationEntity extends AbstractCustomizableEntity implements Inactivatible, Creatable {

    protected Long personId;
    protected ActivationType activationStatus;
    protected boolean receiveCorrespondence = false;
    protected Date temporaryStartDate;
    protected Date temporaryEndDate;
    protected Date seasonalStartDate;
    protected Date seasonalEndDate;
    protected boolean inactive = false;
    protected boolean isPrimary = false;
    private boolean undeliverable = false;
    protected String comments;
    // only meaningful for Permanent emails, and indicates when date becomes effective (ex. they are moving the first of next month)
    protected Date effectiveDate = new java.util.Date();
    protected boolean userCreated = false;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public boolean isReceiveCorrespondence() {
        return receiveCorrespondence;
    }

    public void setReceiveCorrespondence(boolean receiveCorrespondence) {
        this.receiveCorrespondence = receiveCorrespondence;
    }

    public ActivationType getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(ActivationType activationStatusType) {
        this.activationStatus = activationStatusType;
    }

    public Date getTemporaryStartDate() {
        return temporaryStartDate;
    }

    public void setTemporaryStartDate(Date temporaryStartDate) {
        this.temporaryStartDate = temporaryStartDate;
    }

    public Date getTemporaryEndDate() {
        return temporaryEndDate;
    }

    public void setTemporaryEndDate(Date temporaryEndDate) {
        this.temporaryEndDate = temporaryEndDate;
    }

    public Date getSeasonalStartDate() {
        return seasonalStartDate;
    }

    public void setSeasonalStartDate(Date seasonalStartDate) {
        this.seasonalStartDate = seasonalStartDate;
    }

    public Date getSeasonalEndDate() {
        return seasonalEndDate;
    }

    public void setSeasonalEndDate(Date seasonalEndDate) {
        this.seasonalEndDate = seasonalEndDate;
    }

    @Override
    public boolean isInactive() {
        return inactive;
    }

    @Override
    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public boolean isUserCreated() {
        return userCreated;
    }

    @Override
    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }
    
    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    
	public void setUndeliverable(boolean undeliverable) {
		this.undeliverable = undeliverable;
	}

	public boolean isUndeliverable() {
		return undeliverable;
	}
    
    @Override
    public void setDefaults() {
        super.setDefaults();

        if (activationStatus == null) {
            setActivationStatus(ActivationType.permanent);
        }
        if (effectiveDate == null && ActivationType.permanent.equals(getActivationStatus())) {
            setEffectiveDate(Calendar.getInstance(Locale.getDefault()).getTime());
        }
    }

    @Override
    public void prePersist() {
        super.prePersist();
        
        if (activationStatus != null) {
            if (ActivationType.permanent.equals(getActivationStatus())) {
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
                
                if (effectiveDate == null) {
                    setEffectiveDate(Calendar.getInstance(Locale.getDefault()).getTime());
                }
            } 
            else if (ActivationType.seasonal.equals(getActivationStatus())) {
                setEffectiveDate(null);
                setTemporaryEndDate(null);
                setTemporaryStartDate(null);
            } 
            else if (ActivationType.temporary.equals(getActivationStatus())) {
                setEffectiveDate(null);
                setSeasonalEndDate(null);
                setSeasonalStartDate(null);
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("constituentId", personId).append("createDate", getCreateDate()).
            append("updateDate", getUpdateDate()).append("activationStatus", activationStatus).append("receiveCorrespondence", receiveCorrespondence).append("temporaryStartDate", temporaryStartDate).
            append("temporaryEndDate", temporaryEndDate).append("seasonalStartDate", seasonalStartDate).append("seasonalEndDate", seasonalEndDate).append("inactive", inactive).
            append("isPrimary", isPrimary).append("comments", comments).append("effectiveDate", effectiveDate).append("userCreated", userCreated).
            toString();
    }
    
    public abstract boolean isValid();
    
    public String getCommunicationType() {
    	// TODO - this is now multi-valued
    	return StringConstants.UNKNOWN_LOWER_CASE;
    }

}