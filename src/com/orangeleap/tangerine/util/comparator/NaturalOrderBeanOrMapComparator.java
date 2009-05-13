package com.orangeleap.tangerine.util.comparator;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.support.SortDefinition;

/**
 * Performs 'natural order' (i.e. 1-address, 2-address, 10-address, 20-address) comparisons of String objects.  
 * For all other classes, use the default comparison ordering.   
 */
@SuppressWarnings("unchecked")
public class NaturalOrderBeanOrMapComparator implements Comparator {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    protected SortDefinition sortDefinition;

    public NaturalOrderBeanOrMapComparator(SortDefinition sortDefinition) {
        setSortDefinition(sortDefinition);
    }

    public SortDefinition getSortDefinition() {
        return sortDefinition;
    }

    public void setSortDefinition(SortDefinition sortDefinition) {
        this.sortDefinition = sortDefinition;
    }
    
    char charAt(String s, int i) {
        if (i >= s.length()) {
            return 0;
        }
        else {
            return s.charAt(i);
        }
    }
    
    int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;

        // The longest run of digits wins.  That aside, the greatest
        // value wins, but we can't know that it will until we've scanned
        // both numbers to know that they have the same magnitude, so we
        // remember it in BIAS.
        for (; ; ia++, ib++) {
            char ca = charAt(a, ia);
            char cb = charAt(b, ib);
            if (!Character.isDigit(ca)
                 && !Character.isDigit(cb)) {
                return bias;
            }
            else if (!Character.isDigit(ca)) {
                return -1;
            }
            else if (!Character.isDigit(cb)) {
                return +1;
            }
            else if (ca < cb) {
                if (bias == 0) {
                    bias = -1;
                }
            }
            else if (ca > cb) {
                if (bias == 0) {
                    bias = +1;
                }
            }
            else if (ca == 0 && cb == 0) {
                return bias;
            }
        }
    }

    protected int compareNulls(Object o1, Object o2) {
        Object v1 = getValue(o1);
        Object v2 = getValue(o2);
        if (v1 != null && v2 == null) {
            return -1;
        }
        else if (v1 == null && v2 != null) {
            return 1;
        }
        else if (v1 == null && v2 == null) {
            return 0;
        }
        return 10;
    }

    protected Object getValue(Object o) {
        Object v1 = getPropertyValue(o);
        if (this.sortDefinition.isIgnoreCase() && (v1 instanceof String)) {
            v1 = ((String)v1).toLowerCase();
        }
        return v1;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int returnVal = compareNulls(o1, o2);
        if (returnVal != 10) {
            return returnVal;
        }

        Object obj1 = getValue(o1);
        Object obj2 = getValue(o2);
        if (!obj1.getClass().equals(String.class) || !obj2.getClass().equals(String.class)) {
            return compareNonStrings(o1, o2);
        }
        String a = obj1.toString();
        String b = obj2.toString();

        int ia = 0, ib = 0;
        int nza = 0, nzb = 0;
        char ca, cb;
        int result;

        while (true) {
            // only count the number of zeroes leading the last number compared
            nza = nzb = 0;

            ca = charAt(a, ia);
            cb = charAt(b, ib);

            // skip over leading spaces
            while (Character.isSpaceChar(ca)) {
                nza = 0;

                ca = charAt(a, ++ia);
            }

            while (Character.isSpaceChar(cb)) {
                nzb = 0;
                cb = charAt(b, ++ib);
            }

            // process run of digits
            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                if ((result = compareRight(a.substring(ia), b.substring(ib))) != 0) {
                    return (this.sortDefinition.isAscending() ? result : -result);
                }
            }

            if (ca == 0 && cb == 0) {
                // The strings compare the same.  Perhaps the caller
                // will want to call strcmp to break the tie.
                return nza - nzb;
            }

            if (ca < cb) {
                return (this.sortDefinition.isAscending() ? -1 : 1);
            }
            else if (ca > cb) {
                return (this.sortDefinition.isAscending() ? 1 : -1);
            }

            ++ia;
            ++ib;
        }
    }

    protected int compareNonStrings(Object o1, Object o2) {
        Object v1 = getValue(o1);
        Object v2 = getValue(o2);
        int result;

        // Put a null property at the end of the sort.
        try {
            if (!(v1 instanceof Comparable)) {
                v1 = v1.toString();
            }
            if (!(v2 instanceof Comparable)) {
                v2 = v2.toString();
            }

            if (v1 != null) {
                if (v2 != null) {
                    result = ((Comparable)v1).compareTo(v2);
                }

                else {
                    result = -1;
                }
            }
            else {
                if (v2 != null) {
                    result = 1;
                }
                else {
                    result = 0;
                }
            }
        }
        catch (RuntimeException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not sort objects [" + o1 + "] and [" + o2 + "]", ex);
            }
            return 0;
        }
        return (this.sortDefinition.isAscending() ? result : -result);
    }

    /**
     * Get the SortDefinition's property value for the given object.
     * @param obj the object to get the property value for
     * @return the property value
     */
    protected Object getPropertyValue(Object obj) {
        Object retObj = null;
        String prop = this.sortDefinition.getProperty();
        if (prop != null) {
            if (obj instanceof Map) {
                Map map = (Map)obj;
                retObj = map.get(prop);
            }
            else {
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
                if (beanWrapper.isReadableProperty(prop)) {
                    retObj = beanWrapper.getPropertyValue(prop);
                }
            }
        }
        return retObj;
    }
}
