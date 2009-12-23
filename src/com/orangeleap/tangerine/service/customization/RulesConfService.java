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

package com.orangeleap.tangerine.service.customization;

import java.util.List;
import java.util.Map;

import com.orangeleap.tangerine.domain.customization.rule.RuleSegmentType;
import com.orangeleap.tangerine.type.RuleEventNameType;


public interface RulesConfService {

	public List<RuleSegmentType> getAvailableRuleSegmentTypes(String ruleEventTypeName);

    public void generateRulesEventScript(RuleEventNameType rulesEventNameType, boolean testMode);

	public void fireRulesEvent(RuleEventNameType rulesEventNameType, boolean testMode, Map<String, Object> map);

	public void publishEventTypeRules(String ruleEventTypeNameId);

}
