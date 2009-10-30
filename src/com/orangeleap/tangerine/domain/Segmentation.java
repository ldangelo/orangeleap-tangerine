package com.orangeleap.tangerine.domain;

import java.io.Serializable;
import java.util.Date;

public class Segmentation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private int count;
    private Date lastRunDate;
    private String lastRunByUser;

    public Segmentation() { }

    public Segmentation(Long id, String name, String description, int count, Date lastRunDate, String lastRunByUser) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.count = count;
        this.lastRunDate = lastRunDate;
        this.lastRunByUser = lastRunByUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(Date lastRunDate) {
        this.lastRunDate = lastRunDate;
    }

    public String getLastRunByUser() {
        return lastRunByUser;
    }

    public void setLastRunByUser(String lastRunByUser) {
        this.lastRunByUser = lastRunByUser;
    }
}
