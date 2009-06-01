package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum ActivationType {
    // lower-case used for backwards compatibility
    permanent,
    temporary,
    seasonal,
    unknown
}