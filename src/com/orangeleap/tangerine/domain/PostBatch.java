package com.orangeleap.tangerine.domain;

import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;


public class PostBatch extends AbstractCustomizableEntity {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description = "";
    private Map<String, String> whereConditions = new TreeMap<String, String>();
    private Map<String, String> updateFields = new TreeMap<String, String>();


    public PostBatch() {
        super();
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getWhereConditions() {
        return whereConditions;
    }

    /*
     Fields to select on, e.g.:
            status         Paid
            constituentId  123456
            donation_date  >2009-01-01
            txRefNum       !=null
     */
    public void setWhereConditions(Map<String, String> whereConditions) {
        this.whereConditions = whereConditions;
    }

    /*
     Fields to set. e.g.:
            status         Posted
            acustomfield   somevalue
     */
    public Map<String, String> getUpdateFields() {
        return updateFields;
    }

    public void setUpdateFields(Map<String, String> updateFields) {
        this.updateFields = updateFields;
    }
}