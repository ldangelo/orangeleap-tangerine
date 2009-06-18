package com.orangeleap.tangerine.util;

import java.util.List;

import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.SortDefinition;

import com.orangeleap.tangerine.util.comparator.NaturalOrderBeanOrMapComparator;

import edu.emory.mathcs.backport.java.util.Collections;

@SuppressWarnings("serial")
public class TangerinePagedListHolder extends PagedListHolder {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

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
}
