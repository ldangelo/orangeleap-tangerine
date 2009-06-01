package com.orangeleap.tangerine.controller.importexport;

import java.util.List;

public class ImportResult {
	
	private List<String> result;
	
	public ImportResult(List<String> result) {
		this.result = result;
	}
	
	public List<String> getResult() {
		List<String> list = result;
		result = null;
		return list;
	}
}
