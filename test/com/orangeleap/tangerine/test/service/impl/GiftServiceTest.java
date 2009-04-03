package com.orangeleap.tangerine.test.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.PaymentSource;
import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.domain.Site;
import com.orangeleap.tangerine.domain.communication.Address;
import com.orangeleap.tangerine.domain.paymentInfo.DistributionLine;
import com.orangeleap.tangerine.domain.paymentInfo.Gift;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.GiftService;
import com.orangeleap.tangerine.test.BaseTest;
import com.orangeleap.tangerine.type.GiftEntryType;
import com.orangeleap.tangerine.type.GiftType;
import com.orangeleap.tangerine.util.TangerineUserHelper;

public class GiftServiceTest extends BaseTest {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private GiftService giftService;
    
    @Autowired
    private ConstituentService constituentService;
    
    @Autowired
    private TangerineUserHelper tangerineUserHelper;
 
    @BeforeMethod
    public void setup() {
    	tangerineUserHelper.setSystemUserAndSiteName("company1");
    }

    
    @Test
    public void testMaintainGiftMajorDonor() throws Exception {
        Gift gift = getGift();
    	giftService.maintainGift(gift);
    	Person person = constituentService.readConstituentById(gift.getPerson().getId());
    	assert person.isMajorDonor();
    }
    
    private Gift getGift() throws Exception {
    	 // Insert
        Gift gift = new Gift();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        gift.setAcknowledgmentDate(sdf.parse("01/01/2001"));
        gift.setDonationDate(sdf.parse("10/31/1999"));
        gift.setEntryType(GiftEntryType.AUTO);
        gift.setAmount(new BigDecimal(100000.00));
        gift.setCurrencyCode("USD");
        gift.setTxRefNum("0101010101");
        gift.setGiftType(GiftType.MONETARY_GIFT);
        Address addr = new Address();
        addr.setId(100L);
        gift.setSelectedAddress(addr);
        PaymentSource src = new PaymentSource();
        src.setId(100L);
        gift.setSelectedPaymentSource(src);
        Site site = new Site("company1");
        Person person = new Person();
        person.setId(100L);
        person.setSite(site);
        gift.setPerson(person);
        List<DistributionLine> lines = new ArrayList<DistributionLine>();
        
        DistributionLine line = new DistributionLine();
        line.setAmount(new BigDecimal(80000.00));
        line.setProjectCode("proj1");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        line = new DistributionLine();
        line.setAmount(new BigDecimal(20000.00));
        line.setProjectCode("proj2");
        line.setGiftId(gift.getId());
        lines.add(line);
        
        gift.setDistributionLines(lines);
        return gift;
    }
    
}
