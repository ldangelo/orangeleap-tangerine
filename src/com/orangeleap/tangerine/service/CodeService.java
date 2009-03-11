package com.orangeleap.tangerine.service;

import java.util.List;

import com.orangeleap.tangerine.domain.customization.Code;
import com.orangeleap.tangerine.domain.customization.CodeType;

public interface CodeService {
	
	public List<CodeType> listCodeTypes();

	public List<Code> readCodes(String codeType);
	
	public List<Code> readCodes(String codeType, String startsWith);
	
	public List<Code> readCodes(String codeType, String startsWith, String partialDescription, Boolean inactive);

	public Code readCodeBySiteTypeValue(String codeType, String codeValue);

	public Code maintainCode(Code code);
	
    public Code readCodeById(Long id);
    
    public CodeType readCodeTypeByName(String codeType);
}
