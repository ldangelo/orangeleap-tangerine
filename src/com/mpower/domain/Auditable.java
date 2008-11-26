package com.mpower.domain;

/**
 * Use this class to indicate an entity is customizable
 * @author jball
 */
public interface Auditable {

    public Auditable getOriginalObject();

    public void setOriginalObject(Auditable auditable);

    public Long getId();

    public Site getSite();

    public Person getPerson();
}
