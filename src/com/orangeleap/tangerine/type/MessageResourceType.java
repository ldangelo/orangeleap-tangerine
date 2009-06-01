package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum MessageResourceType {
	TITLE,
	SECTION_HEADER,
	FIELD_LABEL,
	FIELD_HELP,
	FIELD_VALIDATION,
	PICKLIST_VALUE,
	CONTENT
}