package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum LayoutType {
	ONE_COLUMN,
	ONE_COLUMN_HIDDEN,
	TWO_COLUMN,
	GRID,
	GRID_HIDDEN_ROW
}
