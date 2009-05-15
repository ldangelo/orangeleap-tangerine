package com.orangeleap.tangerine.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.util.comparator.NaturalOrderBeanOrMapComparator;

import edu.emory.mathcs.backport.java.util.Collections;

@SuppressWarnings("serial")
public class TangerinePagedListHolder extends PagedListHolder {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public TangerinePagedListHolder(List<Person> constituentList, MutableSortDefinition sortDef) {
        super(constituentList, sortDef);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doSort(List source, SortDefinition sort) {
        NaturalOrderBeanOrMapComparator comparator = new NaturalOrderBeanOrMapComparator(sort);
        Collections.sort(source, comparator);
    }
}
