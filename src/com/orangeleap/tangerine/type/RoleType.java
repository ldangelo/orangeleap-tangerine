package com.orangeleap.tangerine.type;

import javax.xml.bind.annotation.XmlType;

@XmlType (namespace="http://www.orangeleap.com/orangeleap/schemas")
public enum RoleType {
	ROLE_SUPER_ADMIN(6000),
	ROLE_ADMIN(5000),
	ROLE_SUPER_MANAGER(4000),
	ROLE_MANAGER(3000),
	ROLE_SUPER_USER(2000),
	ROLE_USER(1000);

	private Integer roleRank;

    private RoleType(Integer roleRank) {
        this.roleRank = roleRank;
    }

    public String getName(){
        return this.name();
    }

    public Integer getRoleRank(){
        return roleRank;
    }
}