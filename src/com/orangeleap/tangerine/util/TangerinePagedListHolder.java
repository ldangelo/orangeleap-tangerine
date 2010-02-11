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

package com.orangeleap.tangerine.util;

import com.orangeleap.tangerine.util.comparator.NaturalOrderBeanOrMapComparator;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.logging.Log;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;

import java.util.List;
import org.springframework.util.comparator.CompoundComparator;

@SuppressWarnings("serial")
public class TangerinePagedListHolder extends PagedListHolder {

    /**
     * Logger for this class and subclasses
     */
    protected final Log logger = OLLogger.getLog(getClass());

	public TangerinePagedListHolder() { }

	@SuppressWarnings("unchecked")
    public TangerinePagedListHolder(List list, MutableSortDefinition sortDef) {
        super(list, sortDef);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSort(List source, SortDefinition sort) {
        NaturalOrderBeanOrMapComparator comparator = new NaturalOrderBeanOrMapComparator(sort);
        Collections.sort(source, comparator);
    }

	public void doSort(List source, String... fields) {
		setSource(source);
		CompoundComparator compoundComparator = new CompoundComparator();
		for (String thisField : fields) {
			MutableSortDefinition definition = new MutableSortDefinition(thisField, true, Boolean.TRUE);
			NaturalOrderBeanOrMapComparator comparator = new NaturalOrderBeanOrMapComparator(definition);
			compoundComparator.addComparator(comparator, true);
		}
		Collections.sort(source, compoundComparator);
		setPage(0);
	}
}
