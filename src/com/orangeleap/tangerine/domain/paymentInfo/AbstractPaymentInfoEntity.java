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
import com.orangeleap.tangerine.domain.AddressAware;
import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.domain.MutableGrid;
import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.PaymentSourceAware;
import com.orangeleap.tangerine.domain.PhoneAware;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.communication.Phone;
import org.springframework.core.style.ToStringCreator;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(namespace = "http://www.orangeleap.com/orangeleap/schemas")
@SuppressWarnings({"unchecked"})
public abstract class AbstractPaymentInfoEntity extends AbstractCustomizableEntity implements PaymentSourceAware, AddressAware, PhoneAware, MutableGrid {
    private static final long serialVersionUID = 1L;

    protected String comments;
    protected String currencyCode;
    protected String paymentType;
    protected String checkNumber;
    protected Long constituentId; // This variable is used by webservices instead of passing the entire constituent object
    protected Constituent constituent;

	protected Address address;
	protected Phone phone;
	protected PaymentSource paymentSource;

	protected List<DistributionLine> distributionLines = new ArrayList<DistributionLine>();

    public AbstractPaymentInfoEntity() {
        super();
    }

	public Constituent getConstituent() {
        return constituent;
    }

    public void setConstituent(Constituent constituent) {
        this.constituent = constituent;
        if (constituent != null) {
            if (paymentSource != null) {
                paymentSource.setConstituent(constituent);
            }
            constituentId = constituent.getId();
        }
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        DistributionLine line = new DistributionLine(constituent);
        line.setDefaults();
        lines.add(line);
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String getPaymentType() {
        return paymentType;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public List<DistributionLine> getDistributionLines() {
        return distributionLines;
    }

    public void setDistributionLines(List<DistributionLine> distributionLines) {
        this.distributionLines = distributionLines;
    }

	public void clearDistributionLines() {
		if (this.distributionLines != null) {
			this.distributionLines.clear();
		}
	}

	public void addDistributionLine(DistributionLine distributionLine) {
		if (this.distributionLines == null) {
			setDistributionLines(new ArrayList<DistributionLine>());
		}
		this.distributionLines.add(distributionLine);
	}

	@Override
	public void resetAddGridRows(int listSize, Constituent constituent) {
		clearDistributionLines();
		if (listSize == 0) {
			listSize = 1;
		}
		for (int i = 0; i < listSize; i++) {
			DistributionLine distributionLine = new DistributionLine(0L, constituent);
			addDistributionLine(distributionLine);
		}
	}

    @Override
    public PaymentSource getPaymentSource() {
        return paymentSource;
    }

    @Override
    public void setPaymentSource(PaymentSource paymentSource) {
        this.paymentSource = paymentSource;
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

    public Site getSite() {
        return constituent != null ? constituent.getSite() : null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append(super.toString()).append("paymentType", paymentType).append("currencyCode", currencyCode).
                append("checkNumber", checkNumber).append("comments", comments).
                toString();
    }

    public void setConstituentId(Long Id) {
        constituentId = id;
    }

    public Long getConstituentId() {
        return constituentId;
    }
}