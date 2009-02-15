package com.mpower.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.CodeDao;
import com.mpower.domain.customization.Code;
import com.mpower.domain.customization.CodeType;
import com.mpower.service.AuditService;
import com.mpower.service.CodeService;

@Service("codeService")
@Transactional(propagation = Propagation.REQUIRED)
public class CodeServiceImpl implements CodeService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "codeDao")
    private CodeDao codeDao;

    @Override
    public List<Code> readCodes(String siteName, String codeType) {
        return codeDao.readCodes(siteName, codeType);
    }

    @Override
    public List<Code> readCodes(String siteName, String codeType, String startsWith) {
        return codeDao.readCodes(siteName, codeType, startsWith);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Code maintainCode(Code code) {
        Code oldCode = null;
        if (code.getId() != null) {
            Code dbCode = codeDao.readCode(code.getId());
            oldCode = codeDao.readCode(code.getId());
            oldCode = new Code();
            try {
                BeanUtils.copyProperties(oldCode, dbCode);
                code.setOriginalObject(oldCode);
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        code = codeDao.maintainCode(code);
        code.setOriginalObject(oldCode);
        auditService.auditObject(code);
        return code;
    }

    @Override
    public Code readCodeById(Long id) {
        return codeDao.readCode(id);
    }

    @Override
    public List<Code> readCodes(String siteName, String codeType, String startsWith, String partialDescription, Boolean inactive) {
        return codeDao.readCodes(siteName, codeType, startsWith, partialDescription, inactive);
    }

	@Override
	public List<String> listCodeTypes(String siteName) {
		return codeDao.listCodeTypes(siteName);
	}

	@Override
	public CodeType readCodeType(String codeType, String siteName) {
		return codeDao.readCodeType(codeType, siteName);
	}

	@Override
    public Code readCodeBySiteTypeValue(String siteName, String codeType, String codeValue) {
	    if (logger.isDebugEnabled()) {
	        logger.debug("readCodeBySiteTypeValue: siteName = " + siteName + " codeType = " + codeType + " codeValue = " + codeValue);
	    }
	    return codeDao.readCodeBySiteTypeValue(siteName, codeType, codeValue);
	}
}
