package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import org.springframework.stereotype.Component;

@Component("numberInput")
public class NumberInput extends TextInput {

    @Override
    protected String getCssClass() {
        return new StringBuilder(super.getCssClass()).append(" number ").toString();
    }
}
