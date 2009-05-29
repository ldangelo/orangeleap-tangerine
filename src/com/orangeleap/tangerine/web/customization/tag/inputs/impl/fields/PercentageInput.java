package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import org.springframework.stereotype.Component;

@Component("percentageInput")
public class PercentageInput extends TextInput {

    @Override
    protected String getCssClass() {
        return new StringBuilder(super.getCssClass()).append(" percentage ").toString();
    }
}
