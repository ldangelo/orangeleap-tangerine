package com.mpower.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mpower.dao.CodeDao;
import com.mpower.domain.customization.Code;

@Service("codeService")
public class CodeServiceImpl implements CodeService {

	/** Logger for this class and subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	@Resource(name = "codeDao")
	private CodeDao codeDao;

	@Override
	public List<Code> readCodes(String siteName, String codeType) {
		return codeDao.readCodes(siteName, codeType);
	}

	@Override
	public List<Code> readCodes(String siteName, String codeType,
			String startsWith) {
		return codeDao.readCodes(siteName, codeType, startsWith);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Code maintainCode(Code code) {
		return codeDao.maintainCode(code);
	}

	@Override
	public Code readCodeById(Long id) {
		return codeDao.readCode(id);
	}

	@Override
	public List<Code> readCodes(String siteName, String codeType,
			String startsWith, String partialDescription) {
		return codeDao.readCodes(siteName, codeType, startsWith, partialDescription);
	}
}
