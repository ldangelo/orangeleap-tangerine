package com.mpower.test.web.customization;

import org.testng.annotations.Test;

import com.mpower.test.BaseTest;
import com.mpower.web.customization.FieldVO;

public class FieldVOTest extends BaseTest {
    
    @Test(groups = { "FieldVO.testGetOtherFieldName" })
    public void testGetOtherFieldName() {
        FieldVO fieldVO = new FieldVO();
        
        fieldVO.setFieldName("customFieldMap[reference]");
        assert "customFieldMap[other_reference]".equals(fieldVO.getOtherFieldName());
        fieldVO.setFieldName("motivationCode");
        assert "other_motivationCode".equals(fieldVO.getOtherFieldName());
        fieldVO.setFieldName("customFieldMap[individual.spouse]");
        assert "customFieldMap[individual.other_spouse]".equals(fieldVO.getOtherFieldName());
        
        fieldVO.setFieldName("customFieldMap[reference].value");
        assert "customFieldMap[other_reference].value".equals(fieldVO.getOtherFieldName());
        fieldVO.setFieldName("motivationCode.value");
        assert "other_motivationCode.value".equals(fieldVO.getOtherFieldName());
        fieldVO.setFieldName("customFieldMap[individual.spouse].value");
        assert "customFieldMap[individual.other_spouse].value".equals(fieldVO.getOtherFieldName());
    }
}
