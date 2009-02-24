package com.mpower.test.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mpower.dao.interfaces.CodeDao;
import com.mpower.domain.model.customization.CodeType;
import com.mpower.domain.model.customization.Code;

public class IBatisCodeDaoTest extends AbstractIBatisTest {
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private CodeDao codeDao;

    @BeforeMethod
    public void setupMocks() {
        codeDao = (CodeDao)super.applicationContext.getBean("codeDAO");
    }
    
    private static final String TEST_CODE_TYPE = "projectCode";
    
    @Test(groups = { "testListCodeTypes" })
    public void testListCodeTypes() throws Exception {
    	List<String> list  = codeDao.listCodeTypes();
    	assert list.size() > 0;
    } 

    @Test(groups = { "testReadCodeType" }, dependsOnGroups = { "testListCodeTypes" })
    public void testReadCodeType() throws Exception {
    	CodeType codeType  = codeDao.readCodeType(TEST_CODE_TYPE);
    	assert codeType != null && codeType.getName().equals(TEST_CODE_TYPE);
    } 
    
    @Test(groups = { "testReadCodes" }, dependsOnGroups = { "testListCodeTypes" })
    public void testReadCodes() throws Exception {
    	List<Code> list;
    	Code code  =  codeDao.readCode(new Long(1));
    	assert code != null && code.getId().equals(new Long(1));
    	list  =  codeDao.readCodes("");
    	assert list.size() == 0;
    	list  =  codeDao.readCodes(TEST_CODE_TYPE);
    	assert list.size() > 0 && list.get(0).getValue().equals("001000");
    	list  =  codeDao.readCodes(TEST_CODE_TYPE, "0010");
    	assert list.size() == 1 && list.get(0).getValue().equals("001000");
    	list  =  codeDao.readCodes(TEST_CODE_TYPE, "0010", "General", false);
    	assert list.size() == 1 && list.get(0).getValue().equals("001000");
    	list  =  codeDao.readCodes("currencyCode", "AE", "", true);
    	assert list.size() == 1 && list.get(0).getValue().startsWith("AE");
    } 
    
    @Test(groups = { "testReadCodeBySiteTypeValue" }, dependsOnGroups = { "testListCodeTypes" })
    public void testReadCodeBySiteTypeValue() throws Exception {
    	Code code  =  codeDao.readCodeBySiteTypeValue(TEST_CODE_TYPE, "001000");
    	assert code != null && code.getId().equals(new Long(1));
    }
    
    @Test(groups = { "testReadCodeBySiteTypeValue" }, dependsOnGroups = { "testListCodeTypes" })
    public void testMaintainCode() throws Exception {
        String testCodeValue = "X"+(""+Math.random()).substring(3,8);

        Code code = new Code();
    	code.setCodeType(new Long(1));
    	code.setDescription("original desc");
    	code.setInactive(false);
    	code.setValue(testCodeValue);
    	code  =  codeDao.maintainCode(code);
    	code  =  codeDao.readCodeBySiteTypeValue(TEST_CODE_TYPE, testCodeValue);
    	assert code != null && code.getValue().equals(testCodeValue);
    	
    	
    	String newDesc = "change desc";
    	code.setDescription(newDesc);
    	code  =  codeDao.maintainCode(code);
    	code  =  codeDao.readCodeBySiteTypeValue(TEST_CODE_TYPE, testCodeValue);
    	assert code != null && code.getDescription().equals(newDesc);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
