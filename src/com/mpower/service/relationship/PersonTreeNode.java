package com.mpower.service.relationship;

import java.util.ArrayList;
import java.util.List;

import com.mpower.domain.Person;
import com.mpower.service.exception.PersonValidationException;

public class PersonTreeNode {
	

	private Person person;
	private List<PersonTreeNode> children = new ArrayList<PersonTreeNode>();
	private int level;
	
	public PersonTreeNode() {
	}
	
	public PersonTreeNode(Person person, int level) throws PersonValidationException {
		this.setPerson(person);
		this.setLevel(level);
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public void setChildren(List<PersonTreeNode> children) {
		this.children = children;
	}

	public List<PersonTreeNode> getChildren() {
		return children;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

}
