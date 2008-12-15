package com.mpower.controller.lookup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SelectedIds {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private final List<String> ids;
    private String idToCheck;

    @SuppressWarnings("unchecked")
    public SelectedIds(final String[] idsStrings) {
        if (idsStrings == null) {
            this.ids = UnmodifiableList.decorate(new ArrayList<String>(0));
        }
        else {
            this.ids = Arrays.asList(idsStrings);
        }
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIdToCheck(String idToCheck) {
        this.idToCheck = idToCheck;
    }

    public boolean isCheckSelectedId() {
        return getIds().contains(idToCheck);
    }
}
