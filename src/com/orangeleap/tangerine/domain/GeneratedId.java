package com.orangeleap.tangerine.domain;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public interface GeneratedId {

    public Long getId();

    public void setId(Long id);

}
