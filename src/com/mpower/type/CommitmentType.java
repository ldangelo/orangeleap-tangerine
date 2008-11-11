package com.mpower.type;

public enum CommitmentType {
    RECURRING_GIFT("recurring gift"),
    PLEDGE("pledge"),
    MEMBERSHIP("membership");

    private String displayName;

    private CommitmentType(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return this.name();
    }

    public String getDisplayName() {
        return displayName;
    }
}