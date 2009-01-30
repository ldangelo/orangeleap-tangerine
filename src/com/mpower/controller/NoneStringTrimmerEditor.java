package com.mpower.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;

public class NoneStringTrimmerEditor extends StringTrimmerEditor {

    public NoneStringTrimmerEditor(boolean emptyAsNull) {
        super(emptyAsNull);
    }

    @Override
    public void setAsText(String text) {
        text = text.replaceFirst("^none$", ""); // Set the value 'none' to empty string
        super.setAsText(text);
    }
}
