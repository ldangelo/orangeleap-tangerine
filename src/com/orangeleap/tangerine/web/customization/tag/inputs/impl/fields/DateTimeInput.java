package com.orangeleap.tangerine.web.customization.tag.inputs.impl.fields;

import org.springframework.stereotype.Component;

@Component("dateTimeInput")
public class DateTimeInput extends TextInput {

    @Override
    protected String getSize() {
        return "16";
    }
}
