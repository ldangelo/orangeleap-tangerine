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

package com.orangeleap.tangerine.controller.constituent;

import com.orangeleap.tangerine.domain.Constituent;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.util.OLLogger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import javax.annotation.Resource;
import java.beans.PropertyEditorSupport;

public class ConstituentEditor extends PropertyEditorSupport {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

    @Resource(name = "constituentService")
    private ConstituentService constituentService;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.trimToNull(text) == null) {
            return;
        }
        long constituentId = Long.valueOf(StringUtils.trimToNull(text));
        Constituent constituent = constituentService.readConstituentById(constituentId);
        setValue(constituent);
    }

    // public String getAsText() {
    //	if (this.getValue() instanceof String) {
    //	    return (String)this.getValue();
    //	}
    // 	return ("" + this.getValue());
    //     }
}
