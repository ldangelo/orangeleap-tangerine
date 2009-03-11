package com.orangeleap.tangerine.domain.util;

import java.util.LinkedHashMap;

import com.orangeleap.tangerine.domain.customization.CustomField;

@SuppressWarnings("serial")
public class CustomFieldMap<K, V> extends LinkedHashMap<K, V> {

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        V o = super.get(key);
        if (o == null) {
            o = (V) new CustomField((String) key);
            super.put((K) key, o);
        }
        return o;
    }
    
}
