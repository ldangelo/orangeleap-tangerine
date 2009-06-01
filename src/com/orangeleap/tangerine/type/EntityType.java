package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum EntityType {
    common,
    person,
    donation,
    event,
    address,
    email,
    phone,
    gift,
    giftInKind,
    adjustedGift,
    code,
    picklist,
    recurringGift,
    pledge,
    paymentSource,
    paymentHistory,
    communicationHistory,
    selectedAddress,
    selectedEmail,
    selectedPhone,
    selectedPaymentSource,
    constituentCustomFieldRelationship
}