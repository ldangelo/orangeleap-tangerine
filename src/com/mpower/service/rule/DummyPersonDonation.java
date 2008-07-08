package com.mpower.service.rule;


public class DummyPersonDonation {
	private double donationAmount;
	private int number;
	private String timeUnit;
	private Long personId;


	public DummyPersonDonation() {
		super();
	}

	public DummyPersonDonation(int number, String timeUnit) {
		super();
		this.timeUnit = timeUnit;
		this.number = number;
	}

	public DummyPersonDonation(double donationAmount, Long personId) {
		super();
		this.donationAmount = donationAmount;
		this.personId = personId;
	}
	public double getDonationAmount() {
		return donationAmount;
	}
	public void setDonationAmount(double donationAmount) {
		this.donationAmount = donationAmount;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnits(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
}
