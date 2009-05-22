package com.orangeleap.tangerine.test.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.orangeleap.tangerine.domain.Person;
import com.orangeleap.tangerine.service.ConstituentService;
import com.orangeleap.tangerine.service.exception.ConstituentValidationException;
import com.orangeleap.tangerine.test.BaseTest;

public class ConstituentServiceTest extends BaseTest {
	   protected final Log logger = LogFactory.getLog(getClass());


	    @Autowired
	    private ConstituentService constituentService;
	    
	    @Test
	    public void testDuplicatePerson() throws Exception {
	    	Person p = constituentService.createDefaultConstituent();
	    	p.setFirstName("Pablo");
	    	p.setLastName("Picasso");
	    	p.setConstituentType("individual");
	    
	    	try {
	    	constituentService.maintainConstituent(p);
	    	} catch (ConstituentValidationException ex) {
	    		assert true;
	    		return;
	    	}
	    	assert false;
	    }
	    
	    @Test
	    public void testByPassDuplicatePerson() throws Exception {
	    	Person p = constituentService.createDefaultConstituent();
	    	p.setFirstName("Pablo");
	    	p.setLastName("Picasso");
	    	p.setConstituentType("individual");
	    	p.setByPassDuplicateDetection(true);
	    	
	    	try {
	    		constituentService.maintainConstituent(p);
	    	} catch (ConstituentValidationException ex) {
	    		assert false;
	    		return;
	    	}
	    	assert true;	    	
	    }
}
