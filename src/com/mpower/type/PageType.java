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
    paymentManager("/paymentManager.htm"),
    paymentManagerEdit("/paymentManagerEdit.htm"),
    paymentSource("/paymentSource.htm"),
    addressSource("/addressSource.htm"),
    queryLookup("/queryLookup.htm"),
    addressManager("/addressManager.htm"),
    addressManagerEdit("/addressManagerEdit.htm"),
    address("/address.htm"),
    recurringGift("/recurringGift.htm"),
    recurringGiftList("/recurringGiftList.htm"),
    recurringGiftSearch("/recurringGiftSearch.htm"),
    recurringGiftSearchResults("/recurringGiftSearchResults.htm"),
    recurringGiftView("/recurringGiftView.htm"),
    pledge("/pledge.htm"),
    pledgeList("/pledgeList.htm"),
    pledgeSearch("/pledgeSearch.htm"),
    pledgeSearchResults("/pledgeSearchResults.htm"),
    pledgeView("/pledgeView.htm"),
    membership("/membership.htm"),
    membershipList("/membershipList.htm"),
    membershipSearch("/membershipSearch.htm"),
    membershipSearchResults("/membershipSearchResults.htm"),
    membershipView("/membershipView.htm"),
    emailManager("/emailManager.htm"),
    emailManagerEdit("/emailManagerEdit.htm"),
    email("/email.htm"),
    phoneManager("/phoneManager.htm"),
    phoneManagerEdit("/phoneManagerEdit.htm"),
    phone("/phone.htm"),
    importexport("/importexport.htm"),
    paymentHistory("/paymentHistory.htm"),
    communicationHistory("/communicationHistory.htm"),
    communicationHistoryList("/communicationHistoryList.htm"),
    communicationHistorySearch("/communicationHistorySearch.htm"),
    communicationHistorySearchResults("/communicationHistorySearchResults.htm"),
    communicationHistoryView("/communicationHistoryView.htm")
    ;

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