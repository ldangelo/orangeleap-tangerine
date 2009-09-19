/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.domain;

import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Email;
import com.orangeleap.tangerine.domain.communication.Phone;
import com.orangeleap.tangerine.type.CommunicationHistoryType;
import com.orangeleap.tangerine.util.StringConstants;

import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/*
 * Communication history (including generated correspondence) for Constituent
 */

// recordedBy and assignedTo have been moved to custom fields
@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
public class CommunicationHistory extends AbstractCustomizableEntity implements AddressAware, PhoneAware, EmailAware {
    private static final long serialVersionUID = 1L;

    private Date recordDate;
    private CommunicationHistoryType communicationHistoryType = CommunicationHistoryType.MANUAL;
    private String entryType = "";
    private boolean systemGenerated = false;
    private String comments = StringConstants.EMPTY;
    private Constituent constituent;
    private Long constituentId; // this is used by SOAP API's so we don't have to embed an entire constituent
    private Long giftId;
    private Long pledgeId;
    private Long recurringGiftId;
    private Address address;
    private Phone phone;
    private Email email;

    public CommunicationHistory() { }

    public CommunicationHistory(Long constituentId) {
        this.constituentId = constituentId;
    }

    public CommunicationHistory(Constituent constituent) {
        this.constituent = constituent;
    }

    public Site getSite() {
        return getConstituent().getSite();
    }

    public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
        if (constituent != null) constituentId = constituent.getId();
    }

    public CommunicationHistoryType getCommunicationHistoryType() {
        return communicationHistoryType;
    }

    public void setCommunicationHistoryType(CommunicationHistoryType communicationHistoryType) {
        this.communicationHistoryType = communicationHistoryType;
    }

    public boolean isSystemGenerated() {
        return systemGenerated;
    }

    public void setSystemGenerated(boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
    }

    public Long getRecurringGiftId() {
        return recurringGiftId;
    }

    public void setRecurringGiftId(Long recurringGiftId) {
        this.recurringGiftId = recurringGiftId;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getEntryType() {
        return entryType;
    }

	@Override
    public Address getAddress() {
        return address;
    }

	@Override
    public void setAddress(Address address) {
        this.address = address;
    }

	@Override
    public Phone getPhone() {
        return phone;
    }

	@Override
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

	@Override
    public Email getEmail() {
        return email;
    }

	@Override
    public void setEmail(Email email) {
        this.email = email;
    }

	public Long getConstituentId() {
	    return constituentId;
	}

	public void setConstituentId(Long id) {
	    constituentId = id;
	}
}
