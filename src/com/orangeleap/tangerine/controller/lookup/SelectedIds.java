package com.orangeleap.tangerine.controller.lookup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.list.UnmodifiableList;
import org.apache.commons.logging.Log;
import com.orangeleap.tangerine.util.OLLogger;
import org.springframework.util.StringUtils;

public class SelectedIds {

    /** Logger for this class and subclasses */
    protected final Log logger = OLLogger.getLog(getClass());

    private List<String> names;
    private List<String> ids;
    private String idToCheck;

    @SuppressWarnings("unchecked")
    public SelectedIds(final String idsString) {
        if (idsString == null) {
            this.ids = UnmodifiableList.decorate(new ArrayList<String>(0));
            this.names= UnmodifiableList.decorate(new ArrayList<String>(0));
        }
        else {
            String[] nameIdPair = StringUtils.delimitedListToStringArray(idsString, "^");
            if (nameIdPair == null || nameIdPair.length == 0) {
                this.ids = UnmodifiableList.decorate(new ArrayList<String>(0));
                this.names= UnmodifiableList.decorate(new ArrayList<String>(0));
            }
            else {
                this.names = new ArrayList<String>();
                this.ids = new ArrayList<String>();
                for (String nameId : nameIdPair) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("SelectedIds: adding " + nameId);
                    }
                    String[] pair = nameId.split("\\|");
                    if (pair != null && pair.length == 2) {
                        names.add(pair[0]);
                        ids.add(pair[1]);
                    }
                }
                this.names = UnmodifiableList.decorate(this.names);
                this.ids = UnmodifiableList.decorate(this.ids);
            }
        }
    }

    public List<String> getNames() {
        return names;
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
    
    @Override
    public String toString() {
        return StringUtils.collectionToCommaDelimitedString(this.ids);
    }
}
