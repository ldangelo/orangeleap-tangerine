package com.orangeleap.tangerine.service.relationship;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.orangeleap.tangerine.domain.Person;

public class PersonTreeNode {
	private Person person;
	private List<PersonTreeNode> children = new ArrayList<PersonTreeNode>();
	private int level;
	
	public PersonTreeNode() {
	}
	
	public PersonTreeNode(Person person, int level) {
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
	
	public String toJSONString() {
		try {
			return toJSONObject().toString(3);
		} catch (JSONException e) {
			e.printStackTrace();
			return "{}";
		}
	}
	
	public JSONObject toJSONObject() {

		String sId = person == null ? "" : person.getId().toString();
		String sPerson = person == null ? "" : person.getDisplayValue();
		String sLevel = "" + level;
		
		JSONObject json = new JSONObject();
		try {
			json.put("id", sId);
			json.put("name", sPerson);
			json.put("level", sLevel);
		  
			for (PersonTreeNode pn : children) {
				json.append("zchildren", pn.toJSONObject());
			}
		} catch (JSONException e) {
		}
		
		return json;
		
	}

}
