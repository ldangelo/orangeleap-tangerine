package com.orangeleap.tangerine.controller.lookup;


public class CodeValue {
    private final String code;
    private final String displayValue;
    private final String reference;
    private final boolean selected;

    public CodeValue(final String code, final String displayValue, final String reference, final boolean selected) {
        this.code = code;
        this.displayValue = displayValue;
        this.reference = reference;
        this.selected = selected;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public String getReference() {
        return reference;
    }

    public boolean isSelected() {
        return selected;
    }
}
