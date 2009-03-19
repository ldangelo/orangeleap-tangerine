package com.orangeleap.tangerine.domain.communication;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.core.style.ToStringCreator;

import com.orangeleap.tangerine.type.ActivationType;

public class Phone extends AbstractCommunicationEntity { // SiteAware, ConstituentInfo TODO: put back for IBatis

    private static final long serialVersionUID = 1L;

    private String number;
    private String provider;
    private String sms;

    public Phone() { }

    public Phone(Long personId) {
        this.personId = personId;
        this.activationStatus = ActivationType.permanent;
    }
    
    public Phone(Long personId, String number) {
        this(personId);
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    /**
     * Check if this is a dummy object; This is not a dummy object all required fields (number) are populated
     * @return true if this Address has all required fields populated
     */
    // @Override
    // TODO: put back for IBatis
    @Override
    public boolean isValid() {
        return (org.springframework.util.StringUtils.hasText(number));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Phone)) {
            return false;
        }
        Phone p = (Phone) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(getPersonId(), p.getPersonId()).append(activationStatus, p.getActivationStatus()).append(number, p.getNumber()).append(sms, p.getSms());
        return eb.isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getPersonId()).append(activationStatus).append(number).append(sms);
        return hcb.hashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("number", number).append("provider", provider).append("sms", sms).toString();
    }

}
