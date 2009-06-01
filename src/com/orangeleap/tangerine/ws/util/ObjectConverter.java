package com.orangeleap.tangerine.ws.util;

import com.orangeleap.tangerine.ws.schema.Constituent;
import com.orangeleap.tangerine.domain.Person;
import org.springframework.beans.BeanUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ldangelo
 * Date: Jun 1, 2009
 * Time: 5:08:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class ObjectConverter {

    public ObjectConverter() {

    }

    static Person Convert(Constituent c) {
        Person p = new Person();

        BeanUtils.copyProperties(c,p);

        return p;
    }

    static Constituent Convert(Person p) {
        Constituent c = new Constituent();

        BeanUtils.copyProperties(p,c);

        return c;
    }
}
