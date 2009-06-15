package com.orangeleap.tangerine.service.relationship;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.orangeleap.tangerine.domain.Constituent;

public class ConstituentTreeNode {
	private Constituent constituent;
	private List<ConstituentTreeNode> children = new ArrayList<ConstituentTreeNode>();
	private int level;
	
	public ConstituentTreeNode() {
	}
	
	public ConstituentTreeNode(Constituent constituent, int level) {
		this.setConstituent(constituent);
		this.setLevel(level);
	}

	public void setConstituent(Constituent constituent) {
		this.constituent = constituent;
	}

	public Constituent getConstituent() {
		return constituent;
	}

	public void setChildren(List<ConstituentTreeNode> children) {
		this.children = children;
	}

	public List<ConstituentTreeNode> getChildren() {
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

		String sId = constituent == null ? "" : constituent.getId().toString();
		String sConstituent = constituent == null ? "" : constituent.getDisplayValue();
		String sLevel = "" + level;
		
		JSONObject json = new JSONObject();
		try {
			json.put("id", sId);
			json.put("name", sConstituent);
			json.put("level", sLevel);
		  
			for (ConstituentTreeNode pn : children) {
				json.append("zchildren", pn.toJSONObject());
			}
		} catch (JSONException e) {
		}
		
		return json;
		
	}

}
