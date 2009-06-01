package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum RelationshipType {
    ONE_TO_ONE,
    ONE_TO_MANY,
    MANY_TO_MANY
}