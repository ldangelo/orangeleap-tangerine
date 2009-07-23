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

package com.orangeleap.tangerine.domain.paymentInfo;

import com.orangeleap.tangerine.domain.AbstractCustomizableEntity;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.list.UnmodifiableList;
import org.springframework.core.style.ToStringCreator;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings("unchecked")
public class GiftInKind extends AbstractCustomizableEntity {
    private static final long serialVersionUID = 1L;

    private BigDecimal fairMarketValue;
    private String currencyCode;
    private Date donationDate = new Date();
    private String motivationCode;
    private String other_motivationCode;
    private boolean anonymous = false;
    private String recognitionName;
    private Date transactionDate;

    private Long giftId;
    private Constituent constituent;

    /**
     * Form bean representation of the GiftInKindDetails
     */
    protected List<GiftInKindDetail> mutableDetails = LazyList.decorate(new ArrayList<GiftInKindDetail>(), new Factory() {
        public Object create() {
            return new GiftInKindDetail();
        }
    });

    /**
     * Domain object representation of the GiftInKindDetails
     */
    private List<GiftInKindDetail> details;

    /* Used by the form for cloning */
    protected final List<GiftInKindDetail> dummyDetails;

    public GiftInKind() {
        super();
        List<GiftInKindDetail> details = new ArrayList<GiftInKindDetail>();
        GiftInKindDetail detail = new GiftInKindDetail();
        detail.setDefaults();
        details.add(detail);
        dummyDetails = UnmodifiableList.decorate(details);
    }

    public GiftInKind(BigDecimal fairMarketValue, String currencyCode, Date donationDate, String motivationCode, String other_motivationCode,
                      boolean anonymous, String recognitionName) {
        this();
        this.fairMarketValue = fairMarketValue;
        this.currencyCode = currencyCode;
        this.donationDate = donationDate;
        this.motivationCode = motivationCode;
        this.other_motivationCode = other_motivationCode;
        this.anonymous = anonymous;
        this.recognitionName = recognitionName;
    }

    public BigDecimal getFairMarketValue() {
        return fairMarketValue;
    }

    public void setFairMarketValue(BigDecimal fairMarketValue) {
        this.fairMarketValue = fairMarketValue;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getMotivationCode() {
        return motivationCode;
    }

    public void setMotivationCode(String motivationCode) {
        this.motivationCode = motivationCode;
    }

    public String getOther_motivationCode() {
        return other_motivationCode;
    }

    public void setOther_motivationCode(String other_motivationCode) {
        this.other_motivationCode = other_motivationCode;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getRecognitionName() {
        return recognitionName;
    }

    public void setRecognitionName(String recognitionName) {
        this.recognitionName = recognitionName;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
    }

    public List<GiftInKindDetail> getDetails() {
        return details;
    }

    public void setDetails(List<GiftInKindDetail> details) {
        this.details = details;
    }

    public List<GiftInKindDetail> getMutableDetails() {
        return mutableDetails;
    }

    public void setMutableDetails(List<GiftInKindDetail> mutableDetails) {
        this.mutableDetails = mutableDetails;
    }

    public void removeEmptyMutableDetails() {
        Iterator<GiftInKindDetail> mutableDetailsIter = mutableDetails.iterator();
        details = new ArrayList<GiftInKindDetail>();
        while (mutableDetailsIter.hasNext()) {
            GiftInKindDetail detail = mutableDetailsIter.next();
            if (detail != null) {
                if (detail.isFieldEntered() == false) {
                    mutableDetailsIter.remove();
                } else {
                    details.add(detail);
                }
            }
        }
        if (details.isEmpty()) {
            details.add(new GiftInKindDetail());
        }
    }

    public void filterValidDetails() {
        details = new ArrayList<GiftInKindDetail>();
        Iterator<GiftInKindDetail> mutableDetailsIter = mutableDetails.iterator();
        while (mutableDetailsIter.hasNext()) {
            GiftInKindDetail detail = mutableDetailsIter.next();
            if (detail != null && detail.isValid()) {
                details.add(detail);
            }
        }
    }

    public List<GiftInKindDetail> getDummyDetails() {
        return dummyDetails;
    }

    @Override
    public void setDefaults() {
        super.setDefaults();
        if (recognitionName == null) {
            setRecognitionName(constituent.getRecognitionName());
        }
    }

    @Override
    public void prePersist() {
        super.prePersist();
        if (this.anonymous) {
            setRecognitionName(StringConstants.ANONYMOUS_CAMEL_CASE);
        }
    }

    public Site getSite() {
        return constituent != null ? constituent.getSite() : null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("fairMarketValue", fairMarketValue).append("currencyCode", currencyCode).
                append("donationDate", donationDate).
                append("motivationCode", motivationCode).append("other_motivationCode", other_motivationCode).append("anonymous", anonymous).append("recognitionName", recognitionName).
                append("constituent", constituent).append("giftId", giftId).append("transactionDate", transactionDate).
                toString();
    }
}
