package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum GiftType {

    MONETARY_GIFT,
    GIFT_IN_KIND,
    ADJUSTMENT
    
}
