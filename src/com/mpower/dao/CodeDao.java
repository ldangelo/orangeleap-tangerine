package com.mpower.dao;

import java.util.List;

import com.mpower.domain.customization.Code;

public interface CodeDao {

	public List<Code> readCodes(String siteName, String codeType);

	public List<Code> readCodes(String siteName, String codeType, String startsWith);

	public List<Code> readCodes(String siteName, String codeType, String startsWith, String partialDescription);

	public Code maintainCode(Code code);

	public Code readCode(Long id);
}
