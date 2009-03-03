package com.mpower.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("unchecked")
@Deprecated
public abstract class AbstractInstanceValuesMap extends HashMap {
	
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());


	private static final long serialVersionUID = 1L;
	private List originalList;
    private Map internalMap = new LinkedHashMap();

    protected void initialize(List originalList) {
        this.originalList = originalList;
        for (Object currentItem : originalList) {
            internalMap.put(getKeyForItem(currentItem), currentItem);
        }
    }

	public abstract Object getKeyForItem(Object o);

    public abstract Object createObject(Object key);

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(Object key) {
        return internalMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return internalMap.containsValue(value);
    }

    @Override
    public Set entrySet() {
        return internalMap.entrySet();
    }

    @Override
    public Object get(Object key) {
        Object returnVal = internalMap.get(key);
        if (returnVal == null) {
            Object newObject = createObject(key);
            originalList.add(newObject);
            internalMap.put(key, newObject);
            returnVal = newObject;
        }
        return returnVal;
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @Override
    public Set keySet() {
        return internalMap.keySet();
    }

    @Override
    public Object put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public Collection values() {
        return internalMap.values();
    }
}
