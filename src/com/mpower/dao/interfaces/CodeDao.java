package com.mpower.dao.interfaces;

import java.util.List;

import com.mpower.domain.model.customization.Code;
import com.mpower.domain.model.customization.CodeType;

public interface CodeDao {
	
	public List<String> listCodeTypes();

	public List<Code> readCodes(String codeType);

	public List<Code> readCodes(String codeType, String startsWith);

	public List<Code> readCodes(String codeType, String startsWith, String partialDescription, Boolean inactive);
	
	public Code readCodeBySiteTypeValue(String codeType, String codeValue);

	public Code maintainCode(Code code);

	public Code readCode(Long id);
	
	public CodeType readCodeType(String codeType);
}
