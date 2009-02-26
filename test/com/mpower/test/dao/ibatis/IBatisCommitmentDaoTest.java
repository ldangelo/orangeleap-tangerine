package com.mpower.test.dao.ibatis;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.CommitmentDao;
import com.mpower.dao.interfaces.ConstituentDao;
import com.mpower.domain.model.Commitment;
import com.mpower.domain.model.Person;
import com.mpower.type.CommitmentType;

public class IBatisCommitmentDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private CommitmentDao commitmentDao;
    private ConstituentDao constituentDao;

    @BeforeMethod
    public void setupMocks() {
        commitmentDao = (CommitmentDao)super.applicationContext.getBean("commitmentDAO");
    	constituentDao = (ConstituentDao)super.applicationContext.getBean("constituentDAO");
    }
    
    
    @Test(groups = { "testCreateCommitment" })
    public void testCreateCommitment() throws Exception {
    	
    	Commitment commitment = new Commitment();
    	commitment.setComments("comments");
    	commitment.setNotes("notes");
    	commitment.setAutoPay(true);
    	commitment.setAmountPerGift(new BigDecimal(150));
    	commitment.setCurrencyCode("USD");
    	commitment.setAmountTotal(new BigDecimal(15000));
    	commitment.setCommitmentType(CommitmentType.RECURRING_GIFT);
    	
    	Person person = constituentDao.readConstituentById(new Long(100));
    	commitment.setPerson(person);
    	commitmentDao.maintainCommitment(commitment);
    	
    	person = constituentDao.readConstituentById(new Long(200));
    	commitment.setPerson(person);
    	commitmentDao.maintainCommitment(commitment);
    	
    //	commitment = commitmentDao.readCommitment(commitment.getId());
    //	assert commitment.getId().equals(new Long(200));
    	
    } 

    
}
