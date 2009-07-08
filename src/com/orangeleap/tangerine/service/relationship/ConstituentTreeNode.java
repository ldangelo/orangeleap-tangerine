/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.service.relationship;

import com.orangeleap.tangerine.domain.Constituent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
