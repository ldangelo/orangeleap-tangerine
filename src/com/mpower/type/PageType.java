package com.mpower.type;

public enum PageType {
	person("/person.htm"),
	personSearch("/personSearch.htm"),
	personSearchResults("/personSearchResults.htm"),
	gift("/gift.htm"),
	giftList("/giftList.htm"),
	giftSearch("/giftSearch.htm"),
	giftSearchResults("/giftSearchResults.htm"),
	giftView("/giftView.htm"),
	commitment("/commitment.htm"),
	commitmentList("/commitmentList.htm"),
	commitmentSearch("/commitmentSearch.htm"),
	commitmentSearchResults("/commitmentSearchResults.htm"),
	commitmentView("/commitmentView.htm"),
	paymentManager("/paymentManager.htm"),
	paymentManagerEdit("/paymentManagerEdit.htm"),
	paymentSource("/paymentSource.htm"),
	queryLookup("/queryLookup.htm");

	private String pageName;

	private PageType(String pageName) {
		this.pageName = pageName;
	}

	public String getName() {
		return this.name();
	}

	public String getPageName() {
		return pageName;
	}
}