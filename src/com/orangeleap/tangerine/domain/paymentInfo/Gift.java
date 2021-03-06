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

import com.orangeleap.tangerine.domain.Postable;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.StringConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.style.ToStringCreator;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public class Gift extends AbstractPaymentInfoEntity implements Postable {

	private static final long serialVersionUID = 1L;
	public static final String STATUS_PAID = "Paid";
	public static final String STATUS_NOT_PAID = "Not Paid";
	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_CANCELLED = "Cancelled";
	public static final String PAY_STATUS_APPROVED = "Approved";
	public static final String PAY_STATUS_AUTHORIZED = "Authorized";
	public static final String PAY_STATUS_DECLINED = "Declined";
	public static final String PAY_STATUS_ERROR = "Error";
	private String giftStatus;
	private GiftType giftType;
	private Long recurringGiftId;
	private BigDecimal amount;
	private BigDecimal adjustedAmount;
	private BigDecimal deductibleAmount;
	private Date donationDate = new Date();
	private boolean posted;
	private Date postedDate = new Date();
	private Date postmarkDate;
	private boolean deductible = false;
	private Date transactionDate;
	private String authCode = StringConstants.EMPTY;
	private String txRefNum;
	private String paymentStatus = StringConstants.EMPTY;
	private String paymentMessage;
	private String avsMessage;
	private GiftEntryType entryType = GiftEntryType.MANUAL;

	private List<Long> associatedPledgeIds;
	private List<Long> associatedRecurringGiftIds;

	private List<AdjustedGift> adjustedGifts;

	public Gift() {
		super();
	}

	public Gift(GiftInKind giftInKind) {
		this();
		setGiftForGiftInKind(giftInKind);
	}

	public Gift(RecurringGift recurringGift, BigDecimal giftAmount) {
		this();
		setGiftForRecurringGift(recurringGift, giftAmount);
	}



	public String getGiftStatus() {
		return giftStatus;
	}

	public void setGiftStatus(String giftStatus) {
		this.giftStatus = giftStatus;
	}

	public GiftType getGiftType() {
		return giftType;
	}

	public void setGiftType(GiftType giftType) {
		this.giftType = giftType;
	}

	public Long getRecurringGiftId() {
		return recurringGiftId;
	}

	public void setRecurringGiftId(Long recurringGiftId) {
		this.recurringGiftId = recurringGiftId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAdjustedAmount() {
		return adjustedAmount;
	}

	public void setAdjustedAmount(BigDecimal adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}

	public BigDecimal getDeductibleAmount() {
		return deductibleAmount;
	}

	public void setDeductibleAmount(BigDecimal deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Date getDonationDate() {
		return donationDate;
	}

	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

    @Override
	public boolean isPosted() {
		return posted;
	}

    @Override
	public void setPosted(boolean posted) {
		this.posted = posted;
	}

    @Override
	public Date getPostedDate() {
		return postedDate;
	}

    @Override
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Date getPostmarkDate() {
		return postmarkDate;
	}

	public void setPostmarkDate(Date postmarkDate) {
		this.postmarkDate = postmarkDate;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public boolean isDeductible() {
		return deductible;
	}

	public void setDeductible(boolean deductible) {
		this.deductible = deductible;
	}

	public GiftEntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(GiftEntryType entryType) {
		this.entryType = entryType;
	}

	public String getTxRefNum() {
		return txRefNum;
	}

	public void setTxRefNum(String txRefNum) {
		this.txRefNum = txRefNum;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String status) {
		this.paymentStatus = status;
	}

	public String getPaymentMessage() {
		return paymentMessage;
	}

	public void setPaymentMessage(String message) {
		this.paymentMessage = message;
	}

	public String getAvsMessage() {
		return avsMessage;
	}

	public void setAvsMessage(String avsMessage) {
		this.avsMessage = avsMessage;
	}

	public List<Long> getAssociatedPledgeIds() {
		return associatedPledgeIds;
	}

	public void setAssociatedPledgeIds(List<Long> associatedPledgeIds) {
		this.associatedPledgeIds = associatedPledgeIds;
	}

	public void addAssociatedPledgeId(Long pledgeId) {
		if (associatedPledgeIds == null) {
			setAssociatedPledgeIds(new ArrayList<Long>());
		}
		getAssociatedPledgeIds().add(pledgeId);
	}

	public List<Long> getAssociatedRecurringGiftIds() {
		return associatedRecurringGiftIds;
	}

	public void setAssociatedRecurringGiftIds(List<Long> associatedRecurringGiftIds) {
		this.associatedRecurringGiftIds = associatedRecurringGiftIds;
	}

	public void addAssociatedRecurringGiftId(Long recurringGiftId) {
		if (associatedRecurringGiftIds == null) {
			setAssociatedRecurringGiftIds(new ArrayList<Long>());
		}
		getAssociatedRecurringGiftIds().add(recurringGiftId);
	}

	public List<AdjustedGift> getAdjustedGifts() {
		return adjustedGifts;
	}

	public void setAdjustedGifts(List<AdjustedGift> adjustedGifts) {
		this.adjustedGifts = adjustedGifts;
	}

	public Boolean getIsAuthorized() {
		return (!StringUtils.trimToEmpty(authCode).equals("")
				&& StringUtils.equals(this.getPaymentStatus(),Gift.PAY_STATUS_AUTHORIZED)
				&& StringUtils.equals(this.getGiftStatus(),Gift.STATUS_PENDING));
	}

	public Boolean getIsCaptured() {
		return (!StringUtils.trimToEmpty(txRefNum).equals("")
				&& StringUtils.equals(this.getPaymentStatus(),Gift.PAY_STATUS_APPROVED)
				&& StringUtils.equals(this.getGiftStatus(),Gift.STATUS_PAID));
	}

	public Boolean getIsProcessed() {
		return (StringUtils.equals(this.getPaymentStatus(),Gift.PAY_STATUS_APPROVED)
				&& StringUtils.equals(this.getGiftStatus(),Gift.STATUS_PAID));

	}

	public Boolean getIsDeclined() {
		return StringUtils.equals(this.getPaymentStatus(),Gift.PAY_STATUS_DECLINED);
	}

    public Boolean getIsError() {
        return StringUtils.equals(this.getPaymentStatus(),Gift.PAY_STATUS_ERROR);
    }

    public void clearPaymentStatusInfo() {
    	setPaymentMessage(null);
    	setPaymentStatus(null);
    	setAvsMessage(null);
    	setAuthCode(null);
    	setTxRefNum(null);
    }

	public void setGiftForGiftInKind(GiftInKind giftInKind) {
		this.giftType = GiftType.GIFT_IN_KIND;
		this.currencyCode = giftInKind.getCurrencyCode();
		this.donationDate = giftInKind.getDonationDate();
		this.transactionDate = giftInKind.getTransactionDate();
		this.constituent = giftInKind.getConstituent();
	}

	public void setGiftForRecurringGift(RecurringGift recurringGift, BigDecimal giftAmount) {
		this.setConstituent(recurringGift.getConstituent());
		this.addAssociatedRecurringGiftId(recurringGift.getId());

		this.setComments(recurringGift.getComments());

		// The amounts may be changed in the recurring gift's payment schedule, so don't set using the default amount of the recurring gift.
		// Nightly recurring gifts processing looks up the next scheduled payment and uses that amount.
		this.setAmount(giftAmount);

		this.setPaymentType(recurringGift.getPaymentType());
		this.setGiftType(GiftType.MONETARY_GIFT);
		this.setEntryType(GiftEntryType.AUTO);
		this.setGiftStatus(Commitment.STATUS_PENDING);

		if (recurringGift.getDistributionLines() != null && this.getAmount() != null) {
			List<DistributionLine> list = new ArrayList<DistributionLine>();
			for (DistributionLine oldLine : recurringGift.getDistributionLines()) {
				DistributionLine newLine = new DistributionLine(oldLine, recurringGift);
				list.add(newLine);
			}
			this.setDistributionLines(list);
			normalizeAmounts();
		}

		this.setCurrencyCode(recurringGift.getCurrencyCode());

		this.setPaymentSource(recurringGift.getPaymentSource());
		this.setAddress(recurringGift.getAddress());
		this.setPhone(recurringGift.getPhone());
	}

	// Set amounts on distributions lines to total amount
	private void normalizeAmounts() {
		if (getDistributionLines() == null || getDistributionLines().size() == 0) return;
		BigDecimal remaining = this.getAmount();
		for (DistributionLine line : this.getDistributionLines()) {
			if (remaining.compareTo(BigDecimal.ZERO) <= 0) break;
			if (line.getPercentage() == null) line.setPercentage(BigDecimal.ZERO);
			BigDecimal lineAmt = line.getPercentage().divide(new BigDecimal("100")).multiply(this.getAmount()).setScale(2, BigDecimal.ROUND_HALF_EVEN);
			if (remaining.subtract(lineAmt).compareTo(BigDecimal.ZERO) < 0) {
				line.setAmount(remaining);
				remaining = BigDecimal.ZERO;
			} else {
				remaining = remaining.subtract(lineAmt);
				line.setAmount(lineAmt);
			}
		}
		if (remaining.compareTo(BigDecimal.ZERO) > 0) {
			DistributionLine lineone = getDistributionLines().get(0);
			lineone.setAmount(lineone.getAmount().add(remaining));
		}
	}

	public String getShortDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAmount()).append(", ");
		sb.append(getGiftStatus()).append(", ");
		sb.append(DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault()).format(donationDate));
		return sb.toString();
	}

	@Override
	public void prePersist() {
		super.prePersist();
		if (this.giftType == null) {
			this.giftType = GiftType.MONETARY_GIFT;
		}
		if (deductibleAmount == null) {
			deductibleAmount = amount;
		}
		if (!posted) {
			setPostedDate(null);
		}
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(super.toString()).append("giftStatus", giftStatus).append("giftType", giftType).append("recurringGiftId", recurringGiftId).append("amount", amount).append(super.toString()).append("deductibleAmount", deductibleAmount).append("transactionDate",
				transactionDate).append(super.toString()).append("donationDate", donationDate).append("postmarkDate", postmarkDate).append(super.toString()).append("authCode", authCode).append(super.toString()).append("deductible", deductible).append("txRefNum", txRefNum).append(super.toString())
				.append("paymentStatus", paymentStatus).append("paymentMessage", paymentMessage).append(super.toString()).append("entryType", entryType).toString();
	}

}
