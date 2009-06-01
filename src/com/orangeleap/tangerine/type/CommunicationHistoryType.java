package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum CommunicationHistoryType {
    MANUAL,
    GIFT_RECEIPT,
    MEMBERSHIP_RECEIPT,
    PLEDGE_RECEIPT
}