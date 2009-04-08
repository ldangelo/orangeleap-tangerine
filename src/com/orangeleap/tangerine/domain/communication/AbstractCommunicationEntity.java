package com.orangeleap.tangerine.domain.communication;

import java.util.Date;

import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Inactivatible;
import com.orangeleap.tangerine.type.ActivationType;
import com.orangeleap.tangerine.util.StringConstants;

@SuppressWarnings("serial")
public abstract class AbstractCommunicationEntity extends AbstractCustomizableEntity implements Inactivatible {

    protected Long personId;
    protected ActivationType activationStatus;
    protected boolean receiveMail = false;
    protected Date temporaryStartDate;
    protected Date temporaryEndDate;
    protected Date seasonalStartDate;
    protected Date seasonalEndDate;
    protected boolean inactive = false;
    protected boolean isPrimary = false;
    protected String comments;
    // only meaningful for Permanent emails, and indicates when date becomes effective (ex. they are moving the first of next month)
    protected Date effectiveDate; 
    protected boolean userCreated = false;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public boolean isReceiveMail() {
        return receiveMail;
    }

    public void setReceiveMail(boolean receiveMail) {
        this.receiveMail = receiveMail;
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
        //TANGERINE-203, for a new, permanent contact, use Today as defaut
        if((id == null || id == 0) && effectiveDate  == null
                && ActivationType.permanent.equals(getActivationStatus())) {
            this.effectiveDate = new Date();
        }

        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }
    
    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
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
            append("updateDate", getUpdateDate()).append("activationStatus", activationStatus).append("receiveMail", receiveMail).append("temporaryStartDate", temporaryStartDate).
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