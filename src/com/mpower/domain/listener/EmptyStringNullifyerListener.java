package com.mpower.domain.listener;

import java.lang.reflect.Field;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmptyStringNullifyerListener {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

	
    @PrePersist
    @PreUpdate
    public void nullifyEmptyString(Object entity) throws Exception {
        if (entity != null && entity.getClass().isAnnotationPresent(Entity.class)) {
            Field[] fields = entity.getClass().getDeclaredFields();
            handleFields(fields, entity);
        }
    }

    private void handleFields(Field[] fields, Object entity) throws Exception {
        String tmpValue = null;
        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    field.setAccessible(true);
                    tmpValue = (String) field.get(entity);
                    if (tmpValue != null) {
                        tmpValue = tmpValue.trim();
                        if ("".equals(tmpValue)) {
                            field.set(entity, null);
                        }
                    }
                } finally {
                    field.setAccessible(false);
                }
            } else if (field.isAnnotationPresent(Embedded.class)) {
                // Call recursively
                try {
                    field.setAccessible(true);
                    handleFields(field.getType().getDeclaredFields(), field.get(entity));
                } finally {
                    field.setAccessible(false);
                }
            }
        }
    }
}
