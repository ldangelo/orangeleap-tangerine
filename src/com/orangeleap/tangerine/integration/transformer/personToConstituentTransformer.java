package com.orangeleap.tangerine.integration.transformer;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.ws.schema.Constituent;
import com.orangeleap.tangerine.ws.util.ObjectConverter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;


/**
 * Created by IntelliJ IDEA.
 * User: ldangelo
 * Date: Jun 2, 2009
 * Time: 9:29:34 AM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class personToConstituentTransformer {
    private Logger logger = LoggerFactory.getLogger(personToConstituentTransformer.class);

    /**
     *
     * Transforms a person object into a Constituent object for integration purposes.
     * 
     * @param p is the person object you wish to transform
     *
     * @return returns a Constituent object converted from p
     */
    public JAXBElement<Constituent> transform(Person p) {
        ObjectConverter converter = new ObjectConverter();
        Constituent c = new Constituent(); 

        converter.ConvertToJAXB(p,c);

        logger.debug(c.toString());

        return new JAXBElement<Constituent>(new QName("constituent"),Constituent.class,null,c);
    }

}
