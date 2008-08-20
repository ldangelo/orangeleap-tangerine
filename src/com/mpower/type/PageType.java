package com.mpower.type;

public enum PageType {
    person("/person.htm"),
    personSearch("/personSearch.htm"),
    personSearchResults("/personSearchResults.htm"),
    gift("/gift.htm"),
    giftList("/giftList.htm"),
    giftSearch("/giftSearch.htm"),
    giftSearchResults("/giftSearchResults.htm"),
    giftView("/giftView.htm");

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