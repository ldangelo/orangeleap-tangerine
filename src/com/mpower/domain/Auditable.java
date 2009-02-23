package com.mpower.domain;

/**
 * Use this class to indicate an entity that is auditable, but not Viewable (see interface)
 * @author jball
 */
public interface Auditable {

    public Auditable getOriginalObject();

    public void setOriginalObject(Auditable auditable);

    public Long getId();

    public Person getPerson();
}
