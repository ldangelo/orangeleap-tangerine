/*
 * Copyright (c) 2009. Orange Leap Inc. Active Constituent
 * Relationship Management Platform.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.orangeleap.tangerine.integration.transformer;

import com.orangeleap.tangerine.ws.util.ObjectConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class constituentToConstituentTransformer {
    private Logger logger = LoggerFactory.getLogger(constituentToConstituentTransformer.class);

    /**
     * Transforms a constituent object into a Constituent object for integration purposes.
     *
     * @param p is the constituent object you wish to transform
     * @return returns a Constituent object converted from p
     */
    public JAXBElement<com.orangeleap.tangerine.ws.schema.Constituent> transform(com.orangeleap.tangerine.domain.Constituent p) {
        ObjectConverter converter = new ObjectConverter();
        com.orangeleap.tangerine.ws.schema.Constituent c = new com.orangeleap.tangerine.ws.schema.Constituent();

        converter.ConvertToJAXB(p, c);

        logger.debug(c.toString());

        return new JAXBElement<com.orangeleap.tangerine.ws.schema.Constituent>(new QName("constituent"), com.orangeleap.tangerine.ws.schema.Constituent.class, null, c);
    }

}
