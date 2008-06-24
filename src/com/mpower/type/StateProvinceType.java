package com.mpower.type;


public enum StateProvinceType {
	//"US"
	ALABAMA("US", "AL"),
	ALASKA("US", "AK"),
	ARIZONA("US", "AZ"),
	ARKANSAS("US", "AR"),
	CALIFORNIA("US", "CA"),
	COLORADO("US", "CO"),
	CONNECTICUT("US", "CT"),
	DELAWARE("US", "DE"),
	DC("US", "DC"),
	FLORIDA("US", "FL"),
	GEORGIA("US", "GA"),
	HAWAII("US", "HI"),
	IDAHO("US", "ID"),
	ILLINOIS("US", "IL"),
	INDIANA("US", "IN"),
	IOWA("US", "IA"),
	KANSAS("US", "KS"),
	KENTUCKY("US", "KY"),
	LOUISIANA("US", "LA"),
	MAINE("US", "ME"),
	MARYLAND("US", "MD"),
	MASSACHUSETTS("US", "MA"),
	MICHIGAN("US", "MI"),
	MINNESOTA("US", "MN"),
	MISSISSIPPI("US", "MS"),
	MISSOURI("US", "MO"),
	MONTANNA("US", "MT"),
	NEBRASKA("US", "NE"),
	NEVADA("US", "NV"),
	NEW_HAMPSHIRE("US", "NH"),
	NEW_JERSEY("US", "NJ"),
	NEW_MEXICO("US", "NM"),
	NEW_YORK("US", "NY"),
	NORTH_CAROLINA("US", "NC"),
	NORTH_DAKOTA("US", "ND"),
	OHIO("US", "OH"),
	OKLAHOMA("US", "OK"),
	OREGON("US", "OR"),
	PENNSYLVANIA("US", "PA"),
	RHODE_ISLAND("US", "RI"),
	SOUTH_CAROLINA("US", "SC"),
	SOUTH_DAKOTA("US", "SD"),
	TENNESSEE("US", "TN"),
	TEXAS("US", "TX"),
	UTAH("US", "UT"),
	VERMONT("US", "VT"),
	VIRGINIA("US", "VA"),
	WASHINGTON("US", "WA"),
	WEST_VIRGINIA("US", "WV"),
	WISCONSIN("US", "WI"),
	WYOMING("US", "WY"),

	//"CA"
	ALBERTA("CA", "AB"),
	BRITISH_COLUMBIA("CA", "BC"),
	MANITOBA("CA", "MB"),
	NEW_BRUNSWICK("CA", "NB"),
	NEWFOUNDLAND("CA", "NL"),
	NORTHWEST_TERRITORIES("CA", "NT"),
	NOVA_SCOTIA("CA", "NS"),
	NUNAVUT("CA", "NU"),
	ONTARIO("CA", "ON"),
	PRINCE_EDWARD_ISLAND("CA", "PE"),
	QUEBEC("CA", "QC"),
	SASKATCHEWAN("CA", "SK"),
	YUKON("CA", "YT");

	private String country;
	private String stateProviceCode;

	private StateProvinceType(String country, String stateProviceCode) {
		this.country = country;
		this.stateProviceCode = stateProviceCode;
	}

	public String getName() {
		return this.name();
	}

	public String getCountry() {
		return this.country;
	}

	public String getStateProvinceCode() {
		return this.stateProviceCode;
	}

	public static StateProvinceType enumForStateProvinceCode(String country, String stateProvinceCode) {
		if (stateProvinceCode != null) {
			stateProvinceCode = stateProvinceCode.toUpperCase();
		} else {
			return null;
		}
		StateProvinceType[] types = StateProvinceType.values();
		for (int i = 0; i < types.length; i++) {
			if (types[i].stateProviceCode.equals(stateProvinceCode) && types[i].getCountry().equals(country)) {
				return types[i];
			}
		}
		return null;
	}

	public static StateProvinceType enumFor(String value) {
		try {
			return StateProvinceType.valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
