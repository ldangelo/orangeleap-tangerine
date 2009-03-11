package com.orangeleap.tangerine.web.remote;

public class HelloImpl implements Hello {

	public String greet(String in) {
		String greeting = "Hello ".concat(in).concat("!");
		return greeting;
	}
}
