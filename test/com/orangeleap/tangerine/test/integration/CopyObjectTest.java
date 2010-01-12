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

package com.orangeleap.tangerine.test.integration;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.test.dataprovider.ConstituentDataProvider;
import com.orangeleap.tangerine.test.dataprovider.GiftDataProvider;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.ws.util.ObjectConverter;

/**
 * Created by IntelliJ IDEA.
 * User: ldangelo
 * Date: Jun 8, 2009
 * Time: 10:25:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class CopyObjectTest extends BaseTest
{
    private ObjectConverter converter;
    private com.orangeleap.tangerine.ws.util.v2.ObjectConverter v2converter;
    
    @BeforeClass
    void setup() {
        converter = new ObjectConverter();
        v2converter = new com.orangeleap.tangerine.ws.util.v2.ObjectConverter();
    }

    @Test(groups = {"copyToJAXBConstituentTest"},dataProvider="setupCreateConstituent", dataProviderClass= ConstituentDataProvider.class)
    public void copyToJAXBConstituentTest(Site site, com.orangeleap.tangerine.domain.Constituent constituent)
    {
        com.orangeleap.tangerine.ws.schema.Constituent jaxConstituent = new com.orangeleap.tangerine.ws.schema.Constituent();
        
        converter.ConvertToJAXB(constituent,jaxConstituent);
        System.out.println(jaxConstituent.getFirstName());
        System.out.println(jaxConstituent.getFirstName());
        assert(jaxConstituent.getFirstName() == constituent.getFirstName());
        com.orangeleap.tangerine.domain.Constituent newconstituent = new com.orangeleap.tangerine.domain.Constituent();
        converter.ConvertFromJAXB(constituent,newconstituent);
        assert(newconstituent.getFirstName() == constituent.getFirstName());
    }

    @Test(groups = {"copyToJAXBConstituentTest"},dataProvider="setupCreateConstituent", dataProviderClass= ConstituentDataProvider.class)
    public void copyToJAXBConstituentV2Test(Site site, com.orangeleap.tangerine.domain.Constituent constituent)
    {
        com.orangeleap.tangerine.ws.schema.v2.Constituent jaxConstituent = new com.orangeleap.tangerine.ws.schema.v2.Constituent();
        
        v2converter.ConvertToJAXB(constituent,jaxConstituent);
        System.out.println(jaxConstituent.getFirstName());
        System.out.println(jaxConstituent.getFirstName());
        assert(jaxConstituent.getFirstName() == constituent.getFirstName());
        com.orangeleap.tangerine.domain.Constituent newconstituent = new com.orangeleap.tangerine.domain.Constituent();
        v2converter.ConvertFromJAXB(constituent,newconstituent);
        assert(newconstituent.getFirstName() == constituent.getFirstName());
    }

    @Test(groups = {"copyToJAXBGiftTest"},dataProvider="setupGift", dataProviderClass= GiftDataProvider.class)
    public void copyToJAXBGiftTest(Site site, com.orangeleap.tangerine.domain.Constituent constituent, Gift gift)
    {
        com.orangeleap.tangerine.ws.schema.Gift jaxbGift = new com.orangeleap.tangerine.ws.schema.Gift();
        converter.ConvertToJAXB(gift,jaxbGift);
        assert(gift.getAmount() == jaxbGift.getAmount());
        Gift newgift = new Gift();
        converter.ConvertFromJAXB(jaxbGift,newgift);
        assert(gift.getAmount() == jaxbGift.getAmount());
    }
    
    @Test(groups = {"copyToJAXBGiftTest"},dataProvider="setupGift", dataProviderClass= GiftDataProvider.class)
    public void copyToJAXBGiftV2Test(Site site, com.orangeleap.tangerine.domain.Constituent constituent, Gift gift)
    {
        com.orangeleap.tangerine.ws.schema.v2.Gift jaxbGift = new com.orangeleap.tangerine.ws.schema.v2.Gift();
        v2converter.ConvertToJAXB(gift,jaxbGift);
        assert(gift.getAmount() == jaxbGift.getAmount());
        Gift newgift = new Gift();
        v2converter.ConvertFromJAXB(jaxbGift,newgift);
        assert(gift.getAmount() == jaxbGift.getAmount());
    }    
}
