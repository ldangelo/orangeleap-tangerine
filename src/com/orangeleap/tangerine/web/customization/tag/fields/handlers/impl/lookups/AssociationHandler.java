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

package com.orangeleap.tangerine.web.customization.tag.fields.handlers.impl.lookups;

import com.orangeleap.tangerine.util.StringConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * User: alexlo
 * Date: Jul 9, 2009
 * Time: 1:44:23 PM
 */
public class AssociationHandler extends QueryLookupHandler {

	public AssociationHandler(ApplicationContext applicationContext) {
		super(applicationContext);
	}

	@Override
	protected String getSideCssClass(Object fieldValue) {
		String sideCss = StringConstants.EMPTY;
		if (fieldValue == null || !StringUtils.hasText(fieldValue.toString())) {
			sideCss = "noDisplay";
		}
		return sideCss;
	}

	@Override
	protected String getDeleteClickHandler() {
	    return "Lookup.deleteAssociation(this)";
	}

}
