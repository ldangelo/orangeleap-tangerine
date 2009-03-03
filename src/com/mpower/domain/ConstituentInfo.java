package com.mpower.domain;

@Deprecated
public interface ConstituentInfo extends Viewable {
    void setPerson(Person person);

    boolean isValid();
}
