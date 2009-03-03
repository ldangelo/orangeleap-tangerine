package com.mpower.dao;

import java.util.List;

import com.mpower.domain.customization.Code;
import com.mpower.domain.customization.CodeType;

@Deprecated
public interface CodeDao {
	
	public List<String> listCodeTypes(String siteName);

	public List<Code> readCodes(String siteName, String codeType);

	public List<Code> readCodes(String siteName, String codeType, String startsWith);

	public List<Code> readCodes(String siteName, String codeType, String startsWith, String partialDescription, Boolean inactive);
	
	public Code readCodeBySiteTypeValue(String siteName, String codeType, String codeValue);

	public Code maintainCode(Code code);

	public Code readCode(Long id);
	
	public CodeType readCodeType(String codeType, String siteName);
}
