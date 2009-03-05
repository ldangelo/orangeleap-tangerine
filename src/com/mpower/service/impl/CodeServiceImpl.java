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

import com.mpower.dao.interfaces.CodeDao;
import com.mpower.domain.model.Site;
import com.mpower.domain.model.customization.Code;
import com.mpower.domain.model.customization.CodeType;
import com.mpower.service.AuditService;
import com.mpower.service.CodeService;

@Service("codeService")
@Transactional(propagation = Propagation.REQUIRED)
public class CodeServiceImpl extends AbstractTangerineService implements CodeService {

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    @Resource(name = "auditService")
    private AuditService auditService;

    @Resource(name = "codeDAO")
    private CodeDao codeDao;

    @Override
    public List<Code> readCodes(String codeType) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodes: codeType = " + codeType);
        }
        return codeDao.readCodes(codeType);
    }

    @Override
    public List<Code> readCodes(String codeType, String startsWith) {
        if (logger.isDebugEnabled()) {
            logger.debug("readCodes: codeType = " + codeType + " startsWith = " + startsWith);
        }
        return codeDao.readCodes(codeType, startsWith);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Code maintainCode(Code code) {
        if (logger.isDebugEnabled()) {
            logger.debug("maintainCode: code = " + code);
        }
        Code oldCode = null;
        if (code.getId() != null) {
            Code dbCode = codeDao.readCodeById(code.getId());
            oldCode = codeDao.readCodeById(code.getId());
            oldCode = new Code();
            try {
                BeanUtils.copyProperties(oldCode, dbCode);
                code.setOriginalObject(oldCode);
            } 
            catch (IllegalAccessException e) { } 
            catch (InvocationTargetException e) { }
        }
        code = codeDao.maintainCode(code);
        code.setOriginalObject(oldCode);
        auditService.auditObject(code);
        return code;
    }

    @Override
    public Code readCodeById(Long id) {
        return codeDao.readCodeById(id);
    }

    @Override
    public List<Code> readCodes(String codeType, String startsWith, String partialDescription, Boolean inactive) {
        return codeDao.readCodes(codeType, startsWith, partialDescription, inactive);
    }

	@Override
	public List<String> listCodeTypes() {
		List<String> result = codeDao.listCodeTypes();
		if (result.size() == 0) {
			result = copyGenericCodesToSite();
		}
		return result;
	}
	
	// Copy the template CodeTypes with null site names and their Codes to this site name.
	private List<String> copyGenericCodesToSite() {
		
		List<CodeType> codeTypes = codeDao.listGenericCodeTypes();
		List<Code> codes = codeDao.listGenericCodes();
		for (CodeType codeType : codeTypes) {
			Long origId = codeType.getId();
			codeType.setSite(new Site(getSiteName())); 
			codeType.setId(null);
			Long newId = codeDao.maintainCodeType(codeType).getId();
			for (Code code : codes) {
				if (origId.equals(code.getCodeTypeId())) {
					code.resetIdToNull();
					code.setCodeType(newId);
					codeDao.maintainCode(code);
				}
			}
		}
		return codeDao.listCodeTypes();
		
	}

	@Override
	public CodeType readCodeTypeByName(String codeTypeName) {
		return codeDao.readCodeTypeByName(codeTypeName);
	}

	@Override
    public Code readCodeBySiteTypeValue(String codeType, String codeValue) {
	    if (logger.isDebugEnabled()) {
	        logger.debug("readCodeBySiteTypeValue: codeType = " + codeType + " codeValue = " + codeValue);
	    }
	    return codeDao.readCodeBySiteTypeValue(codeType, codeValue);
	}
}
