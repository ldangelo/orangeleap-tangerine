package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum PageType {
    constituent("/constituent.htm"),
    constituentSearch("/constituentSearch.htm"),
    constituentSearchResults("/constituentSearchResults.htm"),
    gift("/gift.htm"),
    giftList("/giftList.htm"),
    giftSearch("/giftSearch.htm"),
    giftSearchResults("/giftSearchResults.htm"),
    giftView("/giftView.htm"),
    adjustedGift("/giftAdjustment.htm"),
    adjustedGiftView("/giftAdjustmentView.htm"),
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
    picklistItems("/picklistItems.htm"),
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
    giftInKind("/giftInKind.htm"),
    giftInKindList("/giftInKindList.htm"),
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
    communicationHistoryView("/communicationHistoryView.htm"),
    constituentcustomfieldrelationship("/relationshipCustomize.htm"),
    fieldRelationshipCustomize("/fieldRelationshipCustomize.htm"),
    audit("/audit.htm"),
    siteAudit("/siteAudit.htm"),
    customField("/customField.htm"),
    siteSettings("/siteSettings.htm"),
    postbatch("/postbatch.htm"),
    logView("/logView.htm")
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