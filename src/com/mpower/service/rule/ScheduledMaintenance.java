package com.mpower.service.rule;

public class ScheduledMaintenance {

	private boolean analyzeLapsedDonors;

	public ScheduledMaintenance() {
		super();
	}

	public ScheduledMaintenance(boolean analyzeLapsedDonors) {
		super();
		this.analyzeLapsedDonors = analyzeLapsedDonors;
	}

	public boolean isLapsedDonor() {
		return analyzeLapsedDonors;
	}

	public void setLapsedDonor(boolean isLapsedDonor) {
		this.analyzeLapsedDonors = isLapsedDonor;
	}

}
