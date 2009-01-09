package com.mpower.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpower.domain.ConstituentInfo;
import com.mpower.domain.Person;

public final class Utilities {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Check if a person is null in the ConstituentInfo.  If so, set it to the person instance
     * @param viewable
     * @param person
     */
    public static final void populateIfNullPerson(ConstituentInfo constituentInfo, Person person) {
        if (constituentInfo != null && constituentInfo.getPerson() == null) {
            constituentInfo.setPerson(person);
        }
    }
}
