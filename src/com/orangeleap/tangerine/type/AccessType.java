package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum AccessType {
    ALLOWED,
    DENIED,
    READ_ONLY,
    READ_WRITE
}