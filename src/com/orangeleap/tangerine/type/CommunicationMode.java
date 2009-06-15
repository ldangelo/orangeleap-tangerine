package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum CommunicationMode {
    UNSPECIFIED,
    PHONE,
    EMAIL,
    LETTER,
    IN_CONSTITUENT
}